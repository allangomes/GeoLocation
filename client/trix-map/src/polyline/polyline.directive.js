angular.module('trix-map').directive('polyline', 
['polyline', function(polyline) {
  return {
    restrict: 'E',
    replace: false,
    require: ['^map', '?ngModel'],
    scope: polyline.scope,
    transclude: false,
    template: '<div></div>',
    link: function(scope, element, attrs, ctrls) {
      var ctrl = ctrls[0]
      var modelCtrl = ctrls[1]
      
      if (!modelCtrl)
        return

      ctrl.getMap().then(function(map) {
        setTimeout(function() {
          var line = L.polyline(modelCtrl.$viewValue, polyline.options(scope))
          polyline.watch(line, scope)
          polyline.events(line, scope)
          line.addTo(map)
        }, 100)
      }).catch(function(e) {
        console.error('trix-map/directive/polyline')
        throw e
      })
    }
  }
}])