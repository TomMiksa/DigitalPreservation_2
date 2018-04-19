app.controller('chartController', function($scope, $http) {
    var stations;


    var layoutVienna = {

        xaxis: {
            title: 'Longitude'
        },

        yaxis: {
            title: 'Latitude'

        },

        title:'Metro Stations Vienna',

        width: 600,
        height: 600
    };

    var layoutBudapest = {

        xaxis: {
            title: 'Longitude'
        },

        yaxis: {
            title: 'Latitude'

        },

        title:'Metro Stations Budapest',

        width: 600,
        height: 600
    };

    var layoutViennaBudapest = {

        xaxis: {
            title: 'Longitude'
        },

        yaxis: {
            title: 'Latitude'

        },

        title:'Metro Stations Vienna Budapest Combined',

        width: 900,
        height: 900
    };

    var traceVienna = {
        x: [],
        y: [],
        mode: 'markers',
        type: 'scatter',
        text: [],
        name: 'Vienna'
    };


    var traceBudapest = {
        x: [],
        y: [],
        mode: 'markers',
        type: 'scatter',
        text: [],
        name: 'Budapest'
    };

    var traceBudapestOffset = {
        x: [],
        y: [],
        mode: 'markers',
        type: 'scatter',
        text: [],
        name: 'Budapest'
    };

    $http.get('http://localhost:8080/getStationsVienna').
    then(function(response) {
        stations = response.data;




        for (var i = 0; i < stations.length; i++) {
            traceVienna.x.push(stations[i].longitude);
            traceVienna.y.push(stations[i].latitude);
            traceVienna.text.push(stations[i].name);
        }

        var data = [ traceVienna ];

        Plotly.newPlot('viennaChart', data, layoutVienna);

    });

    $http.get('http://localhost:8080/getStationsBudapest').
    then(function(response) {
        stations = response.data;


        for (var i = 0; i < stations.length; i++) {
            traceBudapest.x.push(stations[i].longitude);
            traceBudapest.y.push(stations[i].latitude);
            traceBudapest.text.push(stations[i].name);
        }

        for (var i = 0; i < stations.length; i++) {
            traceBudapestOffset.x.push(stations[i].longitude-2.7);
            traceBudapestOffset.y.push(stations[i].latitude+0.7);
            traceBudapestOffset.text.push(stations[i].name);
        }

        var data = [ traceBudapest ];

        Plotly.newPlot('budapestChart', data, layoutBudapest);
        Plotly.newPlot('viennaBudapestOffsetChart', [ traceVienna, traceBudapestOffset ], layoutViennaBudapest);

    });




});