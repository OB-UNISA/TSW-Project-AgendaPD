<%@page import="agendapd.model.PazienteBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	PazienteBean pazienteBean = (PazienteBean) request.getSession().getAttribute("pazienteBean");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Notizie">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/dottore.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript" src="JS/notizie.js"></script>
<title>Notizie</title>
<script type="text/javascript">
	$(document).ready(function() {
		loadNews("admin");
<%if (pazienteBean != null && pazienteBean.getDoctorUser() != null) {%>
		loadNews("<%=pazienteBean.getDoctorUser()%>");
<%}%>
	});
</script>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<form id="news" name="formNews" class="visibleBlock">
		<div id="divNews">
			<span id="spanSpace"></span>
		</div>
	</form>
</body>
</html>