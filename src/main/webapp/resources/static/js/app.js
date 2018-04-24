var app = angular.module('app', ['ngRoute','ngResource', 'angular-loading-bar']);
app.config(function($routeProvider){
    $routeProvider
        .when('/form',{
            templateUrl: 'resources/static/views/form.html',
            controller: 'formController'
        })
        .when('/license',{
            templateUrl: 'resources/static/views/license.html',
            controller: 'licenseController'
        }).otherwise(
            { redirectTo: '/form'}
        );
});