var gulp          = require('gulp'),
    concat        = require('gulp-concat');

gulp.task('concat', function() {
  return gulp.src(gulp.paths.scripts.src)
    .pipe(concat(gulp.paths.scripts.dist))
    .pipe(gulp.dest(gulp.paths.dist))
});