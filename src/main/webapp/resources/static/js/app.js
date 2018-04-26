var app = angular.module('app', ['ngRoute','ngResource', 'angular-loading-bar']);

app.config(function($routeProvider){
    $routeProvider
        .when('/form',{
            templateUrl: 'resources/static/views/form.html',
            controller: 'formController'
        })
        .when('/#report',{
            templateUrl: 'resources/static/views/report.html',
            controller: 'reportController'
        }).otherwise(
            { redirectTo: '/form'}
        );
});