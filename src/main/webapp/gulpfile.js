// Include gulp
var gulp = require('gulp');

var sass = require('gulp-sass');
var browserSync = require('browser-sync');
var url = require('url');
var fs = require('fs');
var ngConstant = require('gulp-ng-constant');

gulp.task('styles', function() {
	gulp.src('assets/sass/**/*.scss')
	.pipe(sass().on('error', sass.logError))
	.pipe(gulp.dest('assets/styles/'));
});

gulp.task('watchSass', function() {
	gulp.watch('assets/sass/**/*.scss', ['styles']);
});

gulp.task('configConstants', function () {
	gulp.src('scripts/app/app.constants.json')
		.pipe(ngConstant({
			name: 'corestudioApp',
			wrap: "'use strict';\n\n<%= __ngModule %>",
		}))
		.pipe(gulp.dest('scripts/app'));
});

/**
 * Start Dev Server.
 */
gulp.task('serve', ['watchSass'], function () {
	browserSync.init([
		'./assets/styles/*.css',
		'./assets/js/**/*.js',
		'./scripts/**/*.js',
		'./scripts/**/*.html',
		'./index.html'
	], {
		server: {
			baseDir: "./",
			directory: false,
			index: 'index.html',
			middleware: function(req, res, next) {
				var defaultFile = "index.html";
				var fileName = url.parse(req.url);
				fileName = fileName.href.split(fileName.search).join("");
				var fileExists = fs.existsSync(__dirname + fileName);
				if (!fileExists && fileName.indexOf("browser-sync-client") < 0) {
					req.url = "/" + defaultFile;
				}
				return next();
			}
		},
		watchTask: true
	});
});
