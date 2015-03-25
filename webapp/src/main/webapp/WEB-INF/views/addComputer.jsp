<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.persistance.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
.error {
	color: #A94442;
}
 
.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 1px solid #ff0000;
	padding: 8px;
	margin: 16px;
	
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}
</style>

    <jsp:include page="header.jsp"></jsp:include>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add.computer" text="add.computer" /></h1>
                    
                    
                    <!-- <form action="${pageContext.request.contextPath}/addComputer" method="POST" id="addForm"> -->
                    <form:form method="POST" modelAttribute="computerDTO" command="computerDTO" commandName="computerDTO" action="${pageContext.request.contextPath}/addComputer" id="addForm">
					<form:errors path="*" cssClass="alert alert-danger" element="div" />
                    
                        <fieldset>
                            <div class="form-group">
                                <label for="name"><spring:message code="computers.name.th" text="computers.name.th" /></label>
                                
                                <spring:message code="computers.name.th" text="computers.name.th" var="computers_name_th" />
                                
                                <form:input path="name" type="text" cssClass="form-control" placeholder="${computers_name_th}" />
                                <form:errors path="name" cssClass="error" />
                                
                                <!-- <input type="text" class="form-control" id="computerName" name="computerName" placeholder="<spring:message code="computers.name.th" text="computers.name.th" />"> -->
                            </div>
                            <div class="form-group">
                                <label for="dateAdded"><spring:message code="introduced.date.th" text="introduced.date.th" /></label>
                                
                                <spring:message code="introduced.date.placeholder" text="introduced.date.placeholder" arguments="${requestScope.formatString}" var="introduced_date_placeholder" />
                                
                                <form:input path="dateAdded" cssClass="form-control" placeholder="${introduced_date_placeholder}" />
                                <form:errors path="dateAdded" cssClass="error" />
                                
                                <!-- <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<spring:message code="introduced.date.placeholder" text="introduced.date.placeholder" arguments="${requestScope.formatString}" />"> -->
                            </div>
                            <div class="form-group">
                                <label for="dateRemoved"><spring:message code="discontinued.date.th" text="discontinued.date.th" /></label>
                                
                                <spring:message code="discontinued.date.placeholder" text="discontinued.date.placeholder" arguments="${requestScope.formatString}" var="discontinued_date_placeholder" />
                                
                                <form:input path="dateRemoved" cssClass="form-control" placeholder="${discontinued_date_placeholder}" />
                                <form:errors path="dateRemoved" cssClass="error" />
                                
                                <!-- <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder='<spring:message code="discontinued.date.placeholder" text="discontinued.date.placeholder" arguments="${requestScope.formatString}" />'> -->
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company.th" text="company.th" /></label>
                                
                                <%-- <select class="form-control" id="companyId" name="companyId" >
                                	<option value="-1"><spring:message code="please.select.company" text="please.select.company" /></option>
                                
	                                <mylib:listCompanies companyList="${requestScope.companyList}" />
	                                
                                </select> --%>
                                
                                
                                <spring:message code="please.select.company" text="please.select.company" var="please_select_company"/>
                                <form:select path="companyId" class="form-control">
								   <form:option value="-1" label="${please_select_company}"/>
								   <form:options items="${companyList}" />
								</form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="add" text="add" />" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}" class="btn btn-default"><spring:message code="cancel" text="cancel" /></a>
                        </div>
                        
                    </form:form>
                    <!-- </form> -->
                </div>
            </div>
        </div>
    </section>
    
    <style>
    	label.error{
    		font-weight: initial;
    		color: rgb(180, 0, 0);
    	}
    </style>
    
	<script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/jquery.validate.min.js" type="text/javascript"></script>
    <!-- <script src="${pageContext.request.contextPath}/static/js/run.validator.js" type="text/javascript"></script> -->
    
</body>
</html>