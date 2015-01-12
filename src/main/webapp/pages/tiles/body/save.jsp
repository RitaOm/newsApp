<%@ include file="include/library.jsp"%>
<%@ include file="include/messages.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<title><bean:message key="save.page.title" /></title>
<script type="text/javascript" src="js/save.js"></script>
<div class="content-div-border save-main-div">
	<div class="save-title-space">
		<span class="page-title-decor"><span class="underline"><bean:message
					key="page.name.begin" /></span> &gt;&gt; </span>
		<bean:message key="edit.page.name" />
	</div>
	<bean:define id="news" name="newsForm" property="news" />
	<c:choose>
		<c:when
			test="${'ru' eq sessionScope['org.apache.struts.action.LOCALE']}">
			<c:set var="dateFormat" value="dd.MM.yyyy"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="dateFormat" value="MM/dd/yyyy"></c:set>
		</c:otherwise>
	</c:choose>
	<fmt:formatDate value="${news.date}" var="newsDate" type="date"
		pattern="${dateFormat}" />
	<div class="save-html-errors">
		<html:errors />
	</div>
	<html:form action="SaveNews.do">
		<html:hidden value="${news.id}" property="news.id" />
		<table class="view-save-table">
			<tr>
				<td class="save-first-tr"><bean:message key="news.title" /></td>
				<td class="save-second-tr"><html:text property="title"
						styleId="title" styleClass="save-title-input-text" /> <span
					class="error" id="title_err"></span></td>
				<td class="save-third-tr"></td>
			</tr>
			<tr>
				<td class="save-first-tr"><bean:message key="news.date" /></td>
				<td class="save-second-tr"><html:text property="date"
						styleId="date" value="${newsDate}"
						styleClass="save-date-input-text" /><span class="error"
					id="date_err"></span></td>
				<td class="save-third-tr"></td>
			</tr>
			<tr>
				<td class="save-first-tr"><bean:message key="news.brief" /></td>
				<td class="save-second-tr"><html:textarea property="brief"
						styleId="brief" styleClass="save-textbox" rows="6" /><span
					class="error" id="brief_err"></span></td>
				<td class="save-third-tr"></td>
			</tr>
			<tr>
				<td class="save-first-tr table-bottom-space"><bean:message
						key="news.content" /></td>
				<td class="save-second-tr table-bottom-space"><html:textarea
						property="content" styleClass="save-textbox" rows="11"
						styleId="content" /><span class="error" id="content_err"></span></td>
				<td class="save-third-tr table-bottom-space"></td>
			</tr>
			<tr>
				<td class="table-bottom-space" colspan="3" align="center"><div
						class="div-inline-block submit-button-space">
						<html:submit onclick="return formValidate(msg);">
							<bean:message key="save.save.button" />
						</html:submit>

					</div>
					<div class="div-inline-block">
						<c:choose>
							<c:when
								test="${'view' ne sessionScope['cancel_forward'] or news.id eq 0}">
								<html:submit onclick="submitTheForm('ListNews.do')">
									<bean:message key="save.cancel.button" />
								</html:submit>
							</c:when>
							<c:otherwise>
								<html:submit onclick="submitTheForm('ViewNews.do')">
									<bean:message key="save.cancel.button" />
								</html:submit>
							</c:otherwise>
						</c:choose>
					</div></td>
			</tr>
		</table>
	</html:form>
</div>
