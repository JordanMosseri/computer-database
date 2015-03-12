<%@ page import="java.util.*"%>
<%@ page import="com.excilys.computerdatabase.persistance.*"%>
<%@ page import="com.excilys.computerdatabase.modele.*"%>
<%@ page import="com.excilys.computerdatabase.util.*"%>
<%@ taglib uri="/WEB-INF/mylib.tld" prefix="mylib" %>




    <jsp:include page="header.jsp"></jsp:include>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="${pageContext.request.contextPath}/addComputer" method="POST" id="addForm">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date (formatted <% out.print(Constantes.FORMAT_DATE); %>)">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date (formatted <% out.print(Constantes.FORMAT_DATE); %>)">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<option value="0">Please select a company</option>
                                
	                                <mylib:listCompanies />
                                    
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard.jsp" class="btn btn-default">Cancel</a>
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