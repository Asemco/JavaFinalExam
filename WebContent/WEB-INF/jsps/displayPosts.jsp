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
<title>${topic} Board - Final Forum</title>
</head>
<body>
	<header>
		<nav class="top-nav">
			<div class="nav-wrapper">
				<c:url value="/" var="homeLink" />
				<h1 class="brand-logo center"><a href="${homeLink }">${topic} Board - Final Forum</a></h1>
			</div>
		</nav>
	</header>
	<main>
		<div class="container">
		<p><b>Available Posts</b></p>
		<c:if test="${authenticated}">
				<c:url value="/displayPosts/${topic}/addPost" var="addUrl" />
				<a class="btn waves-effect" href="${addUrl}">Add a new Post</a>
			</c:if>
			<hr/>
			<c:choose>
				<c:when test="${postList.size() > 0}">
					<c:forEach var="post" items="${postList}">
						<p>
							Subject: ${post.name} <br />
							Last Reply: ${post.dateEdited } <br />
							Replies: ${post.postBlocks.size()} <br />
						</p>
						<c:url value="/displayPosts/${topic}/post/${post.id}" var="viewUrl" />
						<a class="btn waves-effect" href="${viewUrl}">View Post</a>
						<br />
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p>There are no posts yet.</p>
				</c:otherwise>
			</c:choose>
			<br />
			<hr/>
			<c:if test="${authenticated}">
				<c:url value="/displayPosts/${topic}/addPost" var="addUrl" />
				<a class="btn waves-effect" href="${addUrl}">Add a new Post</a>
			</c:if>
			<br />
		</div>
	</main>
	<footer class="page-footer">
		<div class="container">
			<c:url value="/displayTopics" var="topicsUrl" />
			<a class="btn waves-effect" href="${topicsUrl}">View Topics</a>
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