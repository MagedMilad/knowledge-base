var User = require('../models/user');
var passport = require('passport');

exports.index = function (req, res) {
    User.find()
        .exec(function (err, users) {
            if (err) { return next(err); }
            res.render('users/index', {
                title: 'users index title',
                subject: 'users index',
                users: users
            });
        });
};

exports.show = function (req, res) {
    res.render('users/show', {
        title: 'users show title',
        subject: 'users show',
        user: req.user
    });
};

exports.create = function (req, res) {
    User.create(req.body, function (err, user) {
        if (err)
            res.send(err);
        else {
            res.redirect(`/users/${user._id}`);
        }
    });
};

exports.newForm = function (req, res) {
    res.render('users/new', {
        title: 'users form title',
        subject: 'users show'
    });
};


exports.idMiddleware = function (req, res, next) {
    var id = req.params.id;
    User.findById(id)
        .exec(function (err, user) {
            if (err) { return next(err); }
            req.user = user;
            next();
        });
};
