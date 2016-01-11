

var dir;
var marker;
var L=window.L;
window.map = L.map('map', {
	layers : MQ.mapLayer(),
	center : [ window.lat1, window.lon1 ],
	zoom : 14
});


if(CodeMapQuest==200){
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
}

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
	marker = L.marker([position[0], position[1] ],{icon: window.icon}).addTo(map);
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
			getCode();
		}
	});
	setTimeout(getNewPosition, speed);
}

function getCode() {
	$.ajax({
		url : 'getCode',
		type:'GET',
		dataType:'text', 
		success : function(data) {
		
			if(data=='404'){
				$("#notification404").show("slow");
				setTimeout(function() { $("#notification404").hide("slow"); }, speed*0.7);
			}
			else 
			if(data=='303'){
				$("#notification303").show("slow");
				setTimeout(function() { $("#notification303").hide("slow"); }, speed*0.7);
			}
			else if(CodeMapQuest!==200)
				{
				$("#notificationMap").show("slow");
				setTimeout(function() { $("#notificationMap").hide("slow"); }, speed*0.7);
				}
		}

	});
	// setTimeout(getNewPosition, speed);
}
