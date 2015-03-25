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
	
	<!-- Get params from url, to put them in the link -->
	
	<c:set var="paramsFromUrl" value="?" />
	<c:set var="i" value="${0*0}" />
	
	<!-- ou pageContext.request.parameterMap -->
	<c:forEach var="par" items="${paramValues}">
		
		<c:if test="${par.key != 'lang'}">
		
			<c:if test="${i >= 1}">
				<c:set var="paramsFromUrl" value="${paramsFromUrl}&" />
			</c:if>
			
			<c:set var="paramsFromUrl" value="${paramsFromUrl}${par.key}=${par.value[0]}" />
			
			<c:set var="i" value="${i+1}" />
		</c:if>
	</c:forEach>
	
			
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			
			<a class="navbar-brand" href="${pageContext.request.contextPath}">
				<i class="fa fa-book fa-lg"></i>&nbsp;
				<spring:message code="application.name" text="application.name" />
			</a>
		</div>
	</header>
	
	<div class="container">
		<div style="float:right;">
			<a href="${paramsFromUrl}&lang=fr"><img style="" alt="" src="${chemin}/icons/France.png"></a>
			<a href="${paramsFromUrl}&lang=en"><img style="" alt="" src="${chemin}/icons/United-kingdom.png"></a>
			<a href="${paramsFromUrl}&lang=es"><img style="" alt="" src="${chemin}/icons/Spain.png"></a>
		</div>
	</div>
		
	<style>
		.navbar-inverse {
			background: #383838;//#888
		}
		.navbar-inverse .navbar-brand, .fa-book{
			color: #ccc;//#F2F2F2 
		}
		.navbar-brand{
			font-variant: small-caps;
		}
	</style>
