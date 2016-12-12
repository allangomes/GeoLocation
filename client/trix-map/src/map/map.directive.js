angular.module('trix-map').directive('map', ['$q', function($q) {
  return {
    restrict: 'E',
    replace: true,
    scope: {
      layers: '=',
      view: '=',
      zoom: '='
    },
    transclude: true,
    template: '<div class="trix-map"><div ng-transclude></div></div>',
    controller: function($scope) {
      this._map = $q.defer();

      this.getMap = function() {
        return this._map.promise
      }
    },
    link: function(scope, element, attrs, ctrl) {      
      element.css("position", "absolute")
      element.css("height", "100%")
      element.css("width", "100%")
      var map = new L.Map(element[0])
      ctrl._map.resolve(map)
    }
}}])
