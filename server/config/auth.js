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

module.exports = {
    'secret' :  '',

    'federation' : {
        'google': {
            'client_id' : '',
            'secret_id' : '',
            'callback_url' : '/auth/login/google/callback'
        },
        'naver' : {
            'client_id' : '',
            'secret_id' : '',
            'callback_url' : '/auth/login/naver/callback'
        },
        'facebook' : {
            'client_id' : '',
            'secret_id' : '',
            'callback_url' : '/auth/login/facebook/callback'
        },
        'kakao' : {
            'client_id' : '',
            'callback_url' : '/auth/login/kakao/callback'
        }
    }
};