
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="companyList" required="true" type="java.util.List" %>
<%@ attribute name="idSelected" required="false" type="java.lang.Integer" %>

<c:forEach items="${companyList}" var="c">
	<c:choose>
		<%-- ${pageScope["computer"].company.id} --%>
		<c:when test="${idSelected == c.id}">
			<option value="${c.id}" selected="selected">${c.name}</option>
		</c:when>
		<c:otherwise>
			<option value="${c.id}">${c.name}</option>
		</c:otherwise>
	</c:choose>
	
</c:forEach>