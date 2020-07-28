<%@page import="java.sql.Timestamp, java.time.LocalDateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ServletContext context = request.getServletContext();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Admin">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/dottore.css" type="text/css" rel="stylesheet">
<link href="CSS/admin.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/admin.js"></script>
<script type="text/javascript" src="JS/dottore.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		ping();
		setInterval(ping, 5000);
	});
</script>
<title>Admin</title>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<br>
	<div id="sideBar">
		<h1 class="selected" onclick="sideBar('Statistiche', 1)">
			&#128202;<span>Statistiche</span>
		</h1>
		<hr>
		<h1 onclick="newsOnClickAdmin()">
			&#128240;<span>Communicazioni</span>
		</h1>
	</div>
	<div id="viewArea">
		<form id="stats" name="formStats">
			<label>Orario server&nbsp;<input type="text" disabled="disabled"
				name="currentTimeServer" class="width"
				value="<%=Timestamp.valueOf(LocalDateTime.now())%>"></label> <br> <br>
			<label>Data accensione server&nbsp;<input type="text"
				disabled="disabled" name="dateServerStarted" class="width"
				value="<%=((Timestamp) context.getAttribute("dataStartingServer")).toString()%>">
			</label> <br> <br> <label>Numero richieste&nbsp;<input type="text"
				disabled="disabled" name="numberRequests"
				value="<%=(Long) context.getAttribute("numberRequests")%>"></label><br>
			<br> <label>Ping&nbsp;<input type="text" disabled="disabled"
				name="ping" value="ND"></label>
		</form>
		<form id="news" name="formNews" class="visibleNone">
			<label>&nbsp;<textarea name="textNews"></textarea>
				<button type="button" class="secondButton" id="buttonNews"
					onclick="addNewsAdmin()">Salva</button></label>
			<div id="divNews">
				<span id="spanSpace"></span>
			</div>
		</form>
	</div>
</body>
</html>