<%@ include file="include/library.jsp"%>
<%@ include file="include/messages.jsp"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<title><bean:message key="list.page.title" /></title>
<script type="text/javascript" src="js/list.js"></script>
<table class="list-news-table content-div-border">
	<tr>
		<td class="page-title-space"><span class="page-title-decor"><span
				class="underline"><bean:message key="page.name.begin" /></span>
				&gt;&gt; </span> <bean:message key="list.page.name" /></td>
		<td></td>
	</tr>
	<logic:present name="newsForm">
		<logic:notEmpty name="newsForm" property="newsList">
			<html:form action="/DeleteNews.do"
				onsubmit="return confirmDeleteChecked(this, msg);">
				<logic:iterate id="news" name="newsForm" property="newsList">
					<tr>
						<td class="list-first-tr"><label
							class="list-news-title-decor"><bean:message
									key="news.title" /> <bean:write name="news" property="title" /></label></td>
						<td class="list-second-tr"><label class="underline"><bean:write
									name="news" property="date" formatKey="date.format" /> </label></td>
					</tr>
					<tr>
						<td class="list-first-tr"><label><bean:write
									name="news" property="brief" /></label></td>
						<td class="list-second-tr"></td>
					</tr>
					<tr>
						<td class="list-first-tr table-bottom-space"></td>
						<td class="list-second-tr table-bottom-space">
							<div class="div-inline-block">
								<html:link page="/EditNews.do" paramId="news.id"
									paramName="news" paramProperty="id" styleClass="list-href">
									<bean:message key="list.edit.button" />
								</html:link>
							</div>
							<div class="div-inline-block">
								<html:link page="/ViewNews.do" paramId="news.id"
									paramName="news" paramProperty="id" styleClass="list-href">
									<bean:message key="list.view.button" />
								</html:link>
							</div>
							<div class="div-inline-block">
								<html:multibox property="ids">
									<bean:write name="news" property="id" />
								</html:multibox>
							</div>
						</td>
					</tr>
				</logic:iterate>
				<tr>
					<td class="list-first-tr"></td>
					<td class="list-second-tr table-bottom-space"><html:hidden
							value="delete" property="action" /> <html:submit>
							<bean:message key="list.delete.button" />
						</html:submit></td>
				</tr>
			</html:form>
		</logic:notEmpty>
		<logic:empty name="newsForm" property="newsList">
			<tr>
				<td class="list-first-tr"><bean:message
						key="list.message.list.empty" /></td>
				<td></td>
			</tr>
		</logic:empty>
	</logic:present>
</table>
