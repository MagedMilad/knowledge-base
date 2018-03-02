var mongoose = require('mongoose');
var bcrypt = require('bcrypt');
var config = require('../config/secrets');
var Schema = mongoose.Schema;

var UserSchema = new Schema(
  {
    username: { type: String, required: true, index: { unique: true } },
    password: { type: String, required: true },
  }
);

UserSchema.pre('save', function(next) {
  var user = this;
  if (!user.isModified('password')) return next();

  bcrypt.genSalt(config.SALT_WORK_FACTOR, function (err, salt) {
    if (err) return next(err);

    bcrypt.hash(user.password, salt, function (err, hash) {
      if (err) return next(err);

      user.password = hash;
      next();
    });
  });
});

UserSchema.methods.comparePassword = function (candidatePassword, cb) {
  bcrypt.compare(candidatePassword, this.password, function (err, isMatch) {
    if (err) return cb(err);
    cb(null, isMatch);
  });
};


UserSchema
  .virtual('name')
  .get(function () {
    return this.last_name + ', ' + this.first_name;
  });


UserSchema
  .virtual('url')
  .get(function () {
    return `/users/${this._id}`;
  });

module.exports = mongoose.model('User', UserSchema);