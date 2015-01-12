<%@ include file="tiles/body/include/library.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><bean:message key="error.page.title" /></title>
</head>
<body>
	<div class="error404-body-div" align="center">
		<h2 class="error404-h2">
			<bean:message key="error.page.name" />
		</h2>
		<img  src="img/404.jpg">
		<p>
			<html:link page="/ListNews.do">
				<bean:message key="error.href.news.page" />
			</html:link>
		</p>
	</div>
</body>
</html>