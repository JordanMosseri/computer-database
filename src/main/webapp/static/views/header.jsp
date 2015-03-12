<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
<title><spring:message code="page.title" text="page.title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">


<%-- $request.getContextPath() --%>

<c:set var="chemin" value="${pageContext.request.contextPath}/static/css/"></c:set>
<c:set var="chemin" value="static/css/"></c:set>

<!-- Bootstrap -->
<link href="${chemin}bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${chemin}font-awesome.css" rel="stylesheet" media="screen">
<link href="${chemin}main.css" rel="stylesheet" media="screen">
</head>
<body>
	
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/Dashboard">
				<spring:message code="application.name" text="application.name" />
			</a>
		</div>
	</header>
