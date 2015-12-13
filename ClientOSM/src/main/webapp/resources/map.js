
var dir;
var marker;
var L=window.L;
window.map = L.map('map', {
			layers : MQ.mapLayer(),
			center : [ window.lat1, window.lon1 ],
			zoom : 14
		});

	dir = MQ.routing.directions();

    dir.route({
        locations: [
            { latLng: { lat: window.lat1, lng:window.lon1} },
            { latLng: { lat: window.lat2, lng:window.lon2 } }
        ]
    });
	
	
    map.addLayer(MQ.routing.routeLayer({
        directions: dir,
        fitBounds: true
    }));		
    
    
    var popup = L.popup();

    function onMapClick(e) {
        popup
            .setLatLng(e.latlng)
            .setContent("You clicked the map at " + e.latlng.toString())
            .openOn(map);
    }

    map.on('click', onMapClick);


        function printMarker(position){  
        if(marker!=undefined)   
        map.removeLayer(marker);  
        marker = L.marker([position[0], position[1] ]).addTo(map);
        marker.bindPopup("<b>Ma Position!</b>").openPopup();
        map.invalidateSize();
    }
    
    

    function getNewPosition() {
        $.ajax({
            url : 'getNewPosition',
            type:'GET',
            dataType:'JSON', 
            success : function(data) {
                      
                printMarker(data.position);
            }
        });
        setTimeout(getNewPosition, 10000);
    }