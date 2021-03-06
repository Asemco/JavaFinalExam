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
<title>Add a Final Forum Post</title>
</head>
<body>
	<header>
		<nav class="top-nav">
			<div class="nav-wrapper">
				<c:url value="/" var="homeLink" />
				<h1 class="brand-logo center"><a href="${homeLink }">Add a Final Forum Post</a></h1>
			</div>
		</nav>
	</header>
	<main>
		<div class="container">
			<c:url value="/displayPosts/${topic}/savePost" var="saveUrl" />
			<form:form commandName="post" method="post" action="${saveUrl}">
				<form:hidden path="id"/>
				<div class="input-field">
					<label for="name">Subject</label>
					<form:input path="name" required="required"/>
				</div>
				<div class="input-field">
					<label for="message">Message</label>
					<form:textarea path="message" required="required"/>
				</div>
				<br />
				<input type="submit" class="btn waves-effect" value="Save Post!" />
			</form:form>
		</div>
	</main>
	<footer class="page-footer">
		<div class="container">
			<c:url value="/displayPosts/${topic}" var="cancelUrl" />
			<a class="btn waves-effect" href="${cancelUrl}">Go Back</a>
		</div>
	</footer>
	<script type="text/javascript" src="/spring/scripts/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="/spring/scripts/materialize.min.js"></script>
</body>
</html>