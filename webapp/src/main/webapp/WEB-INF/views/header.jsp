<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>

<html>
<head>
	
	<title><spring:message code="page.title" text="page.title" /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	
	
	<!-- CSS references -->
	
	<%-- $request.getContextPath() --%>
	<c:set var="chemin" value="${pageContext.request.contextPath}/static/css/"></c:set>
	<c:set var="chemin" value="static/css/"></c:set>
	
	<!-- Bootstrap -->
	<link href="${chemin}bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="${chemin}font-awesome.css" rel="stylesheet" media="screen">
	<link href="${chemin}main.css" rel="stylesheet" media="screen">

</head>
<body>
	
	<mylib:defineParamsForLanguageLinks />
	
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
		
			<!-- Logout part -->
			
			<p style="float:right;color: #CCC;">
				<c:if test="${pageContext.request.userPrincipal.name != null}">
				   Welcome : ${pageContext.request.userPrincipal.name} | 
				</c:if>
				<a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
			</p>
			
			<!-- Title link -->
		
			<a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">
				<i class="fa fa-book fa-lg"></i>&nbsp;
				<spring:message code="application.name" text="application.name" />
			</a>
			
		</div>
	</header>
	
	<!-- Language links -->
	
	<div class="container">
		<div style="float:right;">
			<a href="${paramsFromUrl}&lang=fr"><img style="" alt="" src="${chemin}/icons/France.png"></a>
			<a href="${paramsFromUrl}&lang=en"><img style="" alt="" src="${chemin}/icons/United-kingdom.png"></a>
			<a href="${paramsFromUrl}&lang=es"><img style="" alt="" src="${chemin}/icons/Spain.png"></a>
		</div>
	</div>
		
