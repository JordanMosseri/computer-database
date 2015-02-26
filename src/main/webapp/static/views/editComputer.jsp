<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.dao.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ page import="com.excilys.computerdatabase.main.*"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>

<%! int intId; %>
<%! Computer computer; %>
<%
    if (request.getParameter("id") == null) {
    	intId=-1;
    } else {
    	String stringId = request.getParameter("id");
    	intId = Service.stringToInt(stringId);
    }
%>
<% 
computer = ComputerDAO.getInstance().getComputer(intId); 
%>

    <jsp:include page="header.html"></jsp:include>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <%= intId %>
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="/computerdatabase_test_maven/editComputer" method="POST">
                        <input type="hidden" value="0"/>
                        <fieldset>
                        	
                        	<input type="hidden" name="idHidden" value="<%= intId %>">
                            
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" placeholder="<%= computer.name %>">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" placeholder="<%= computer.getDateAddedString() %>">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" placeholder="<%= computer.getDateRemovedString() %>">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" >
                                    <option value="0">Please select a company</option>
                                   <%
		                                List<Company> liste = CompanyDAO.getInstance().getListCompanies();
                                		for(Company c : liste){
                                			out.print("<option value=\"" + c.id + "\"");
                                			if(computer.company.id == c.id){
                                				out.print(" selected=\"selected\"");
                                			}
                                			out.print(">" + c.name + "</option>");
                                		}
                                %>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard.html" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>