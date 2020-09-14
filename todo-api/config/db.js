// connect to mongo database using mongoose
const mongoose = require("mongoose");
// we will use async as we will be connecting to database asynchronously
const connectDB = async() => {
    const conn = await mongoose.connect(process.env.MONGO_URI, 
        {
            useNewUrlParser: true,
            useCreateIndex: true,
            useFindAndModify: false,
            useUnifiedTopology: true
        });
    console.log(`mongoDB connected : ${conn.connection.host}`.cyan.bold);
}

module.exports = connectDB;