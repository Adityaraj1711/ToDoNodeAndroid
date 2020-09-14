var http = require('http');
var express = require('express');
const colors = require('colors');
var morgan = require('morgan');
const dotenv = require('dotenv');
const connectDB = require('./config/db');


const app = express()
app.use(morgan('dev'));

dotenv.config({
    path: './config/config.env'
});

connectDB();

// GET, POST, DELETE, PUT
app.get('/todo', (req, res) => {
    res.status(200).json({
        "name": "bravo"
    });
});

const PORT = process.env.PORT || 3000;

app.listen(PORT,  
    console.log(`server is running on port ${PORT}`.red.underline.bold)
);
