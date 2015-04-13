<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<!-- Get params from url, to put them in the language links -->
	
	<c:set var="paramsFromUrl" value="?" scope="request"/>
	<c:set var="i" value="${0*0}" />
	
	<!-- ou pageContext.request.parameterMap -->
	<c:forEach var="par" items="${paramValues}">
		
		<c:if test="${par.key != 'lang'}">
		
			<c:if test="${i >= 1}">
				<c:set var="paramsFromUrl" value="${paramsFromUrl}&" scope="request" />
			</c:if>
			
			<c:set var="paramsFromUrl" value="${paramsFromUrl}${par.key}=${par.value[0]}" scope="request" />
			
			<c:set var="i" value="${i+1}" />
		</c:if>
	</c:forEach>
	
