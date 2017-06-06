/**
 * Created by dykim on 2017-06-05.
 */
var mysql        = require('mysql');
var connection   = mysql.createConnection({
    supportBigNumbers: true,
    bigNumberStrings: true,
    connectionLimit: 3,
    host: '52.78.143.80',
    user: 'ionicClient',
    password: 'qlqjs1989',
    database: 'EnsembleDB'
});

module.exports = connection;
