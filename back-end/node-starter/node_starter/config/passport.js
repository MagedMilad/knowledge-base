var passport = require('passport');
var User = require('../models/user');

module.exports = function(app){
    app.use(passport.initialize());
    app.use(passport.session());

    passport.serializeUser(function(user, callback){
        callback(null, user._id);
    });

    passport.deserializeUser(function(userId, callback){
        User.findById(userId, function(err, user){
            if(err) return callback(err);
            callback(null, user);
        });
    });

    require('./strategies/localStrategy')();
};