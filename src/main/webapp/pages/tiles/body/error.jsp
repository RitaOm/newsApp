<%@ include file="include/library.jsp"%>
<title><bean:message key="error.page.title" /></title>
<div class="error-body-div">
	<h2>
		<bean:message key="error.page.name" />
	</h2>
	<p>
		<html:link page="/ListNews.do">
			<bean:message key="error.href.news.page" />
		</html:link>
	</p>
</div>
