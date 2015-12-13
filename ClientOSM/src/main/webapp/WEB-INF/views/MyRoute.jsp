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
<link
	href="<c:url value='/resources/css/bootstrap/bootstrap.min.css' />"
	rel="stylesheet">
	<link href="<c:url value='/resources/css/bootstrap.min.css' />"
		rel="stylesheet">
		<link href="<c:url value='/resources/css/font-awesome.min.css' />"
			rel="stylesheet">
			<link href="<c:url value='/resources/css/prettyPhoto.css' />"
				rel="stylesheet">
				<link href="<c:url value='/resources/css/price-range.css' />"
					rel="stylesheet">
					<link href="<c:url value='/resources/css/animate.css' />"
						rel="stylesheet">
						<link href="<c:url value='/resources/css/main.css' />"
							rel="stylesheet">
							<link href="<c:url value='/resources/css/responsive.css' />"
								rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.5/leaflet.css" />

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.5/leaflet.js"></script>
<script
	src="https://www.mapquestapi.com/sdk/leaflet/v2.s/mq-map.js?key=Kmjtd%7Cluua2qu7n9%2C7a%3Do5-lzbgq"></script>
<script
	src="https://www.mapquestapi.com/sdk/leaflet/v2.s/mq-routing.js?key=Kmjtd%7Cluua2qu7n9%2C7a%3Do5-lzbgq"></script>

<style media="screen" type="text/css">

.margin{
 
 margin-left: 10%;
  
}

</style>
<title>Mes Coordonnées</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row voffset2">
			<div class="col-md-1">
				<form action="MakeRoute" method="post">
					<div class="form-group">
						<input type="text" placeholder="Id" name="Id" />
					</div>
					from:
					<div class="form-group">
						<input type="text" placeholder="Latitude" name="lat1" />
					</div>
					<div class="form-group">
						<input type="text" placeholder="Longitude" name="lon1" />
					</div>
					to:
					<div class="form-group">
						<input type="text" placeholder="Latitude" name="lat2" />
					</div>
					<div class="form-group">
						<input type="text" placeholder="Longitude" name="lon2" />
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-default">Envoyer</button>
					</div>
				</form>
				
			</div>
		<div class="col-md-9">	
<div class="margin"  id='map' style='width: 100%; height: 530px;'></div>
		</div>
		</div>
	</div>





</body>
<script type="text/javascript">
                    window.onload = function() {

                    	var map; 
        				
                     
                        map = L.map('map', {
                            layers: MQ.mapLayer(),    
                            center: [44.802895,  -0.610479 ],
                            zoom: 15
                        });
                        var basic = new MQA.Poi({ lat: 44.802895, lng: -0.610479 });
                       
                     // add POI to the map's default shape collection
                     map.addShape(basic);
                    
                 
            
         
                
            }
            
        </script>

</html>