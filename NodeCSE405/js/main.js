const http = require('http');
const url = require('url');
const fs = require('fs');
const server = http.createServer((req, res) => {
    if (req.url === '/') {
        res.write('Welcome to my Node.js!');
        res.end();
    }

    if (req.url === '/miya.html') {
        fs.readFile('miya.html', function (err, data) {
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.write(data);
            return res.end();
        });

    }

    if (req.url === '/hiro.html') {
        fs.readFile('hiro.html', function (err, data) {
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.write(data);
            return res.end();
        });

    }

    if (req.url === '/kyo.html') {
        fs.readFile('kyo.html', function (err, data) {
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.write(data);
            return res.end();
        });

    }

    if (req.url === '/index.html') {
        fs.readFile('index.html', function (err, data) {
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.write(data);
            return res.end();
        });

    }
});
server.listen(8080);

console.log('Listening on port 8080.');