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


    $scope.processFileOptionsForm = function() {

        var $inputs = $('#fileOptionsForm :input,selection');
        var data = [];
        var i = -1;
        $inputs.each(function() {
            var name = this.name;
            name = name.replace("[]", "");

            console.log(name + " " + $(this).val());
            if(name == "name"){
                i++;
                data[i] = {
                    "name" : $(this).val(),
                    "type" : "",
                    "size" : "",
                    "isOutput" : "",
                    "quantity" : ""
                };
            } else if(name != "") {
                data[i][name] = $(this).val();
            }
        });

        $http({
            method  : 'POST',
            url     : 'setFileOptions',
            data    : data,
            headers : { 'Content-Type': 'application/json' }
        }).success(function(data) {
            console.log(data);

            $scope.repoSelects = [];
            angular.forEach(data, function(value, key) {
                var name = '';
                name += value.name;
                if(value.url !== undefined && value.url!=null){
                    name+=': '+value.url;
                }

                $scope.repoSelects.push({repoSelect:name, value:name});
            });

            $scope.repoSelect = $scope.repoSelects[0];


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

                    $("#filesTable").append("<tr><td><input disabled name='name[]' value='"+value.name+"'>"
                        +"</td><td><input disabled name='size[]' value='"+value.size+"'>"
                        +"</td><td><input disabled name='type[]' value='"+value.type+"'>"
                        +"</td><td><select name='output[]'><option value='true'>Output</option><option value='false'>Input</option></select>"
                        +"</td><td><input type='number' value='1' min='0' name='quantity[]'></td></tr>")

                });
            },
            error: function() { console.log("error", "e"); },
            complete: function() {  }
        });

    };








});