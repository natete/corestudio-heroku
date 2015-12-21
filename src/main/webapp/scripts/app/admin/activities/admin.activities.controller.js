/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('ActivitiesController', ActivitiesController);

    ActivitiesController.$inject = ['Activity', 'Alerts', '$scope'];

    function ActivitiesController(Activity, Alerts, $scope) {
        var vm = this;

        vm.data = [];
        vm.displayData = [].concat(vm.data);

        vm.addActivity = addActivity;
        vm.cancelEdition = cancelEdition;
        vm.saveActivity = saveActivity;
        vm.deleteActivity = deleteActivity;

        activate();

        /////////////////////////

        function activate() {
            vm.data = Activity.query();
        }

        function addActivity() {
            var activity = {
                activity: {},
                editing: true,
                isNew: true
            };

            vm.data.push(activity);
        }

        function cancelEdition(activity) {
            if (activity.isNew) {
                vm.data.splice(vm.data.indexOf(activity), 1);
            } else {
                activity.editing = false;
            }
        }

        function saveActivity(activity) {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.activitiesForm.$invalid) {
                Alerts.addErrorAlert('El formulario de creación contiene datos erróneos');
                return;
            }

            if (activity.isNew) {
                Activity.save(activity.activity, function (savedActivity) {
                    activity.activity.id = savedActivity.id;
                    activity.isNew = false;
                    activity.editing = false;
                    Alerts.addSuccessAlert('Se ha guardado la actividad ' + savedActivity.name);
                }, function () {
                    Alerts.addErrorAlert('Ha habido un error creando la actividad ' + activity.name);
                });
            } else {
                Activity.update(activity.activity, function (savedActivity) {
                    activity.isNew = false;
                    activity.editing = false;
                    Alerts.addSuccessAlert('Se ha actualizado la actividad ' + savedActivity.name);
                }, function () {
                    Alerts.addErrorAlert('Ha habido un error actualizando la actividad ' + activity.name);
                });
            }
        }

        function deleteActivity(activity) {
            var index = vm.data.indexOf(activity);
            Activity.delete({id: activity.activity.id}, function () {
                vm.data.splice(index, 1);
                Alerts.addSuccessAlert('Se ha eliminado la actividad ' + activity.name);
            }, function () {
                Alerts.addErrorAlert('Ha habido un error eliminando la actividad ' + activity.name);
            });
        }
    }

})();

