<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.persistance.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ page import="com.excilys.computerdatabase.service.*"%>
<%@ taglib uri="/WEB-INF/mylib.tld" prefix="mylib" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>




	<%-- <mylib:initDashboard /> --%>

    <jsp:include page="header.jsp"></jsp:include>
    
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
                    <a class="btn btn-success" id="addComputer" href="static/views/addComputer.jsp"><spring:message code="add.computer" text="add.computer" /></a> 
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
	                            <a href="${pageContext.request.contextPath}/EditComputer?id=${c.id}" onclick="">${c.name}</a>
	                        </td>
	                        <td>${c.dateAddedString}</td>
	                        <td>${c.dateRemovedString}</td>
	                        <td>${c.company.name}</td>
	
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
            	
            		<%-- indexPage="<%= paging.indexPage %>" 
            		offset="<%= paging.offset %>" 
            		limit="<%= paging.getLimit() %>" 
            		totalSize="<%= paging.totalSize %>" --%> 
            		
            	
	            
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
<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/dashboard.js"></script>

</body>
</html>