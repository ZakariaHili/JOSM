<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<html>
<head>
<meta charset="UTF-8">
<title>Config Default Server</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=yes">


<link rel='stylesheet prefetch'
	href='http://fonts.googleapis.com/css?family=Open+Sans'>

<link rel="stylesheet"
	href="<c:url value='/resources/Test/css/style.css'/>">

<style>
	div.app{
		position:absolute;
		-ms-transform: translate(-50%,-50%); /* IE 9 */
    	-webkit-transform: translate(-50%,-50%); /* Chrome, Safari, Opera */
    	transform: translate(-50%,-50%);
		width:70%;
		padding:10px;
		background:rgba(0,0,0,0.6);
		color:white;
		top: 50%;
		left: 50%;
		width:350px;
		height: 100px;
		text-align: center;
		line-height: center;
	}
	div.app.active .text{
		line-height:30px;
		color:white;
		font-weight:normal;
		
	}
</style>
<style>
 	h1{
		color:white;
		text-align:center;
		font-family: 'Open Sans', sans-serif;
		background:black;
		padding:1%;
		-moz-border-radius: 5px;
		-webkit-border-radius: 5px;
		border-radius: 5px;
	}
 	textarea{
 		width:90%;
 		height:50vh;
 		margin-left:auto;
 		margin-right:auto;
 		display:block;
 	}
 	button{
 		background: black;
 		color:white;
 		padding:10px;
 		padding-left:30px;
 		padding-right:30px;
 		display:block;
 		margin-left:auto;
 		margin-right:auto;
 		margin-top:2%;
 	}
 	#error,#success{
 		display:none;
 		margin-top:2%;
 		width:90%;
 		margin-left:auto;
 		margin-right:auto;
 		color:red;
 		text-align:center;
 	}
 	#success{
 		color:green;
 	}
 </style> 

</head>
<body>


	<div class="cont">


		<form method="post" id="send">

			<div class="form">
				<div class="text">Default Server</div>
				<div class="forceColor"></div>
				<div class="topbar">
					<div class="spanColor"></div>
					<input type="text" id="default_server" class="input" name="defaultserver" placeholder="Ip Address" />
				</div>
				
				<button type="button" class="submit_" id="submit">Save</button>
			</div>
		</form>
	</div>
	<div class="app">
		<div class="forceColor"></div>
		<div class="text">The new settings were well saved</div>
		
		
				
	</div>
	
	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

	<script src="<c:url value='/resources/Test/js/index.js'/>"></script>
</body>
</html>