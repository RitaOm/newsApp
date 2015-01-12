<%@ page contentType="text/javascript" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<script type="text/javascript">
	var msg = {
		emptyField : "<bean:message key='form.error.field' />",
		tooLong : "<bean:message key='form.error.long' />",
		wrongDate : "<bean:message key='form.error.wrong.date' />",
		noDate : "<bean:message key='form.error.no.date' />",
		deteteOne : "<bean:message key='confirm.delete.one' />",
		deteteAll : "<bean:message key='confirm.delete.all' />",
		deleteInfo : "<bean:message key='info.delete.all' />",
		lang : "${sessionScope['org.apache.struts.action.LOCALE']}"			
	};		
</script>