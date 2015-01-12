<%@ include file="body/include/library.jsp"%>
<script type="text/javascript" src="js/menu.js"></script>
<div class="menu-main-div" align="right">
	<div class="menu-title">
		<bean:message key="menu.title" />
	</div>
	<div class="menu-div-hrefs">
		<div class="menu-div-imgs-for-hrefs div-inline-block">
			<img src="img/tr.png" class="menu-top-img-for-hrefs" width="18px"><img
				src="img/tr.png" width="18px">
		</div>
		<div class="div-inline-block">
			<p>
				<html:link page="/ListNews.do" styleId="menu-list-link">
					<bean:message key="menu.news.list" />
				</html:link>
			</p>
			<p>
				<html:link page="/AddNews.do" styleId="menu-add-link">
					<bean:message key="menu.add.news" />
				</html:link>
			</p>
		</div>
	</div>
	<script>
		changeHrefsColors();			
		</script>
</div>

