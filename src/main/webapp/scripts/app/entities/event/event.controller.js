'use strict';

angular.module('monApp')
    .controller('EventController', function ($scope, Event, ParseLinks) {
        $scope.eventSources = [];
        $scope.eventClick = function (calEvent, jsEvent, view) {
            $scope.update(calEvent.id);
        };
        $scope.uiConfig = {
            calendar: {
                height: 450,
                editable: false,
                header: {
                    left: 'title',
                    center: '',
                    right: 'today prev,next'
                },
                eventClick: $scope.eventClick
            }
        };
        $scope.load = function () {
            $scope.eventSources.splice(0, $scope.eventSources.length);
            Event.query({}, function (result) {
                $scope.events = result.map(function (event) {
                    return $scope.getEventDto(event);
                });
                $scope.eventSources.push($scope.events);
            });
        };

        $scope.save = function () {
            Event.update($scope.event,
                function () {
                    $scope.load();
                    $('#updateEventModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {

            Event.get({id: id}, function (event) {
                event.startAt = new Date(event.startAt);
                event.endAt = new Date(event.endAt);
                $scope.event = event;
                $('#updateEventModal').modal('show');
            });
        };

        $scope.getEventDto = function (event) {
            var eventDTO = {};
            eventDTO.id = event.id;
            eventDTO.start = new Date(event.start);
            eventDTO.end = new Date(event.end);
            eventDTO.title = event.title;
            return eventDTO;
        };

        $scope.load();

        $scope.clear = function () {
            $scope.event = {id: null, start: null, end: null, title: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
