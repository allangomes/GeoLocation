# GeoLocation
Spring Boot | Mongo DB | Kotlin | Angular JS | Leaflet | Custom JS Plugin



# REST SERVER

### Routes
Get All Routes
```http
http://localhost:8080/routes/
```
Get Route Path/Polyline
```
http://localhost:8080/routes/{routeId}/path
```
Check run away and nearby point
```
http://localhost:8080/routes/vehicle/{vehicleId}/checkpoint?lat=-3.726636&lng=-38.508690
```

# Front

start
```http
$ npm start

http://localhost:8001/
```

### Plugin


customize polyline | below example: Customized Profiles default and runned
```javascript
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
```


#### ARQUITETURA


Baseada na arquitetura do Leaflet, Extremamente fácil de mapear com a biblioteca
