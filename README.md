# GeoLocation
Spring Boot | Mongo DB | Kotlin | Angular JS | Leaflet | Custom JS Plugin



# REST SERVER

### Routes
Create New Route
```http
POST http://localhost:8080/routes/
{
	"name": "Rota Principal",
	"vehicleId": 4,
	"routeDate": "2016-12-13",
	"stops": [
		{ "name": "Inicio", "coordinate": { "lat": -3.784426, "lng": -38.583058 } },
		{ "name": "Parada", "coordinate": { "lat": -3.742261, "lng": -38.499705 } },
		{ "name": "Final", "coordinate": { "lat": -3.737121, "lng": -38.575313 } }
	]
}

```
Get All Routes
```http
GET http://localhost:8080/routes/
```
Get Route Path/Polyline
```
GET http://localhost:8080/routes/{routeId}/path
```
Check run away and nearby point
```
POST http://localhost:8080/routes/vehicle/{vehicleId}/checkpoint?lat=-3.726636&lng=-38.508690
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
