var User = require('../models/user');
var passport = require('passport');

exports.authenticatedMiddleware = function (req, res, next) {
    if (req.user)
        next();
    else
        next({ message: 'not allowed' });
};

exports.signIn = function (req, res, next) {
    var user = req.body;
    passport.authenticate('local', function (err, user, info) {
        if (err || !user) {
            return res.status(400).send(info);
        }

        req.logIn(user, function (err) {
            if (err) return next(err);
            return res.redirect('/');
        });
    })(req, res, next);
};

exports.signUp = function (req, res, next) {
    User.create(req.body, function (err, user) {
        if (err)
            res.send(err);
        else {
            req.logIn(user, function (err) {
                if (err) return next(err);
                return res.redirect('/');
            });
        }
    });
};



exports.logOut = function (req, res, next) {
    req.logout();
    res.redirect('/login');
};
