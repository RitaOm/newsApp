<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<tiles:useAttribute id="relativeCssUrl" name="css" />
<c:url var="absoluteCssUrl" value="${relativeCssUrl}" />
<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="${absoluteCssUrl}" />
</head>
<body>
	<div id="left"></div>
	<div id="right"></div>
	<div id="top"></div>
	<div id="bottom"></div>
	<div class="layout-main-div">
		<tiles:insert attribute="header" ignore="true" />
		<tiles:insert attribute="body" />
	</div>
	<tiles:insert attribute="footer" />
</body>
</html>