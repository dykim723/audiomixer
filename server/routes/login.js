/*
var express = require('express');
var router = express.Router();
var passport = require('passport');

var mysql      = require('mysql');
var pool = mysql.createPool({
    connectionLimit: 3,
    host: '52.78.143.80',
    user: 'ionicClient',
    database: 'EnsembleDB',
    password: 'qlqjs1989'
});

router.post('/', passport.authenticate('local', {
    successRedirect: console.log('1234'),
    failureRedirect: console.log('33333'),
    failureFlash: true
}), function(req, res, info){
    //res.render('login/index',{'message' :req.flash('message')});
    console.log('!!!!');
});

/!*router.post('/', function (req, res, next) {
    /!*pool.getConnection(function (err, connection) {
        // Use the connection
        //console.log(req.query.email, req.query.password);
        var query;
        req.accepts('application/json');

        json = req.body;
        console.log(json.email, json.password);
        query = connection.query('SELECT * FROM UserInfo WHERE Email=? AND Password=?', [json.email, json.password] , function (err, rows) {
            if (err) console.error("err : " + err);
            console.log("rows : " + JSON.stringify(rows));

            if(rows.length == 1){
                res.json({'email':json.email, 'password': json.password, 'nickname': json.nickname});
            } else{
                res.json({'email':"", 'password': "", 'nickname': ""});
            }

            connection.release();

            // Don't use the connection here, it has been returned to the pool.
        });
    });*!/

});*!/



module.exports = router;*/

'use strict';

var express = require('express');
var passport = require('passport');
var auth = require('../config/auth');

// 패스포트 세팅
require('../config/passport').setup();

var router = express.Router();

// 로그인 라우팅 POST /login
router.post('/', function(req, res, next) {

    //  패스포트 모듈로 인증 시도
    passport.authenticate('local', function (err, user, info) {
        var error = err || info;
        if (error) {
            //return res.json(401, error);
            console.log(err);
            console.log(info);
            return res.status(401).json(error);
        }

        if (!user) {
            return res.json(404, {message: 'Something went wrong, please try again.'});
        }

        // 인증된 유저 정보로 응답
        //res.json(req.user);
        var token = auth.signToken(user.email);
        console.log(user);
        console.log(token);
        res.status(201).json(token);
    })(req, res, next);
});

module.exports = router;