<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.dao.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ page import="com.excilys.computerdatabase.main.*"%>
<%@ taglib uri="/WEB-INF/mylib.tld" prefix="mylib" %>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database - Edit Computer</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	<%-- <mylib:initEditComputer/> --%>

    <jsp:include page="header.jsp"></jsp:include>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${requestScope.intId}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="${pageContext.request.contextPath}/editComputer" method="POST">
                        <input type="hidden" value="0"/>
                        <fieldset>
                        	
                        	<input type="hidden" name="idHidden" value="${requestScope.intId}">
                            
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <%-- ${pageScope['computer'] --%>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="${requestScope.computer.name}" value="${requestScope.computer.name}">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="${requestScope.computer.dateAddedString}" value="${requestScope.computer.dateAddedString}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="${requestScope.computer.dateRemovedString}" value="${requestScope.computer.dateRemovedString}">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">Please select a company</option>
                                    
                                    <mylib:listCompanies idSelected="${requestScope.computer.company.id}"/>
                                   
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard.jsp" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>