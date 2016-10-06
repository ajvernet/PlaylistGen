angular
    .module("podcaster")
    .factory("getDuration", getDuration)

function getDuration(directionsService) {
        directionsService.route({
          origin: document.getElementById('start').value,
          destination: document.getElementById('end').value,
          travelMode: 'DRIVING'
        }, function(response, status) {
          if (status === 'OK') {
           return response.routes[0].legs[0].duration.value           
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      }