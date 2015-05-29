'use strict';

angular.module('monApp')
    .controller('EventController', function ($scope, Event, ParseLinks) {
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
        $scope.eventSources = [];
        $scope.uiConfig = {
            calendar: {
                height: 450,
                editable: true,
                header: {
                    left: 'title',
                    center: '',
                    right: 'today prev,next'
                },
                eventClick: function(calEvent, jsEvent, view) {

                    $scope.eventToUpdate = [];
                    $scope.eventToUpdate.id = calEvent.id;
//                    $scope.eventToUpdate.start = getOnlyTime(new Date(calEvent.start));
//                    $scope.eventToUpdate.end = getOnlyTime(new Date(calEvent.end));
                    $scope.eventToUpdate.start = new Date(calEvent.start);
                    $scope.eventToUpdate.end = new Date(calEvent.end);
                    $scope.eventToUpdate.title = calEvent.title;
                    $('#updateEventModal').modal('show');
                }
            }
        };

        $scope.create = function () {
            $scope.updateEvent = angular.copy($scope.eventToUpdate);
            $scope.updateEvent.id = $scope.eventToUpdate.id;
            $scope.updateEvent.start = new Date($scope.eventToUpdate.start).getTime();
            $scope.updateEvent.end = new Date($scope.eventToUpdate.end).getTime();
            Event.update($scope.updateEvent,
                function () {
                    $scope.eventSources = [];
                    $scope.load();
                    $('#updateEventModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.load = function () {
            Event.query({}, function (result) {
                $scope.events = result.map(function (event) {
                    var eventDTO = {};
                    eventDTO.id = event.id;
                    eventDTO.start = new Date(event.start);
                    eventDTO.end = new Date(event.end);
                    eventDTO.title = event.title;
                    return eventDTO;
                });


                $scope.eventSources.push($scope.events);
            });
        };

        $scope.load();

        $scope.clear = function () {
            $scope.eventToUpdate = {id: null, start: null, end: null, title: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };


        function getOnlyTime(date) {
            var now     = date;
            var hour    = now.getHours();
            var minute  = now.getMinutes();

            if(hour.toString().length == 1) {
                var hour = '0'+hour;
            }
            if(minute.toString().length == 1) {
                var minute = '0'+minute;
            }

            var dateTime = hour+':'+minute;
            return dateTime;
        }
    });
