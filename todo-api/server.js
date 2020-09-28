var http = require('http');
var express = require('express');
const colors = require('colors');
var morgan = require('morgan');
const dotenv = require('dotenv');
const connectDB = require('./config/db');


const app = express()
app.use(morgan('dev'));

app.use(express.json({}))
app.use(express.json({
    entended:true
}))

dotenv.config({
    path: './config/config.env'
});

connectDB();


// we will get similar URL https://localhost:3000/api/todo/auth/register and check the post request on postman
app.use('/api/todo/auth', require('./routes/user'));
app.use('/api/todo', require('./routes/todo'));

const PORT = process.env.PORT || 3000;

app.listen(PORT,  
    console.log(`server is running on port ${PORT}`.red.underline.bold)
);
