<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<html>
<head>
<meta charset="UTF-8">
<title>Login/Logout animation concept</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=yes">


<link rel='stylesheet prefetch'
	href='http://fonts.googleapis.com/css?family=Open+Sans'>

<link rel="stylesheet"
	href="<c:url value='/resources/Test/css/style.css'/>">



<script
	src="http://www.mapquestapi.com/sdk/leaflet/v2.s/mq-map.js?key=SNFcPuZBgGgCbS5lDZi4hPPsxdiyTGqa"></script>

</head>
<body>


	<div class="cont">


		<form method="post" action="SendGPS">

			<div class="form">
				<div class="text">Config.json</div>
				<div class="forceColor"></div>
				<div class="topbar">
					<div class="spanColor"></div>
					<input type="text" class="input" placeholder="Ip Address" />
				</div>
				<div class="text">Mysql</div>
				<div class="topbar">
					<div class="spanColor"></div>
					<input type="text" class="input" placeholder="Username" />
				</div>
				<div class="topbar">
					<div class="spanColor"></div>
					<input type="password" class="input" placeholder="Password" />
				</div>
				<div class="topbar">
					<div class="spanColor"></div>
					<input type="text" class="input" placeholder="Port" />
				</div>
				<button type="button" class="submit_" id="submit">Save</button>
			</div>



		</form>
	</div>
	<div class="app">
		<div class="text">Les informations sont bien enregistrés</div>
		<div class="forceColor"></div>
		
				<div class="app__logout">
        <svg class="app__logout-icon svg-icon" viewBox="0 0 20 20">
          <path d="M6,3 a8,8 0 1,0 8,0 M10,0 10,12"/>
        </svg>
      </div>
	</div>
	
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script src="<c:url value='/resources/Test/js/index.js'/>"></script>
</body>
</html>