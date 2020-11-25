var express = require("express");
var app = express();
var server = require("http").createServer(app);
var io = require("socket.io").listen(server);
server.listen(process.env.PORT || 3000);

var host = server.address().address
var port = server.address().port
console.log('Server listening at http://%s:%s', host, port)

io.sockets.on('connection', function (socket) {	
	var socketId = socket.id;
	var clientIp = socket.request.connection.remoteAddress;
	console.log('New connection from SocketID <' + socketId + '>, ClientIP <' + clientIp + '>');
	
	socket.on('client_receive_msg_all_success', function (data) {		
		console.log("Server received message: "+ data.msg +"\n\n");
	});
	
	var i=1;	
	var myTimer = setInterval(function(){
		// emit to this client
		socket.emit('chat_all', { msg:i});
		i++;
		console.log("Server sent message: "+ i +"\n\n");
	}, 1000);
	
	socket.on('disconnect', function () {
		clearInterval(myTimer);
	});
});