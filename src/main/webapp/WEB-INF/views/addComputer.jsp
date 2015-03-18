<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.persistance.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ taglib uri="/WEB-INF/mylib.tld" prefix="mylib" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>



    <jsp:include page="header.jsp"></jsp:include>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="add.computer" text="add.computer" /></h1>
                    <form action="${pageContext.request.contextPath}/addComputer" method="POST" id="addForm">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="computers.name.th" text="computers.name.th" /></label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="<spring:message code="computers.name.th" text="computers.name.th" />">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced.date.th" text="introduced.date.th" /></label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<spring:message code="introduced.date.placeholder" text="introduced.date.placeholder" arguments="${requestScope.formatString}" />">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued.date.th" text="discontinued.date.th" /></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder='<spring:message code="discontinued.date.placeholder" text="discontinued.date.placeholder" arguments="${requestScope.formatString}" />'>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company.th" text="company.th" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<option value="0"><spring:message code="please.select.company" text="please.select.company" /></option>
                                
	                                <mylib:listCompanies companyList="${requestScope.companyList}" />
                                    
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="add" text="add" />" class="btn btn-primary">
                            or
                            <a href="dashboard.jsp" class="btn btn-default"><spring:message code="cancel" text="cancel" /></a>
                        </div>
                    </form>
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
    <script type="text/javascript">
    
	
		jQuery.validator.addMethod("regex", 
			function(value, element, regexp) {
				if (regexp.constructor != RegExp)
					regexp = new RegExp(regexp);
				else if (regexp.global)
					regexp.lastIndex = 0;
				return this.optional(element) || regexp.test(value);
			}, "Incorrect date, out of bounds.");
		
		jQuery(document).ready(function() {
			jQuery("#addForm").validate({
				rules : {
					"computerName" : {
						"required" : true,
						"maxlength" : 250
					},
					"introduced" : {
						//"regex" : /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/
						"regex" : /^(19|20)[0-9]{2}-[0-9]{2}-[0-9]{2}$/
					},
					"discontinued" : {
						"regex" : /^(19|20)[0-9]{2}-[0-9]{2}-[0-9]{2}$/
					}
				}
			});
		});
		
		$(document).ready(function() {
			$("#addForm").validate();
		});
		
	</script>
    
</body>
</html>