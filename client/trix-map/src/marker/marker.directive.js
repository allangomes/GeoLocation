angular.module('trix-map').directive('marker', ['marker', function(marker) {

  return {
    restrict: 'E',
    replace: false,
    scope: marker.scope,
    require: ['^map', '?ngModel'],
    transclude: false,
    link: function(scope, element, attrs, ctrls) {
      var ctrl = ctrls[0]
      var ngModel = ctrls[1]

      if (!ngModel)
        return
      ctrl.getMap().then(function(map) {
        setTimeout(function() {
          var latLng = L.latLng(ngModel.$viewValue)
          var point = L.marker(latLng, marker.options(scope))
          point.addTo(map)
          marker.watch(point, scope)
          marker.events(point, scope)
        }, 100)
      }).catch(function(e) {
        console.error(`trix-map/directive/marker | ${e}`)
        throw e
      })
    }
  }
}])
