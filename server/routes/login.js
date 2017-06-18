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