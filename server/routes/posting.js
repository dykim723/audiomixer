var express = require('express');
var router = express.Router();
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');
var multer = require('multer')
var fs = require('fs');

var storage = multer.diskStorage({
    destination: function (req, file, cb) {
        var dirPath = './upload/' + req.body.Email.substring(2, req.body.Email.length);

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
        var dirPath = './upload/' + email + '/';
        var fileName = file.originalname.substring(2, file.originalname.length);
        var fileNameBuff = file.originalname.substring(2, file.originalname.length);
        var fileNum = 1;

        console.log("File Count: " + req.files.length);

        while(fs.existsSync(dirPath + fileNameBuff))
        {
            fileNameBuff = fileNum + '_' + fileName;
            fileNum++;
        }

        var insertData = {FileNo: 0, FilePath: fileNameBuff, BoardNo: 0, Email: email};

        connection.query('INSERT INTO FileInfo SET ?', insertData, function(err, result) {
            if (err) {
                console.log('insert query fail');
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

    var insertData = {BoardNo: 0, Title: title, Content: content, Email: email};

    connection.query('INSERT INTO Board SET ?', insertData, function(err, result) {
        if (err) {
            console.log('insert query fail');
            return;
        }
        else {
            console.log('insert query success');
            connection.query('SELECT BoardNo FROM Board WHERE Email = "' + email + '" ORDER BY BoardNo DESC' , function(err, result) {
                if (err) {
                    console.log('select query fail');
                    return;
                }
                else {
                    console.log('select query success');
                    console.log('result.BoardNo ' + result[0].BoardNo);
                    boardNo = result[0].BoardNo;

                    connection.query('UPDATE FileInfo SET BoardNo = ' + boardNo + ' WHERE BoardNo = 0' , function(err, result) {
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