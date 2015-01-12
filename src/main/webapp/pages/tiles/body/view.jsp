<%@ include file="include/library.jsp"%>
<%@ include file="include/messages.jsp"%>
<title><bean:write name="newsForm" property="news.title" /></title>
<script type="text/javascript" src="js/view.js"></script>
<bean:define id="news" name="newsForm" property="news" />
<table class="view-save-table content-div-border">
	<tr>
		<td colspan="2" class="page-title-space"><span
			class="page-title-decor"><span class="underline"><bean:message
						key="page.name.begin" /></span> &gt;&gt; </span> <bean:message
				key="view.page.name" /></td>
		<td class="view-third-tr"></td>
	</tr>
	<tr>
		<td class="view-first-tr"><bean:message key="news.title" /></td>
		<td class="view-second-tr"><label><bean:write name="news"
					property="title" /></label></td>
		<td class="view-third-tr"></td>
	</tr>
	<tr>
		<td class="view-first-tr"><bean:message key="news.date" /></td>
		<td class="view-second-tr"><label> <bean:write
					name="news" property="date" formatKey="date.format" />
		</label></td>
		<td class="view-third-tr"></td>
	</tr>
	<tr>
		<td class="view-first-tr"><bean:message key="news.brief" /></td>
		<td class="view-second-tr"><label><bean:write name="news"
					property="brief" /></label></td>
		<td class="view-third-tr"></td>
	</tr>
	<tr>
		<td class="view-first-tr  table-bottom-space"><bean:message
				key="news.content" /></td>
		<td class="view-second-tr table-bottom-space"><label><bean:write
					name="news" property="content" /></label></td>
		<td class="view-third-tr table-bottom-space"></td>
	</tr>
	<tr>
		<td class="view-first-tr"></td>
		<td class="view-second-tr table-bottom-space" colspan="2"><bean:define
				id="newsId" name="news" property="id" />
			<div class="view-submit-div" align="right">
				<div class="div-inline-block submit-button-space">
					<html:form action="/EditNews.do">
						<html:hidden value="${newsId}" property="news.id" />
						<html:hidden value="view" property="cancel_forward" />
						<html:submit>
							<bean:message key="view.edit.button" />
						</html:submit>
					</html:form>
				</div>
				<div class="div-inline-block">
					<html:form action="/DeleteNews.do"
						onsubmit="return confirmDelete(msg);">
						<html:hidden value="${newsId}" name="newsForm" property="ids" />
						<html:submit>
							<bean:message key="view.delete.button" />
						</html:submit>
					</html:form>
				</div>
			</div></td>
	</tr>
</table>
