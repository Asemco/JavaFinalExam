<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<title>Final Forum</title>
</head>
<body>
	<header>
		<nav class="top-nav">
			<div class="nav-wrapper">
				<c:url value="/" var="homeLink" />			
				<h1 class="brand-logo center"><a href="${homeLink }">Final Forum</a></h1>
			</div>
		</nav>
	</header>
	<main>
	<c:url value="/displayTopics" var="topicsUrl" />
		<div class="container">
		<c:choose>
				<c:when test="${authenticated}">
				
					<h2 style="text-align:center">Hello ${username}.</h2>
					<div class="row">
						<a class="btn waves-effect col s6 offset-s3" href="${topicsUrl}">Click here to view all Topics</a>
					</div>
				</c:when>
				<c:otherwise>
					<h3 class="center">Please login if you wish to post or reply.</h3>
			<c:url value="/login" var="loginUrl" />
			<h3 style="color:red" value="${error}"></h3>
			<form:form commandName="user" method="post" action="${loginUrl}">
				<form:hidden path="id" value="${user.id}"/>
				<div class="input-field">
					<form:input path="username" placeholder="username1234" required="required"/><br />
					<label for="username">Username</label>
				</div>
				<div class="input-field">
					<form:password path="password" placeholder="hunter2" required="required"/><br />
					<label for="password">Password</label>
				</div>
				<input type="submit" class="btn waves-effect" value="Login!"/>
			</form:form>
			<c:if test="${error != null}">
				<span class="red-text">${error}</span>
			</c:if>
			<c:if test="${success != null}">
				<span class="green-text">${success}</span>
			</c:if>
				</c:otherwise>
			</c:choose>
		</div>
	</main>
	<footer class="page-footer">
		<div class="container">
			<c:url value="/register" var="registerUrl" />
			<a class="btn waves-effect" href="${registerUrl}">Register Here</a>
			<c:choose>
				<c:when test="${authenticated}">
					<c:url value="/logout" var="logoutUrl" />
					<a class="btn waves-effect" href="${logoutUrl}">Logout</a>
				</c:when>
				<c:otherwise>
					<c:url value="/" var="loginUrl" />
					<a class="btn waves-effect" href="${loginUrl}">Login</a>
				</c:otherwise>
			</c:choose>
			<a class="btn waves-effect" href="${topicsUrl}">View Topics</a>
		</div>
	</footer>
	<script type="text/javascript" src="/spring/scripts/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="/spring/scripts/materialize.min.js"></script>
</body>
</html>