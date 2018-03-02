exports.PORT = process.env.PORT || 3000;
exports.DATABASE_URL = process.env.DATABASE_URL || 'mongodb://127.0.0.1:27017/node_starter';
exports.SALT_WORK_FACTOR = 10;
exports.SESSION_SECRET = 'node_starter';