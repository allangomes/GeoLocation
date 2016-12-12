//TODO: restrict: 'AE' com suporte a multiplus layears
angular.module('trix-map').directive('layers', ['layersProvider', function(layersProvider) {
  return {
    restrict: 'A',
    scope: false,
    replace: false,
    require: 'map',
    transclude: false,
    link: function(scope, element, attrs, ctrl) {
      ctrl.getMap().then(function(map) {
        var layers = layersProvider.create(attrs.layers)
        layersProvider.add(map, layers)
      }).catch(function(e) {
        console.error(`trix-map/directive/layers | ${e}`)
        throw e
      })
    }
  }
}])