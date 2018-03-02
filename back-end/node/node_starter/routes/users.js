var express = require('express');
var router = express.Router();

var userController = require('../controllers/userController');
var authController = require('../controllers/authController');

router.route('/')
    .all(authController.authenticatedMiddleware)
    .get(userController.index)
    .post(userController.create);

router.route('/new')
    .all(authController.authenticatedMiddleware)
    .get(userController.newForm);


router.route('/logout')
    .get(authController.logOut);

router.route('/:id')
    .all(authController.authenticatedMiddleware)
    .all(userController.idMiddleware)
    .get(userController.show);

module.exports = router;
