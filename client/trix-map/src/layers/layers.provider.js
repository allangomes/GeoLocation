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
