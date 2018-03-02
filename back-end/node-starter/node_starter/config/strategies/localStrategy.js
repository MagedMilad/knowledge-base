var passport = require('passport');
var LocalStrategy = require('passport-local').Strategy;
var User = require('../../models/user');

module.exports = function () {
    passport.use(new LocalStrategy({
        userNameField: 'username',
        passwordField: 'password',
        passReqToCallback : true        
    }, function (req, username, password, callback) {
        User.findOne({ 'username': username }, function (err, user) {
                if (err) return callback(err);
                if(!user){
                    return callback(null, false, { message: 'Unknown user' });
                }
                user.comparePassword(password, function (err, isMatch) {
                    if (err) return callback(err);
                    if (!isMatch) return callback(null, false, { message: 'Invalid password' });
                    return callback(null, user);
                });
            });
    }));
};