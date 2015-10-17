// Include gulp
var gulp = require('gulp');

var sass = require('gulp-sass');

gulp.task('styles', function() {
	gulp.src('sass/**/*.scss')
	.pipe(sass().on('error', sass.logError))
	.pipe(gulp.dest('./css/'));
});

gulp.task('watchSass', function() {
	gulp.watch('sass/**/*.scss', ['styles']);
});
