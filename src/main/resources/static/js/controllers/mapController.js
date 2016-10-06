angular
    .module("podcaster")
    .controller("MapController", MapController)      
MapController.$inject = ['$state', 'getDuration']

function MapController('$state', 'getDuration'){
    
    var ctrl = this
    
    var directionsService = new google.maps.DirectionsService;
    var directionsDisplay = new google.maps.DirectionsRenderer;
    
    var duration = ctrl.tripDuration
    
    var map = new google.maps.Map(ctrl.map, {
          zoom: 7,
          center: {lat: 41.85, lng: -87.65}
        });
        directionsDisplay.setMap(map);

        var onChangeHandler = function() {
          duration = calculateAndDisplayRoute(directionsService, directionsDisplay);
        };
    
        ctrl.start.addEventListener('change', onChangeHandler);
        ctrl.end.addEventListener('change', onChangeHandler);
      }