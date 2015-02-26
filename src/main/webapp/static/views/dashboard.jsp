<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.dao.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ page import="com.excilys.computerdatabase.main.*"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<%-- $request.getContextPath() --%>
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>


<%! int intOffset; %>
<%! Paging<Computer> paging; %>
<%
    if (request.getParameter("offset") == null) {
    	intOffset=0;
    } else {
    	String stringOffset = request.getParameter("offset");
    	intOffset = Service.stringToInt(stringOffset);
    }
%>
<% 
paging = ComputerDAO.getInstance().getAll(intOffset, 15); 
%>

    <jsp:include page="header.html"></jsp:include>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <%= paging.getLimit() %> computers found 
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
                    <a class="btn btn-success" id="addComputer" href="addComputer.jsp">Add Computer</a> 
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
                	<%
                	
               		for(Computer c : paging.actualList){
                		%>
	                    <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="0">
	                        </td>
	                        <td>
	                            <a href="editComputer.jsp?id=<%= c.id %>" onclick=""><%= c.name %></a>
	                        </td>
	                        <td><%= c.getDateAddedString() %></td>
	                        <td><%= c.getDateRemovedString() %></td>
	                        <td><%= c.company.name %></td>
	
	                    </tr>
                    	<%
                    }
                    %>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
	            <% if(paging.indexPage>0) { %>
	                <li>
	                    <a href="?offset=<%= paging.offset-paging.getLimit() %>" aria-label="Previous">
	                      <span aria-hidden="true">&laquo;</span>
	                  </a>
	              </li>
	              <% } %>
	              <%
	              int numberOfPages = paging.totalSize/paging.getLimit();
	              	for(int i=paging.indexPage-2; i<paging.indexPage+2+1; i++){
	              		if(i>=0 && i<numberOfPages) {
	              		%>
	              			<li><a href="#"><%= i %></a></li>
	              		<%
	              		}
	              	}
	              %>
	              <%-- <li><a href="#"><%= paging.indexPage %></a></li> --%>
	              
	              <% if(paging.indexPage<numberOfPages-1) { %>
	              <li>
	                <a href="?offset=<%= paging.offset+paging.getLimit() %>" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
	            <% } %>
	        </ul>
	
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <button type="button" class="btn btn-default">10</button>
	            <button type="button" class="btn btn-default">50</button>
	            <button type="button" class="btn btn-default">100</button>
	        </div>
		</div>
    </footer>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/dashboard.js"></script>

</body>
</html>