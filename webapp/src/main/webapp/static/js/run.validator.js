/**
 * 
 */

/*
 * var strings = new Array();
  strings['messages.view'] = "<spring:message code='messages.view' javaScriptEscape='true' />";
  strings['messages.edit'] = "<spring:message code='messages.edit' javaScriptEscape='true' />";
 */

jQuery(document).ready(function() {
	jQuery.validator.addMethod("regex", 
			function(value, element, regexp) {
				if (regexp.constructor != RegExp)
					regexp = new RegExp(regexp);
				else if (regexp.global)
					regexp.lastIndex = 0;
				return this.optional(element) || regexp.test(value);
			}, "Incorrect date, out of bounds.");

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
//});

//$(document).ready(function() {
	//$("#addForm").validate();
});