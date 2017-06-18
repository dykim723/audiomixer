var express = require('express');
var router = express.Router();
var auth = require('../config/auth');

/* GET users listing. */
/*
router.get('/', auth.isAuthenticated(), function(req, res) {
  res.send(req.user);
});
*/

module.exports = router;
