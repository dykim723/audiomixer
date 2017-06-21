var express = require('express');
var router = express.Router();
var mysql      = require('mysql');
var connection = require('../config/database');

router.post('/', function (req, res) 
{
    var boardNo = Number(req.body.BoardNo);
	var queryStr;
	
	console.log('Run board.js !!!');
	console.log("boardNo " + boardNo);
	
	if(boardNo == -1) 
	{
		queryStr = "SELECT * FROM Board, FileInfo WHERE Board.BoardNo = FileInfo.Board_BoardNo ORDER BY BoardNo DESC LIMIT 5;";
	}
	else 
	{
		queryStr = "SELECT * FROM Board, FileInfo WHERE Board.BoardNo = FileInfo.Board_BoardNo AND BoardNo < " + boardNo + " ORDER BY BoardNo DESC LIMIT 5;"
	}
	
	connection.query(queryStr , function(err, result) 
	{
		if (err) 
		{
			console.log('Select board query fail');
			return;
		}
		else 
		{
			console.log('Select board success'); 
			res.json(result);
		}
	});
});

module.exports = router;