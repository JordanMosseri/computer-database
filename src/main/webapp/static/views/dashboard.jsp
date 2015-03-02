<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.dao.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ page import="com.excilys.computerdatabase.main.*"%>
<%@ taglib uri="/WEB-INF/mylib.tld" prefix="mylib" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database - Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<%-- $request.getContextPath() --%>
<link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>



	<%-- <mylib:initDashboard /> --%>

    <jsp:include page="header.jsp"></jsp:include>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
            	<%-- ${pageScope["paginationObject"].pageSize/actualList} --%>
                ${pageScope.request["paginationObject"]} computers found 
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="static/views/addComputer.jsp">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
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
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	
                	<!-- c is-a Computer -->
                	<c:forEach var="c" items="${pageScope.request['paginationObject'].actualList}">
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="static/views/editComputer.jsp?id=${c.id}" onclick="">${c.name}</a>
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
            	
            	
            	<mylib:pagination pagingObject="${pageScope.request['paginationObject']}" />
            	
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