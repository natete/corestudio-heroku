(function() {
  var showErrorsModule;

  showErrorsModule = angular.module('ui.bootstrap.showErrors', []);

  showErrorsModule.directive('showErrors', [
    '$timeout', 'showErrorsConfig', '$interpolate', '$compile', function($timeout, showErrorsConfig, $interpolate, $compile) {
      var getShowSuccess, getShowFeedback, getTrigger, linkFn;
      getTrigger = function(options) {
        var trigger;
        trigger = showErrorsConfig.trigger;
        if (options && (options.trigger != null)) {
          trigger = options.trigger;
        }
        return trigger;
      };
      getShowSuccess = function(options) {
        var showSuccess;
        showSuccess = showErrorsConfig.showSuccess;
        if (options && (options.showSuccess != null)) {
          showSuccess = options.showSuccess;
        }
        return showSuccess;
      };
      getShowFeedback = function (options) {
        var showFeedback;
        showFeedback = showErrorsConfig.showFeedback;
        if(options && options.showFeedback) {
          showFeedback = options.showFeedback;
        }
        return showFeedback;
      };
      linkFn = function(scope, el, attrs, formCtrl) {
        var blurred, inputEl, inputName, inputNgEl, options, showSuccess, showFeedback, toggleClasses, trigger;
        blurred = false;
        options = scope.$eval(attrs.showErrors);
        showSuccess = getShowSuccess(options);
        showFeedback = getShowFeedback(options);
        trigger = getTrigger(options);
        inputEl = el[0].querySelector('.form-control[name]');
        inputNgEl = angular.element(inputEl);
        inputName = $interpolate(inputNgEl.attr('name') || '')(scope);
        if (!inputName) {
          throw "show-errors element has no child input elements with a 'name' attribute and a 'form-control' class";
        }
        inputNgEl.bind(trigger, function() {
          blurred = true;
          return toggleClasses(formCtrl[inputName].$invalid);
        });
        scope.$watch(function() {
          return formCtrl[inputName] && formCtrl[inputName].$invalid;
        }, function(invalid) {
          if (!blurred) {
            return;
          }
          return toggleClasses(invalid);
        });
        scope.$on('show-errors-check-validity', function() {
          return toggleClasses(formCtrl[inputName].$invalid);
        });
        scope.$on('show-errors-reset', function() {
          return $timeout(function() {
            el.removeClass('has-error');
            el.removeClass('has-success');
            return blurred = false;
          }, 0, false);
        });
        return toggleClasses = function(invalid) {
          el.toggleClass('has-error', invalid);
          el.find('error-feedback').remove();
          el.find('success-feedback').remove();
          if(showFeedback) {
            if (invalid) {
              el.find('input').after($compile('<error-feedback></error-feedback>')({}));
            }
          }
          if (showSuccess) {
            if(!invalid) {
              el.find('input').after($compile('<success-feedback></success-feedback>')({}));
            }
            return el.toggleClass('has-success', !invalid);
          }
        };
      };
      return {
        restrict: 'A',
        require: '^form',
        compile: function(elem, attrs) {
          if (attrs['showErrors'].indexOf('skipFormGroupCheck') === -1) {
            if (!(elem.hasClass('form-group') || elem.hasClass('input-group'))) {
              throw "show-errors element does not have the 'form-group' or 'input-group' class";
            }
          }
          return linkFn;
        }
      };
    }
  ]);

  showErrorsModule.directive('errorFeedback', function() {
    return {
      restrict: 'E',
      link: function (scope, elem, attrs) {
        elem.addClass('fa fa-times form-control-feedback');
      }
    };
  });

  showErrorsModule.directive('successFeedback', function() {
    return {
      restrict: 'E',
      link: function (scope, elem, attrs) {
        elem.addClass('fa fa-check form-control-feedback');
      }

    };
  });

  showErrorsModule.provider('showErrorsConfig', function() {
    var _showSuccess, _trigger, _showFeedback;
    _showSuccess = false;
    _showFeedback = false;
    _trigger = 'blur';
    this.showSuccess = function(showSuccess) {
      return _showSuccess = showSuccess;
    };
    this.trigger = function(trigger) {
      return _trigger = trigger;
    };
    this.showFeedback = function (showFeedback) {
      return _showFeedback = showFeedback;
    }
    this.$get = function() {
      return {
        showSuccess: _showSuccess,
        showFeedback: _showFeedback,
        trigger: _trigger
      };
    };
  });

}).call(this);
