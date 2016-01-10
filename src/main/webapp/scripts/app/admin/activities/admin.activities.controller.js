/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 08/12/15.
 */
(function () {
    'use strict';

    angular.module('corestudioApp.admin')
        .controller('ActivitiesController', ActivitiesController);

    ActivitiesController.$inject = ['Activity', 'Alerts', '$scope', '$uibModal'];

    function ActivitiesController(Activity, Alerts, $scope, $uibModal) {
        var vm = this;

        vm.data = [];


        vm.search = search;
        vm.addActivity = addActivity;
        vm.cancelEdition = cancelEdition;
        vm.saveActivity = saveActivity;
        vm.deleteActivity = deleteActivity;
        vm.initEditing = initEditing;

        activate();

        /////////////////////////

        function activate() {
            //Activity.query({page: 0, size: 10}, function(data) {
            //    vm.data = data.content;
            //});
        }

        function search(tableState) {
            var pagination = tableState.pagination;
            var pageRequest = {};
            pageRequest.page = pagination.start ? (pagination.start + 1) % pagination.number : 0;
            pageRequest.size = pagination.number || 10;
            pageRequest.sortBy = tableState.sort.predicate;
            pageRequest.direction = tableState.sort.reverse ? 'DESC' : 'ASC';
            Activity.query(pageRequest, function(response) {
                vm.data = response.content;
                tableState.pagination.numberOfPages = response.totalPages;
            });
        }

        function addActivity() {
            var activity = {
                activity: {},
                editing: true,
                isNew: true
            };

            cancelAllEditing();
            vm.data.push(activity);
        }

        function initEditing(activity) {
            cancelAllEditing();
            activity.editing = true;
            activity.oldValue = angular.copy(activity.activity);
        }

        function cancelEdition(activity) {
            if (activity.isNew) {
                vm.data.splice(vm.data.indexOf(activity), 1);
            } else {
                activity.editing = false;
                if(activity.oldValue) {
                    activity.activity = activity.oldValue;
                }
            }
        }

        function cancelAllEditing() {
            vm.data.forEach(function (value) {
                cancelEdition(value);
            });
        }

        function saveActivity(activity) {
            $scope.$broadcast('show-errors-check-validity');

            if ($scope.activitiesForm.$invalid) {
                Alerts.addErrorAlert('El formulario de creación contiene datos erróneos');
                return;
            }

            if (activity.isNew) {
                Activity.save(activity.activity, function (responseData, headers) {
                    activity.activity.id = responseData.activity.id;
                    activity.isNew = false;
                    activity.editing = false;
                    Alerts.addHeaderSuccessAlert(headers());
                }, function (response) {
                    Alerts.addHeaderErrorAlert(response.headers());
                });
            } else {
                Activity.update(activity.activity, function (responseData, headers) {
                    activity.isNew = false;
                    activity.editing = false;
                    Alerts.addHeaderSuccessAlert(headers());
                }, function (response) {
                    Alerts.addHeaderErrorAlert(response.headers());
                });
            }
        }

        function deleteActivity(activity) {
            var modalInstance = $uibModal.open({
                templateUrl: 'scripts/components/modals/confirmModal.html',
                size: 'sm',
                controller: 'ConfirmModalController',
                controllerAs: 'modal',
                resolve: {
                    title: function () {
                        return 'Eliminar actividad';
                    },
                    message: function () {
                        return 'Está seguro de que desea eliminar la actividad ' + activity.activity.name;
                    }

                }
            });

            modalInstance.result.then(function (result) {
                if (result === 'OK') {
                    var index = vm.data.indexOf(activity);
                    Activity.delete({id: activity.activity.id}, function (responseData, headers) {
                        vm.data.splice(index, 1);
                        Alerts.addHeaderSuccessAlert(headers());
                    }, function (response) {
                        Alerts.addHeaderErrorAlert(response.headers());
                    });
                }
            });
        }
    }
})();
