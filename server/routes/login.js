'use strict';

var express = require('express');
var passport = require('passport');


// 패스포트 세팅
require('../config/passport').setup();

var router = express.Router();


router.post('/auth/login/kakao', passport.authenticate('kakaoLocal', { successRedirect: '/',
    failureRedirect: '/login',/*router.post('/auth/login/kakao', function(req, res, next) {
     passport.authenticate('kakaoLocal', function (err, user, info) {
     var error = err || info;
     if (error) {
     //return res.json(401, error);
     console.log(err);
     console.log(info);
     return res.status(401).json(error);
     }
     console.log(user);

     /!*console.log(res.body);
     if (!user) {
     return res.json(404, {message: 'Something went wrong, please try again.'});
     }*!/

     // 인증된 유저 정보로 응답
     //res.json(req.user);
     /!*var token = auth.signToken(user.email);
     console.log(user);
     console.log(token);
     res.status(201).json(token);*!/
     })(req, res, next);
     });*/
    failureFlash: true })
);



router.get('/auth/login/kakao/callback',
    passport.authenticate('kakao', {
        successRedirect: '/',
        failureRedirect: '/login'
    })
);

// 로그인 라우팅 POST /login
router.post('/', passport.authenticate('local', { successRedirect: '/',
    failureRedirect: '/login',
    failureFlash: true })
);

/*
router.post('/', passport.authenticate('local', function (email, password, done) {
    /!*var error = err;
    if (error) {
        //return res.json(401, error);
        console.log(err);
        //console.log(info);
        return res.status(401).json(error);
    }*!/
    var user = {
        "email":email,
        "password":password
    }
    if (!user) {
        return res.json(404, {message: 'Something went wrong, please try again.'});
    }

    return done(null, user);
    // 인증된 유저 정보로 응답
    //res.json(req.user);
    /!*var token = auth.signToken(user.email);
    console.log(user);
    console.log(token);
    res.status(201).json(token);*!/
}));
*/

module.exports = router;