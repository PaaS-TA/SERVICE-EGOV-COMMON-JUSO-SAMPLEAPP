'use strict';

/* App Module */

var sampleApp = angular.module('sampleApp', [
  'ngRoute',
  'ngResource',

  'sampleControllers'
]);

sampleApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/main', {
        templateUrl: 'partials/main.html',
        controller: 'MainCtrl'
      }).
      when('/dorojuso', {
        templateUrl: 'partials/doroJusoList.html',
        controller: 'DoroJusoCtrl'
      }).    
      when('/dorojusoDB', {
          templateUrl: 'partials/doroJusoDBList.html',
          controller: 'DoroJusoDBCtrl'
      }).      
      when('/dorojusoDBMgt', {
          templateUrl: 'partials/doroJusoDBMgtList.html',
          controller: 'DoroJusoDBMgtCtrl'
      }).      
      when('/dorojusoDBMgt/:no', {
          templateUrl: 'partials/doroJusoDBMgtForm.html',
          controller: 'DoroJusoDBMgtFormCtrl'
      }).      
      otherwise({
        redirectTo: '/main'
      });
  }]);

sampleApp.config(['$httpProvider', function($httpProvider) {

        $httpProvider.defaults.useXDomain = true;

        delete $httpProvider.defaults.headers.common['X-Requested-With'];

    }
]);