
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="pagingObject" required="true" type="com.excilys.computerdatabase.modele.Paging" %>
  

	<!-- Left arrow -->
	
	<c:if test="${pagingObject.indexPage > 0}">
		<li>
			<a href="?offset=
			${pagingObject.offset-pagingObject.getLimit()}
			&pageSize=
			${request.pageSize}
			" aria-label="Previous">
				<span aria-hidden="true">&laquo;</span>
			</a>
		</li>
	</c:if>

	
	<!-- Numbers -->
	
	<c:set var="boldStyle" value="font-weight:bold;" />
	
	
	<c:set var="numberOfPages" value="1" />
	<c:if test="${pagingObject.getLimit() != 0}">
		<c:set var="numberOfPages" value="${pagingObject.getTotalSize()/pagingObject.getLimit()}" />
	</c:if>
	
	<c:forEach var="i" begin="${pagingObject.indexPage}" end="${pagingObject.indexPage+4+1}">
		<c:if test="${i>=2 && i<numberOfPages+2}">
			<li>
				<c:choose>
					<c:when test="${i-2 == pagingObject.getIndexPage()}">
						<a style="${boldStyle}" href="?offset=${(i-2)*pagingObject.limit}&pageSize=${request.pageSize}">${i-2}</a>
					</c:when>
					<c:otherwise>
						<a href="?offset=${(i-2)*pagingObject.limit}&pageSize=${request.pageSize}">${i-2}</a>
					</c:otherwise>
				</c:choose>
			</li>
		 </c:if>
	</c:forEach>
	
	<%-- <li><a href="#"><%= paging.getIndexPage() %></a></li> --%>
	
	<!-- Right arrow -->
	
	<c:if test="${pagingObject.getIndexPage() < numberOfPages-1}">
		<li>
			<a href="?offset=${pagingObject.offset+pagingObject.getLimit()}&pageSize=${request.pageSize}" aria-label="Next">
				<span aria-hidden="true">&raquo;</span>
			</a>
		</li>
	</c:if>
	
