app.controller('formController', function($scope, $http) {

    $scope.formData = {};


    $scope.processNameForm = function() {
        $http({
            method  : 'POST',
            url     : 'searchEmployee',
            data    : $.param($scope.formData),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' }
        }).success(function(data) {
                console.log(data);

                $scope.nameSelects = [];
                angular.forEach(data, function(value, key) {
                    var name = '';

                    if(value.tissId !== undefined && value.tissId!=null){
                        name += value.tissId+' ';
                    }

                    if(value.precedingTitle !== undefined && value.precedingTitle !=null)
                        name += value.precedingTitle+' ';

                    name += value.firstName+' '+value.lastName;

                    if(value.postpositionedTitle !== undefined && value.postpositionedTitle !=null)
                        name += ', '+value.postpositionedTitle;

                    $scope.nameSelects.push({nameSelect:name, value:name});
                });

                $scope.nameSelect = $scope.nameSelects[0];

            }).error(function() {
                console.log("request failed...");

        });
    };


    $scope.processDataForm = function() {

        var data = new FormData();

        for(var i=0; i<document.getElementById('files').files.length; i++) {
            data.append('files', document.getElementById('files').files[i]);
        }


        $.ajax({
            url: "doUpload",
            type: "POST",
            data: data,
            processData: false,
            contentType: false,
            dataType: "json",
            xhr: function(){
                var xhr = $.ajaxSettings.xhr() ;
                xhr.upload.onprogress = function(evt){  } ;
                xhr.upload.onload = function(){ console.log('Upload done.') } ;
                return xhr ;
            },
            success: function(response) {
                angular.forEach(response, function(value, key) {
                    console.log(value);

                    $("#filesTable").append("<tr><td>"+value.name+"</td><td>"+value.size+"</td><td>"+value.type
                        +"</td><td><select><option>Output</option><option>Input</option></select>"
                        +"</td><td><input type='number' value='1' min='0' name='quantity[]'></td></tr>")



                });
            },
            error: function() { console.log("General error occured", "e"); },
            complete: function() {  }
        });

    };








});