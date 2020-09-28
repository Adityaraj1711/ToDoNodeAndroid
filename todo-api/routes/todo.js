const express = require('express');
const auth = require("../middleware/user_jwt");
const Todo = require('../models/todo')

const router = express.Router();

// create new todo task
// method POST
router.post('/', auth, async (req, res, next) => {
    try{
        const toDo = await Todo.create({title: req.body.title, description: req.body.description, user: req.user.id});
        if(!toDo){
            return res.status(400).json({
                success: false,
                msg: "something went wrong"
            });
        }
        res.status(200).json({
            success: true,
            todo: toDo,
            msg: 'successfully created'
        });
    } 
    catch(error){
        next(error);
    }
})

// desc Fetch all ToDos
// method GET

router.get('/', auth, async(req, res, next) => {
    try{
        const todo = await Todo.find({user: req.user.id, finished: false});

        if(!todo){
            return res.status(400).json({success: false, msg: "error happened"});
        }
        res.status(200).json({success: true, count: todo.length, todos: todo ,msg: "Successfully fetched"});
    }
    catch(error){
        next(error);
    }
})

module.exports = router;