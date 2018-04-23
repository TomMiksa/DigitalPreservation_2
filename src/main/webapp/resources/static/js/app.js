var app = angular.module('app', ['ngRoute','ngResource']);
app.config(function($routeProvider){
    $routeProvider
        .when('/license',{
            templateUrl: 'resources/static/views/license.html',
            controller: 'licenseController'
        })
        .when('/upload', {
            templateUrl: 'resources/static/views/upload.html'
        }).otherwise(
            { redirectTo: '/'}
        );
});