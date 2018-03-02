var express = require('express');
var router = express.Router();

var userController = require('../controllers/userController');
var authController = require('../controllers/authController');
var indexController = require('../controllers/indexController');

router.route('/')
  .get(indexController.home);

router.route('/login')
  .get(indexController.login)
  .post(authController.signIn);

router.route('/signup')
  .get(indexController.signUp)
  .post(authController.signUp);

module.exports = router;
