const jwt = require('jsonwebtoken');

module.exports = async function(req, res, next){
    // here we will verify if authorization header exists or not
    const token = req.header('Authorization'); // "authorization" is the key set during api, we can set some other name too
    if(!token){
        return res.status(401).json({
            msg:"no token, authorization denied"
        })
    }
    try{
        await jwt.verify(token, process.env.jwtUserSecret, (err, decoded) => {
            if(err){
                res.status(401).json({
                    msg:"token not valid"
                });
            } else {
                req.user = decoded.user;
                next();
            }
        })
    } catch(err){
        console.log("something went wrong with middleware" + err);
        res.json(500).json({
            msg: "server error"
        });
    }
}
