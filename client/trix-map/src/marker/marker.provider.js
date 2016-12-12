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