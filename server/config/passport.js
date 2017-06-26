'use strict';

var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var KakaoStrategy = require('passport-kakao').Strategy;
var connection = require('./database');
var config = require('./config');


function loginByThirdparty(info, done) {
    console.log('process : ' + info.auth_type);
    var stmt_duplicated = 'SELECT * FROM `UserInfo` WHERE `email` = ?';

    connection.query(stmt_duplicated, info.auth_id, function (err, result) {
        if (err) {
            return done(err);
        } else {
            if (result.length === 0) {
                // TODO 신규 유저 가입 시켜야됨
                var stmt_thridparty_signup = 'INSERT INTO `UserInfo` set `email`= ?, `name`= ?';
                connection.query(stmt_thridparty_signup, [info.auth_id, info.auth_name], function (err, result) {
                    if(err){
                        return done(err);
                    }else{
                        done(null, {
                            'email': info.auth_id,
                            'name': info.auth_name
                        });
                    }
                });
            } else {
                //  TODO 기존유저 로그인 처리
                console.log('Old User');
                done(null, {
                    'email': result[0].user_id,
                    'name': result[0].nickname
                });
            }
        }
    });
}

exports.setup = function () {
    passport.use(new LocalStrategy({
            usernameField: 'email',
            passwordField: 'password'
        },
        function(email, password, done) {
            // 인증 정보 체크 로직

            console.log(email, password);
            connection.query('SELECT * FROM UserInfo WHERE Email=? AND Password=?', [email, password] , function (err, rows) {
                if (err) console.error("err : " + err);
                var string = JSON.stringify(rows);
                console.log("rows : " + string);

                var json = JSON.parse(string);
                console.log(json);

                if(rows.length == 1){
                    //res.json({'email':json.email, 'password': json.password, 'nickname': json.nickname});
                    var user = {'email':json[0].Email, 'password': json[0].Password, 'nickname': json[0].Name};
                    return done(null, user);
                } else{
                    //res.json({'email':"", 'password': "", 'nickname': ""});
                    return done(null, false, { message: 'Fail to login.' });
                }

                connection.release();

                // Don't use the connection here, it has been returned to the pool.
            });


        }
    ));

    passport.use(new KakaoStrategy({
            clientID: config.federation.kakao.client_id,
            callbackURL: config.federation.kakao.callback_url
        },
        function (accessToken, refreshToken, profile, done) {
            var _profile = profile._json;
            console.log('Kakao login info');
            console.info(_profile);
            // todo 유저 정보와 done을 공통 함수에 던지고 해당 함수에서 공통으로 회원가입 절차를 진행할 수 있도록 한다.

            loginByThirdparty({
                'auth_type': 'kakao',
                'auth_id': _profile.id,
                'auth_name': _profile.properties.nickname,
                'auth_email': _profile.id
            }, done);
        }
    ));
};