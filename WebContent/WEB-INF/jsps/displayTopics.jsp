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
<title>Final Forum Topics</title>
</head>
<body>
	<header>
		<nav class="top-nav">
			<div class="nav-wrapper">
				<c:url value="/" var="homeLink" />
				<h1 class="brand-logo center"><a href="${homeLink }">Topics - Final Forum</a></h1>
			</div>
		</nav>
	</header>
	<main>
		<div class="container">
			<p><b>Available Topics</b></p>
			<ul>
				<c:forEach var="topic" items="${topicList}">
					<li>
					 	${topic.tag}
					 	<c:url value="/displayPosts/${topic.tag}" var="postsUrl" />
					 	<a class="btn waves-effect" href="${postsUrl }">View Posts</a>
					</li>
					<br />	
				</c:forEach>
			</ul>
			<c:if test="${authenticated}">
				<c:url value="/addTopic" var="addUrl" />
				<a class="btn waves-effect" href="${addUrl}">Add a Topic</a>
			</c:if>
		</div>
	</main>
	<footer class="page-footer">
		<div class="container">
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
		</div>
	</footer>
	<script type="text/javascript" src="/spring/scripts/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="/spring/scripts/materialize.min.js"></script>
</body>
</html>