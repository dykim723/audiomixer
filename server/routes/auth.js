/*
/!**
 * Created by dykim on 2017-06-06.
 *!/
'use strict';

var jwt = require('jsonwebtoken');
var compose = require('composable-middleware');
var SECRET = 'token_secret';
var EXPIRES = 60; // 1 hour

// JWT 토큰 생성 함수
function signToken(id) {
    return jwt.sign({id: id}, SECRET, { expiresIn: EXPIRES });
}

// 토큰을 해석하여 유저 정보를 얻는 함수
function isAuthenticated() {
    return compose()
    // Validate jwt
        .use(function(req, res, next) {
            var decoded = jwt.verify(req.headers.authorization, SECRET);
            console.log(decoded) // '{id: 'user_id'}'
            req.user = decode;
        })
        // Attach user to request
        .use(function(req, res, next) {
            req.user = {
                email: req.user.email,
                name: req.user.name
            };
            next();
        });
}


exports.signToken = signToken;
exports.isAuthenticated = isAuthenticated;*/


'use strict';

var express = require('express');
var passport = require('passport');

// 패스포트 세팅
require('../config/passport').setup();

var router = express.Router();

/*router.get('/login/kakao/callback', function(){
    console.log('dddd');
});*/

module.exports = router;