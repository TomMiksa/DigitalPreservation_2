var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/chart',{
            templateUrl: 'resources/static/views/chart.html',
            controller: 'chartController'
        })
        .otherwise(
            { redirectTo: '/chart'}
        );
});