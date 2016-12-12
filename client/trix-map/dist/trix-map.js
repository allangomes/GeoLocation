angular.module('trix-map', [])

function watch(item, key, scope) {
  scope.$watch(key, function(value, old) {
    if (old !== value) {
      var change = {}
      change[key] = value
      item.setStyle( change ) //TODO: Extrair essa linha para seus respectivos components
    }
  })
}

angular.module('trix-map').constant('utils', {

  watch: function(item, scope, options) {
    for (var key in options) {
      if (options.hasOwnProperty(key)) {
        watch(item, key, scope)
      }
    }
  },

  bind: function(item, scope, events) {
    for (var key in events) {
      if (events.hasOwnProperty(key)) {
        var handle = scope[key]
        if (handle)
          item.on(key, handle)          
      }
    }
    if (scope.$root.$$phase != '$apply' && scope.$root.$$phase != '$digest') {
        scope.$apply();
    }
  }
})

angular.module('trix-map').constant('mouseEvents', {
  scope: {
    click: '&?',
    ngClick: '&?',
    dblclick: '&?',
    ngDblclick: '&?',
    mousedown: '&?',
    mouseover: '&?',
    mouseout: '&?',
    contextmenu: '&?'
  }
})
angular.module('trix-map').constant('markerOptions', {
  scope: {
    icon: '=?',
    draggable: '=?',
    keyboard: '=?',
    title: '=?',
    alt: '=?',
    zIndexOffset: '=?',
    opacity: '=?',
    riseOnHover: '=?',
    riseOffset: '=?',
    pane: '=?'
  }
})

angular.module('trix-map').constant('pathOptions', {
  scope: {
    stroke: '=?',
    color: '=?',
    weight: '=?',
    opacity: '=?',
    lineCap: '=?',
    lineJoin: '=?',
    dashArray: '=?',
    dashOffset: '=?',
    fill: '=?',
    fillColor: '=?',
    fillOpacity: '=?',
    fillRule: '=?',
    className: '=?'
  }
})

angular.module('trix-map').provider('layersProvider', function() {
  var _layers = {
    google: {
      factory: function(params = {}) {
        if (parseInt(L.version) === 0)
          return new L.Google(params.type || 'ROADMAP')
        else
          return L.gridLayer.googleMutant({ type: params.type || 'roadmap' })
      }
    }
  }

  var _create = function(name, params) {
    return _layers[name].factory(params)
  }
  
  var _add = function(map, layer) {
    if (layer.addTo)
      layer.addTo(map)
    else
      map.addLayer(layer) 
  }

  this.layer = function(name, factory) {
    _layers[name] = { factory: factory }
  }

  this.$get = function() {
    return {
      create: _create,
      add: _add 
    }
  }
})

angular.module('trix-map').provider('marker', 
['utils','markerOptions','mouseEvents',
function(utils, markerOptions, mouseEvents) {
  var _options = { options: '=?' }
  _options = Object.assign(_options, markerOptions.scope)

  var _events = {}
  _events = Object.assign(_events, mouseEvents.scope)

  var _scope = Object.assign({}, _options, _events)

  var _optionsValue = { default: markerOptions.default }

  this.options = function(name, options) {
    _optionsValue[name] = options
  }

  this.$get = function() {
    return {
      scope: _scope,
      options: function(scope) {
        var optionsParam = scope.options || _optionsValue.default || {}
        if (!(optionsParam instanceof Object))
          optionsParam = _optionsValue[optionsParam]
        return optionsParam
      },
      watch: function(item, scope) {
        utils.watch(item, scope, _options)
      },
      events: function(item, scope) {
        utils.bind(item, scope, _events)
      }
    }
  }
}])
angular.module('trix-map').provider('polyline', 
['utils','pathOptions','mouseEvents',
function(utils, pathOptions, mouseEvents) {

  var _options = { options: '=' }
  _options = Object.assign(_options, pathOptions.scope)

  var _events = {}
  _events = Object.assign(_events, mouseEvents.scope)

  var _scope = Object.assign({}, _options, _events)

  var _optionsValue = { default: pathOptions.default }

  this.options = function(name, options) {
    _optionsValue[name] = options
    return this
  }

  this.$get = function() {
    return {
      scope: _scope,
      options: function(scope) {
        var optionsParam = scope.options || _optionsValue.default || {}
        if (!(optionsParam instanceof Object))
          optionsParam = _optionsValue[optionsParam]
        return optionsParam
      },
      watch: function(item, scope) {
        utils.watch(item, scope, _options)
      },
      events: function(item, scope) {
        utils.bind(item, scope, _events)
      }
    }
  }
}])
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