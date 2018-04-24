app.controller('formController', function($scope, $http) {

    $scope.formData = {};


    $scope.processForm = function() {
        $http({
            method  : 'POST',
            url     : 'searchEmployee',
            data    : $.param($scope.formData),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded' }
        }).success(function(data) {
                console.log(data);

                $scope.nameSelects = [];
                angular.forEach(data, function(value, key) {
                    var name = '';

                    if(value.precedingTitle != undefined)
                        name += value.precedingTitle+' ';

                    name += value.firstName+' '+value.lastName;

                    if(value.postpositionedTitle != undefined)
                        name += ', '+value.postpositionedTitle;

                    $scope.nameSelects.push({nameSelect:name, value:name});
                })

                $scope.nameSelect = $scope.nameSelects[0];


            /*if (!data.success) {
                $scope.errorName = data.errors.name;
            } else {
                $scope.message = data.message;
            }*/
            }).error(function() {
                console.log("request failed...");

        });
    };








});