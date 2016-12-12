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
