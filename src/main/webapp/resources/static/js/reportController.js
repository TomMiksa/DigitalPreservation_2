app.controller('reportController', function($scope, $http) {

    $( document ).ready(function() {
        $.ajax({
            url: "getReport",
            type: "POST",
            processData: false,
            contentType: false,
            dataType: "json",
            xhr: function () {
                var xhr = $.ajaxSettings.xhr();
                xhr.upload.onprogress = function (evt) {
                };
                xhr.upload.onload = function () {
                    console.log('Upload done.')
                };
                return xhr;
            },
            success: function (response) {
                angular.forEach(response, function (value, key) {
                    if (key == "files") {
                        angular.forEach(value, function(file, fileKey) {
                            console.log("file: "+file);

                            $("#filesTable").append("<tr><td><input disabled name='name[]' value='"+file.name+"'>"
                                +"</td><td><input disabled name='size[]' value='"+file.size+"'>"
                                +"</td><td><input disabled name='type[]' value='"+file.type+"'>"
                                +"</td><td><input disabled type='number' value='"+file.quantity+"' min='0' name='quantity[]'></td></tr>")

                        });

                    } else {
                        $('#' + key + '').text(value);
                    }
                    console.log(key + " - " + value);

                });
            },
            error: function () {
                console.log("error", "e");
            },
            complete: function () {
            }
        });


        var d = new Date();

        var month = d.getMonth()+1;
        var day = d.getDate();

        var output = d.getFullYear() + '/' +
            (month<10 ? '0' : '') + month + '/' +
            (day<10 ? '0' : '') + day;

        $('#date').text(output);
    });



});