<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page session="true"%>



	<%-- <mylib:initDashboard /> --%>

    <jsp:include page="header.jsp" />
    
    <%-- Current Locale : ${pageContext.response.locale}<br/> --%>
	
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
            	<%-- ${pageScope["paginationObject"].pageSize/actualList} --%>
                ${requestScope.paginationObject.pageSize} <spring:message code="computers.found" text="computers.found" /> 
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="?offset=${requestScope.offset}&pageSize=${requestScope.pageSize}" method="GET" class="form-inline">
						
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="search.name" text="search.name" />" />
                        <input type="submit" id="searchsubmit" value="<spring:message code="filter.name" text="filter.name" />"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
	                <!-- static/views/addComputer.jsp -->
                    <a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/add"><spring:message code="add.computer" text="add.computer" /></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="edit" text="edit" /></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${pageContext.request.contextPath}/deleteComputer" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <spring:message code="computers.name.th" text="computers.name.th" />
                        </th>
                        <th>
                            <spring:message code="introduced.date.th" text="introduced.date.th" />
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <spring:message code="discontinued.date.th" text="discontinued.date.th" />
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <spring:message code="company.th" text="company.th" />
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	
                	<!-- c is-a Computer -->
                	<c:forEach var="c" items="${requestScope.paginationObject.actualList}">
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${c.id}">
	                        </td>
	                        <td>
	                            <a href="${pageContext.request.contextPath}/editComputer?id=${c.id}" onclick="">${c.name}</a>
	                        </td>
	                        <td>${c.dateAdded}</td>
	                        <td>${c.dateRemoved}</td>
	                        <td>${c.companyName}</td>
	
	                    </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
            	
            	<%-- ${pageScope.request['paginationObject']} --%>
            	
            	<mylib:pagination pagingObject="${requestScope.paginationObject}" />
	        </ul>
	
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <!-- <button type="button" class="btn btn-default">10</button>
	            <button type="button" class="btn btn-default">50</button>
	            <button type="button" class="btn btn-default">100</button> -->
	            <a href="?pageSize=10" type="button" class="btn btn-default">10</a>
	            <a href="?pageSize=50" type="button" class="btn btn-default">50</a>
	            <a href="?pageSize=100" type="button" class="btn btn-default">100</a>
	        </div>
		</div>
		
    </footer>
    
    <jsp:include page="footer.jsp"></jsp:include>
    
	<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>

</body>
</html>