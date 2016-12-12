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
<link type="text/css" rel="stylesheet"
	href="/spring/css/materialize.min.css" media="screen,projection" />

<!--Let browser know website is optimized for mobile-->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${post.name}by ${post.editedBy} - Final Forum</title>
</head>
<body>
	<header>
		<nav class="top-nav">
			<div class="nav-wrapper">
				<c:url value="/" var="homeLink" />
				<h1 class="brand-logo center"><a href="${homeLink }">${post.name}- Final Forum</a></h1>
			</div>
		</nav>
	</header>
	<main>
	<div class="container">
		<div>
			<h2>${post.name}</h2>
			<p>
				${post.message } <br /> <i>Posted by: ${post.editedBy } on
					${post.datePosted }</i>
			</p>
		</div>
		<hr />
		<c:url value="/getReply/" var="JSONUrl" />
		<c:choose>
				<c:when test="${post.postBlocks.size() > 0}">
					<c:forEach var="postBlock" items="${post.postBlocks}">
						<div style="border: 1px solid #aaa">
							<b>Subject:</b> 
							<a href="#" style="background-color: #eee"
								id="a${postBlock.id}"
								onclick="getReply('${JSONUrl}', ${postBlock.id})">${postBlock.header}</a>
							<div id="reply${postBlock.id}"></div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p>There are no replies yet.</p>
				</c:otherwise>
			</c:choose>
		<br />
		<c:if test="${authenticated}">
			<c:url value="/displayPosts/${topic}/post/${post.id}/addPostBlock"
				var="addUrl" />
			<a class="btn waves-effect" href="${addUrl}">Reply</a>
			<br />
			<hr />
			<a class="btn waves-effect" href="${addPostUrl}">Add a new ${topic } Post</a>
		</c:if>
	</div>
	</main>
	<footer class="page-footer">
		<div class="container">
			<c:url value="/displayPosts/${topic}" var="postsUrl" />
			<a class="btn waves-effect" href="${postsUrl}">Back to the ${topic } board</a>
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
	<script type="text/javascript"
		src="/spring/scripts/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="/spring/scripts/materialize.min.js"></script>
	<script type="text/javascript" src="/spring/scripts/script.js"></script>
</body>
</html>