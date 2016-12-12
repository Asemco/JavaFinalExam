<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<!--Import Google Icon Font-->
<link href="http://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!--Import materialize.css-->
<link type="text/css" rel="stylesheet" href="/spring/css/materialize.min.css"
	media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Final Forum - Register</title>
</head>
<body>
	<header>
		<nav class="top-nav">
			<div class="nav-wrapper">
				<c:url value="/" var="homeLink" />
				<h1 class="brand-logo center"><a href="${homeLink }">Register - Final Forum</a></h1>
			</div>
		</nav>
	</header>
	<main> 
		<div class="container">
			<c:url value="/register" var="registerUrl" />
			<h3 style="color: red" value="${error}"></h3>
			<form:form commandName="user" method="post" action="${registerUrl}">
				<form:hidden path="id" value="${user.id}" />
				<div class="input-field">
					<label for="username">Username</label>
					<form:input path="username" placeholder="username1234" required="required" />
				</div>
				<br />
				<div class="input-field">
					<label for="password">Password</label>
					<form:password path="password" placeholder="hunter2" required="required" />
				</div>
				<br />
				<input type="submit" class="btn waves-effect" value="Register!" />
			</form:form> 
			<c:if test="${error != null}">
				<span class="red-text">${error}</span>
			</c:if>
		</div>
	</main>
	<footer class="page-footer">
		<div class="container">
			<c:url value="/" var="homeUrl" />
			<a class="btn waves-effect" href="${homeUrl}">Go to Login</a>
			<c:url value="/displayTopics" var="topicsUrl" />
			<a class="btn waves-effect" href="${topicsUrl}">View Topics</a>
		</div>
	</footer>
	<script type="text/javascript" src="/spring/scripts/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="/spring/scripts/materialize.min.js"></script>
</body>
</html>