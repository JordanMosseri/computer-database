<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.persistance.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ page import="com.excilys.computerdatabase.service.*"%>
<%@ taglib uri="/WEB-INF/mylib.tld" prefix="mylib" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



	<%-- <mylib:initEditComputer/> --%>

    <jsp:include page="header.jsp"></jsp:include>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${requestScope.intId}
                    </div>
                    <h1><spring:message code="edit.computer" text="edit.computer" /></h1>

                    <form action="${pageContext.request.contextPath}/editComputer" method="POST">
                        <input type="hidden" value="0"/>
                        <fieldset>
                        	
                        	<input type="hidden" name="idHidden" value="${requestScope.intId}">
                            
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computers.name.th" text="computers.name.th" /></label>
                                <%-- ${pageScope['computer'] --%>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="${requestScope.computer.name}" value="${requestScope.computer.name}">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced.date.th" text="introduced.date.th" /></label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="${requestScope.computer.dateAddedString}" value="${requestScope.computer.dateAddedString}">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued.date.th" text="discontinued.date.th" /></label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="${requestScope.computer.dateRemovedString}" value="${requestScope.computer.dateRemovedString}">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company.th" text="company.th" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0">Please select a company</option>
                                    
                                    <mylib:listCompanies companyList="${requestScope.companyList}" idSelected="${requestScope.computer.companyId}"/>
                                   
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="edit" text="edit" />" class="btn btn-primary">
                            or
                            <a href="dashboard.jsp" class="btn btn-default"><spring:message code="cancel" text="cancel" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>