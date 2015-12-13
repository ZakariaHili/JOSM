<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
    <head>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.5/leaflet.css" />

        <script src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.5/leaflet.js"></script>
        <script src="https://www.mapquestapi.com/sdk/leaflet/v2.s/mq-map.js?key=Kmjtd%7Cluua2qu7n9%2C7a%3Do5-lzbgq"></script>
        <script src="https://www.mapquestapi.com/sdk/leaflet/v2.s/mq-routing.js?key=Kmjtd%7Cluua2qu7n9%2C7a%3Do5-lzbgq"></script>

        <script type="text/javascript">
                    window.onload = function() {

                var map,
                    dir;

                map = L.map('map', {
                    layers: MQ.mapLayer(),
                    center: [ 38.895345, -77.030101 ],
                    zoom: 15
                });

                dir = MQ.routing.directions();

                dir.route({
                    locations: [
                        { latLng: { lat: 38.895211, lng: -77.036495 } },
                        { latLng: { lat: 39.895211, lng: -77.036495 } }
                    ]
                });
				
				
                map.addLayer(MQ.routing.routeLayer({
                    directions: dir,
                    fitBounds: true
                }));
                clacRes(dir.routeData);
            }
            
        </script>
        <script>
  function clacRes(res) {
    // when this function is called at client side you get the resolution

    //var res = 100;

    document.getElementById("resolution").value = res;
  }
</script>
        
    </head>

    <body style='border:0; margin: 0'>
        <div id='map' style='width:1100px; height:530px;'></div>
        <input type="text" name="resolution" id="resolution" value="">
        
    </body>
</html>