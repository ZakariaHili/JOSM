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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Ma Position</title>
<style media="screen" type="text/css">

.row{
  margin-top: 10%;
  margin-bottom: 30px
}

</style>
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
</head>
<body>



	<div class="container m-y-lg">
		<div class="row voffset2">
		<div class="text-center">

			
					<form action="SendGPS" method="post">
						<div class="form-group">
							<input type="text" placeholder="Id" name="Id" />
						</div>
						<div class="form-group">
							<input type="text" placeholder="Latitude" name="lat" />
						</div>
						<div class="form-group">
							<input type="text" placeholder="Longitude" name="lon" />
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-default">Envoyer</button>
						</div>
					</form>
				</div>
</div>
	</div>
	
	
	
</body>

</html>