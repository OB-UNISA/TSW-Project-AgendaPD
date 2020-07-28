<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="cssFile.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="ajax.js"></script>
<script type="text/javascript" src="jquery-3.5.1.js"></script>
<title>B</title>
</head>
<body>
	<h2>
		SessionID load page: <span><%=request.getSession().getId()%></span>
	</h2>
	<h2>
		SessionID ajax: <span onclick="getID()">click</span>
	</h2>
	<h3>
		<a href="a.jsp" target="_blank">GO TO A</a>
	</h3>
	<h4>
		<a href="index.html">index</a>
	</h4>
</body>
</html>