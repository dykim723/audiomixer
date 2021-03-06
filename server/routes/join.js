var express = require('express');
var router = express.Router();

var mysql      = require('mysql');
var connection = require('../config/database.js');

function createUser(email, password, nickname){
    var query;
    var user = {'Email': email,
        'Password': password,
        'Name': nickname};

    connection.query('INSERT INTO UserInfo SET ?', user , function (err, result) {
        if (err) console.error("err : " + err);
        else{
            console.log(result);
        }

        /*if(re.length == 1){
         res.json({'status':'SUCCESS'});
         } else{
         res.json({'status':'User Creation FAIL'});
         }*/

        //connection.release();

        // Don't use the connection here, it has been returned to the pool.
    });
}

router.post('/', function (req, res, next) {

    // Use the connection
    //console.log(req.query.email, req.query.password);
    var query;
    req.accepts('application/json');

    json = req.body;
    console.log(json);
    console.log(json.email, json.password, json.nickname);
    connection.query('SELECT * FROM UserInfo WHERE Email=?', [json.email] , function (err, rows) {
        if (err) console.error("err : " + err);
        tJson = JSON.stringify(rows);
        console.log("rows : " + tJson);

        if(rows.length == 1){
            res.json({'user': false, 'description': 'Already The User Created'});
        } else{
            createUser(json.email, json.password, json.nickname);
            //connection.release();
            res.json({'email':json.email, 'password': json.password, 'nickname': json.nickname});
        }



        // Don't use the connection here, it has been returned to the pool.
    });

});



module.exports = router;