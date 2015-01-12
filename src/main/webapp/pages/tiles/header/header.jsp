<%@ include file="../body/include/library.jsp"%>
<div class="header-main-div">
	<h1 class="header-h1">
		<bean:message key="header.page.name" />
	</h1>
	<div align="left" class="header-locale-href">
		<html:link page="/Locale.do?lang=en">
			<bean:message key="lang.en" />
		</html:link>
		<html:link page="/Locale.do?lang=ru">
			<bean:message key="lang.ru" />
		</html:link>
	</div>
</div>

