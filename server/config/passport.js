'use strict';

var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var connection = require('./database');

exports.setup = function () {

    passport.serializeUser(function(user, done){
        console.log(user);
       done(null, user);
    });

    passport.deserializeUser(function(user, done){
        console.log(user);
       done(null, user);
    });

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
};