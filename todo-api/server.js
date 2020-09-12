var http = require('http');

http.createServer(function(request, response){
    // 200 - success
    // 400 - program error
    // 500 - server error
    response.writeHead(200, {
        'Content-Type': 'text/plain'
    });

    response.end("Hello nodemon");

}).listen(3000, console.log("server is running on port 3000")); 