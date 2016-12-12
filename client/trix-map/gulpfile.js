var gulp = require('gulp');
var requiredir = require('require-dir');

gulp.paths = {
  dist: 'dist',
  scripts: {
    src: [
      'src/module.js',
      'src/utils.js', 
      'src/**/*.events.js',
      'src/**/*.options.js',
      'src/**/*.provider.js',
      'src/**/*.directive.js'],
    dist: 'trix-map.js'
  }
};

requiredir('tasks')

gulp.task('compile', ['concat']);

gulp.task('watch', function() {
  gulp.watch('src/**/*.js', ['compile']);
});


gulp.task('default', ['compile','watch'])

