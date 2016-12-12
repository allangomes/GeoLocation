var gulp = require('gulp');
var requiredir = require('require-dir');

gulp.paths = {
  dist: 'dist',
  scripts: {
    src: [
      'src/*.js',
      'src/**/*.js'], 
    dist: 'application.js'
  }
};

requiredir('tasks')

gulp.task('compile', ['concat']);

gulp.task('watch', function() {
  gulp.watch('src/**/*.js', ['compile']);
});


gulp.task('default', ['compile','watch'])


