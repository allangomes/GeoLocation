angular.module('trix.routes', [
    'trix-map',
    'ngRoute'
])

angular.module('trix.routes').config(['$routeProvider', function ($routeProvider) {

  $routeProvider
  .when('/', { 
    templateUrl: 'src/routes/routes.html', 
    controller: 'routesController'})
}])

angular.module('trix.routes').config(['polylineProvider', function (polylineConfig) {
  polylineConfig
  .options('default', {
    color: 'blue',
    weight: 10
  })
  .options('runned', {
    color: 'red',
    weight: 3
  })
}])