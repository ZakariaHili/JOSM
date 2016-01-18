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
			<li class="current"><a href="">My Position</a></li>
			<li><a href="MyRoute">My Route</a></li>

		</ul>
		</nav>



		<!-- Logo -->




		<!-- Search -->
		<section class="box">
		<form method="post" action="SendGPS">
			<ul>
				<li><input type="text" placeholder="Id" name="Id" /></li>
				<ul><br> 
					<li><input id="lat" type="text" placeholder="Latitude" name="lat" /></li>
					<ul><br> 
						<li><input id="lon" type="text" placeholder="Longitude" name="lon" />
						</li><br> 
						
								
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
        				var i=0;
                     
                        map = L.map('map', {
                            layers: MQ.mapLayer(),    
                            center: [44.802895,  -0.610479 ],
                            zoom: 15
                        });
                        var icon = L.icon({
                            iconUrl: "<c:url value='/resources/images/marker-icon.png'/>",
                            shadowUrl: "<c:url value='/resources/images/marker-shadow.png'/>",

                            iconSize:     [60, 60], // size of the icon
                            shadowSize:   [0, 0], // size of the shadow
                            iconAnchor:   [30,60], // point of the icon which will correspond to marker's location
                            shadowAnchor: [4, 62],  // the same for the shadow
                            popupAnchor:  [0, -60] // point from which the popup should open relative to the iconAnchor
                        });
                        var marker1;
                        
                        function onMapClick(e) {
                            if(i==1){
                            	
                            		map.removeLayer(marker1); 
                            		
                            }
                            
                            	 marker1 = L.marker([e.latlng.lat, e.latlng.lng],{icon: icon}).addTo(map);
                                marker1.bindPopup("<b>Send this Position!</b>").openPopup();
                                document.getElementById("lat").value = e.latlng.lat;
                                document.getElementById("lon").value = e.latlng.lng;
                                i=1;
                            
                            
                        }

                        map.on('click', onMapClick);
                        //var basic = new MQA.Poi({ lat: 44.802895, lng: -0.610479 });
                       
                     // add POI to the map's default shape collection
                     //map.addShape(basic);
                    
                 
            
         
                
            }
                   
            
        </script>
</body>
</html>