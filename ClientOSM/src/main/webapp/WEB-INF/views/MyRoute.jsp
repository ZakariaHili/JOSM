<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:h="http://java.sun.com/jsf/html">
<head>
<link rel="stylesheet"
	href="<c:url value='/resources/leaflet.css'/>" />

<script
	src="<c:url value='/resources/leaflet.js'/>"></script>
<script
	src="https://www.mapquestapi.com/sdk/leaflet/v2.s/mq-map.js?key=Kmjtd%7Cluua2qu7n9%2C7a%3Do5-lzbgq"></script>
<script
	src="https://www.mapquestapi.com/sdk/leaflet/v2.s/mq-routing.js?key=Kmjtd%7Cluua2qu7n9%2C7a%3Do5-lzbgq"></script>

<link rel="shortcut icon"
 href="<c:url value='/resources/favicon.ico'/>"   type="image/x-icon"/>
 
 <link rel="icon" href="<c:url value='/resources/favicon.ico'/>" type="image/x-icon">

<style type="">
#map > div.leaflet-control-container > div.leaflet-bottom.leaflet-left{
display: none;
}
#map > div.leaflet-control-container > div.leaflet-bottom.leaflet-right > div{

display:none;}
body {
    overflow-y:hidden;
}

</style>
<title>My Route</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="<c:url value='/resources/assets/js/ie/html5shiv.js'/>"></script><![endif]-->
<link rel="stylesheet"
	href="<c:url value='/resources/assets/css/main.css '/>" />
<!--[if lte IE 8]><link rel="stylesheet" href="<c:url value='/resources/assets/css/ie8.css '/>"/><![endif]-->
</head>
<body>

	<!-- Content -->
	<div id="content">

		<div id='map' style='width: 100%; height: 100vh;'></div>


	</div>

	<!-- Sidebar -->
	<div id="sidebar">
		<!-- Nav -->
		<img src="<c:url value='/resources/assets/css/images/logo.png'/>" style='display: block;
    margin-left: auto;
    margin-right: auto;
     width: 100px; height: 100px;' alt="" />
		<nav id="nav">
		<ul>
			<li ><a href="MyPosition">My Position</a></li>
			<li class="current"><a href="MyRoute">My Route</a></li>

		</ul>
		</nav>



		<!-- Logo -->




		<!-- Search -->
		<section class="box">
		<form method="post" action="MakeRoute">
			
				<input type="text" placeholder="Id" name="Id" />
					
					from:
					
						<input type="text" placeholder="Latitude" name="lat1" id="lat1" />
					<br>
						<input type="text" placeholder="Longitude" name="lon1" id="lon1" />
					
					to:
					
						<input type="text" placeholder="Latitude" name="lat2" id="lat2" />
					<br>
						<input type="text" placeholder="Longitude" name="lon2" id="lon2" />
						
						<br>
								
									<input  type="submit" />
								
							
		</form>
		</section>




	</div>

	<!-- Scripts -->
	<script src="<c:url value='/resources/assets/js/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/assets/js/skel.min.js'/>"></script>
	<script src="<c:url value='/resources/assets/js/util.js'/>"></script>
	<!--[if lte IE 8]><script src="<c:url value='/resources/assets/js/ie/respond.min.js'/>"></script><![endif]-->
	<script src="<c:url value='/resources/assets/js/main.js'/>"></script>
	<script type="text/javascript">
                    window.onload = function() {

                    	var map; 
        				
                     
                        map = L.map('map', {
                            layers: MQ.mapLayer(),    
                            center: [44.802895,  -0.610479 ],
                            zoom: 15
                        });
                        
                        //var basic = new MQA.Poi({ lat: 44.802895, lng: -0.610479 });
                       
                     // add POI to the map's default shape collection
                     //map.addShape(basic);
                     var icon = L.icon({
                iconUrl: "<c:url value='/resources/images/marker-icon.png'/>",
                shadowUrl: "<c:url value='/resources/images/marker-shadow.png'/>",

                iconSize:     [60, 60], // size of the icon
                shadowSize:   [0, 0], // size of the shadow
                iconAnchor:   [30,60], // point of the icon which will correspond to marker's location
                shadowAnchor: [4, 62],  // the same for the shadow
                popupAnchor:  [0, -60] // point from which the popup should open relative to the iconAnchor
            });

                        

                      
                        var marker2,marker1,i=0;
                        function onMapClick(e) {
                            if(i==0){
                            	if(marker1!=undefined)   {
                            		map.removeLayer(marker1); 
                            		map.removeLayer(marker2); }
                            	
                            	 marker1 = L.marker([e.latlng.lat, e.latlng.lng],{icon: icon}).addTo(map);
                                marker1.bindPopup("<b>start Position!</b>").openPopup();
                                document.getElementById("lat1").value = e.latlng.lat;
                                document.getElementById("lon1").value = e.latlng.lng;
                                i=1;
                                
                            }
                            else{
                            	 marker2 = L.marker([e.latlng.lat, e.latlng.lng],{icon: icon}).addTo(map);
                                marker2.bindPopup("<b>final Position!</b>").openPopup();
                                document.getElementById("lat2").value = e.latlng.lat;
                                document.getElementById("lon2").value = e.latlng.lng;

                                i=0;
                            	
                            }
                        }
            
         
                        map.on('click', onMapClick);
            }
            
        </script>
</body>
</html>