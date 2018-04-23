app.controller('licenseController', function($scope, $http) {


    $(function() {
        var clarinLs, ls = $('<p><a href="#!">Click to open selector</a></p>')
            .appendTo('body')
            .licenseSelector({
                showLabels : true,
                onLicenseSelected : function (license) {
                    $('body').append($('<pre></pre>').text(JSON.stringify(license, null, 4)))
                    console.log(license)
                }
            });

        // Add selector for CLARIN only if it exists
        if ($.fn.clarinLicenseSelector) {
            clarinLs = $('<p><a href="#!">Click to open selector for CLARIN</a></p>')
                .appendTo('body')
                .clarinLicenseSelector({
                    onLicenseSelected : function (license) {
                        $('body').append($('<pre></pre>').text(JSON.stringify(license, null, 4)))
                        console.log(license)
                    }
                });
        }

        if (clarinLs && window.location.hash == '#clarin') {
            clarinLs.click();
        } else {
            ls.click();
        }

        $('<p><a href="#!">Click to open selector with modified options</a></p>')
            .appendTo('body')
            .licenseSelector({
                licenses: {
                    'abc-license': {
                        name: 'ABC license',
                        priority: 1,
                        available: true,
                        url: 'http://www.example.com/abc-license',
                        description: 'This is ABC license inserted as a test',
                        template: function($el, license, selectFunction) {
                            var h = $('<h4 />').text(license.name);
                            h.append($('<a/>').attr({
                                href: license.url,
                                target: '_blank'
                            }));
                            //$el.click(selectFunction);
                            $el.append(h);
                            $el.append('<p>Custom template function</p>');
                            $el.append($('<button/>').append('<span>Click here to select license</span>').click(selectFunction));
                        },
                        categories: ['data', 'abc']
                    },
                    'cc-by': {
                        description: 'Modified description ...',
                        cssClass: 'cc-by'
                    }
                },
                start: 'DataCopyrightable',
                onLicenseSelected : function (license) {
                    $('body').append($('<pre></pre>').text(JSON.stringify(license, null, 4)))
                    console.log(license)
                }
            });
    });


});