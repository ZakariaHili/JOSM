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
 href="<c:url value='/resources/favicon.ico'/>" />

<style type="">
#map > div.leaflet-control-container > div.leaflet-bottom.leaflet-left{
display: none;
}
#map > div.leaflet-control-container > div.leaflet-bottom.leaflet-right > div{

display:none;
}
body {
    overflow-y:hidden;
}

</style>
<script type="text/javascript">
	var map;
	var lati, lon,speed,code,codeMap;
	<%
	String lat1 = request.getParameter("lat1");
	String Id = request.getParameter("Id");
	String lat2 = request.getParameter("lat2");
	String lon1 = request.getParameter("lon1");
	String lon2 = request.getParameter("lon2");
	//String v = request.getParameter("speed");
	//String codeMap = request.getParameter("codeMapQuest");
	//int speed = Integer.valueOf( v);
	
	%>
	
	speed=${speed};
	CodeMapQuest=${CodeMapQuest};
	
	lat1 =<%=lat1%>
			lon1 =
		<%=lon1%>
			lat2 =
		<%=lat2%>
			lon2 =
		<%=lon2%>

			
			var icon = L.icon({
                iconUrl: "<c:url value='/resources/images/marker-icon.png'/>",
                shadowUrl: "<c:url value='/resources/images/marker-shadow.png'/>",

                iconSize:     [60, 60], // size of the icon
                shadowSize:   [0, 0], // size of the shadow
                iconAnchor:   [30,60], // point of the icon which will correspond to marker's location
                shadowAnchor: [4, 62],  // the same for the shadow
                popupAnchor:  [0, -60] // point from which the popup should open relative to the iconAnchor
            });
			</script>


<title>Mes Coordonnées</title>
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
		<img src="<c:url value='/resources/assets/css/images/logo.PNG'/>" style='display: block;
    margin-left: auto;
    margin-right: auto;
     width: 100px; height: 100px;' alt="" />
		<nav id="nav">
		<ul>
			<li ><a href="MyPosition">My Position</a></li>
			<li class="current"><a href="MyRoute">My Route</a></li>

		</ul>
		</nav>



		<!-- Logo --

<div id="codeError" class="code"><script type=""> No server support this area
</script> </div>


		<!-- Search -->
		<section class="box">
		<form method="post" action="MakeRoute">
			<ul>
				<input type="text" value="<%=Id%>" name="Id" />
				
					from:
					
						<input type="text" value="<%=lat1%>" name="lat1" />
					<br>
						<input type="text" value="<%=lon1%>" name="lon1" />
					
					to:
					
						<input type="text" value="<%=lat2%>" name="lat2" />
					<br>
						<input type="text" value="<%=lon2%>" name="lon2" />
						<br>
								
									<input  type="submit" />
								
							
		</form>
		</section>
		
		


	</div>
<div class="notification_box" id="notification404">
  <div class="inner">
		<p>Ouups, No Server Control this Area !!</p>
	</div>
</div>
<div class="notification_box" id="notification303">
  <div class="inner">
		<p>We will redirect to a new server</p>
	</div>
</div>
<div class="notification_box" id="notificationMap">
  <div class="inner">
		<p>Ouups,No Route found !!</p>
	</div>
</div>
	<!-- Scripts -->
	<script src="<c:url value='/resources/assets/js/jquery.min.js'/>"></script>
	<script src="<c:url value='/resources/assets/js/skel.min.js'/>"></script>
	<script src="<c:url value='/resources/assets/js/util.js'/>"></script>
	<!--[if lte IE 8]><script src="<c:url value='/resources/assets/js/ie/respond.min.js'/>"></script><![endif]-->
	<script src="<c:url value='/resources/assets/js/main.js'/>"></script>
	<script type="">

setTimeout(function(){getNewPosition();},speed);


</script>



<script src="<c:url value='/resources/map.js'/>"></script>



</body>
</html>