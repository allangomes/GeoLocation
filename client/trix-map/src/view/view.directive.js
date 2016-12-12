angular.module('trix-map').directive('view', [function() {
  return {
    restrict: 'A',
    scope: false,
    replace: false,
    require: 'map',
    transclude: false,
    link: function(scope, element, attrs, ctrl) {
      ctrl.getMap().then(function(map) {
        map.setView(JSON.parse(attrs.view), attrs.zoom, {})
      }).catch(function(e) {
        console.error('trix-map/directive/view')
        throw e
      })
    }
  }
}])