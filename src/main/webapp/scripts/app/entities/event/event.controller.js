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
                editable: false,
                header: {
                    left: 'title',
                    center: '',
                    right: 'today prev,next'
                }
            }
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
    });
