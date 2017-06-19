var express = require('express');
var router = express.Router();
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');
var multer = require('multer')
var fs = require('fs');
var connection = require('/home/ubuntu/work/audiomixer_branch/audiomixer/server/config/database');
var dateReq = require('date-utils');

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        var dirPath = '/home/ubuntu/work/audiomixer_branch/audiomixer/server/public/' + req.body.Email.substring(2, req.body.Email.length);

        if(fs.existsSync(dirPath) == false)
        {
            fs.mkdir(dirPath, 0777, function(err) {
                if(err)
                    console.log('err mkdir');
                else
                    console.log('Created newdir');
            });
        }

        cb(null, dirPath);
    },
    filename: function (req, file, cb) {
        var email = req.body.Email.substring(2, req.body.Email.length);
        var fileType = req.body.FileType.substring(2, req.body.FileType.length);
        var dirPath = '/home/ubuntu/work/audiomixer_branch/audiomixer/server/public/' + email + '/';
        var fileName = file.originalname.substring(2, file.originalname.length);
        var fileNameBuff = file.originalname.substring(2, file.originalname.length);
        var fileNum = 1;

        console.log("File Count: " + req.files.length);
        console.log('file type: ' + fileType);

        while(fs.existsSync(dirPath + fileNameBuff))
        {
            fileNameBuff = fileNum + '_' + fileName;
            fileNum++;
        }

        var insertData = {FileNo: 0, FilePath: fileNameBuff, FileType: fileType, Board_BoardNo: 0, UserInfo_Email: email};

        connection.query('INSERT INTO FileInfo SET ?', insertData, function(err, result) {
            if (err) {
                console.log('insert query fail 1');
                return;
            }
            else {
                console.log('insert query success 1');
            }
        });

        cb(null, fileNameBuff);
    }
});

var upload = multer({ storage: storage });

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(function (req, res, next) {
    // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', '*');
    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    // Request headers you wish to allow
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    res.setHeader('Access-Control-Allow-Credentials', true);
    // Pass to next layer of middleware
    next();
});

/* POST users listing. */
router.post('/', upload.array('file', 5), function (req, res) {
    var title;
    var content;
    var email;
    var boardNo = 0;

    title = req.body.Title.substring(2, req.body.Title.length);
    content = req.body.Content.substring(2, req.body.Content.length);
    email = req.body.Email.substring(2, req.body.Email.length);

    console.log('Title ' + title);
    console.log('Content ' + content);
    console.log('Email ' + email);

    //var insertData = {BoardNo: 0, Title: title, Content: content, Email: email};
    var strDate = new Date().toFormat("YYYY-MM-DD HH24:MI:SS");
    //var strDate = new Date().toFormat("YYYY-MM-DD");
    console.log('date: ' + strDate);
    var insertData = {BoardNo: 0, Title: title, Content: content, Date: strDate, UserInfo_Email: email};

    connection.query('INSERT INTO Board SET ?', insertData, function(err, result) {
        if (err) {
            console.log('insert query fail');
            return;
        }
        else {
            console.log('insert query success');
            connection.query('SELECT BoardNo FROM Board WHERE UserInfo_Email = "' + email + '" ORDER BY BoardNo DESC' , function(err, result) {
                if (err) {
                    console.log('select query fail');
                    return;
                }
                else {
                    console.log('select query success');
                    console.log('result.BoardNo ' + result[0].BoardNo);
                    boardNo = result[0].BoardNo;

                    connection.query('UPDATE FileInfo SET Board_BoardNo = ' + boardNo + ' WHERE Board_BoardNo = 0' , function(err, result) {
                        if (err) {
                            console.log('update query fail');
                            return;
                        }
                        else {
                            console.log('update query success');
                            res.json({'BoardNo': ''+boardNo});
                        }
                    });
                }
            });
        }
    });
});

module.exports = router;
