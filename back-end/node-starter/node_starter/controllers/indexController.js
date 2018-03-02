exports.login = function (req, res) {
    res.render('index/login', {
        title: 'users login title',
    });
};

exports.signUp = function (req, res) {
    res.render('index/signup', {
        title: 'users signup title',
    });
};

exports.home = function (req, res, next) {
    res.render('index/index', {
        title: 'Express',
        user: req.user
    });
};
