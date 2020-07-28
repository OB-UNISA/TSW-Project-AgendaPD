<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int httpCode = response.getStatus();
if (httpCode == 200) {
	String error = request.getParameter("error");
	if (error != null) {
		try {
	httpCode = Integer.parseInt(request.getParameter("error"));
		} catch (NumberFormatException e) {

		}
		response.setStatus(httpCode);
	}
}
String message = "Si è verificato un errore, riprova più tardi";
switch (httpCode) {
case 401:
	message = "Accedere prima";
	break;
case 403:
	message = "Non è possibile accedere alla risorsa";
	break;
case 404:
	message = "La risorsa richiesta non esiste";
	break;
case 409:
	message = "La risorsa richiesta è già presente";
	break;
case 422:
	message = "I dati che ci hai mandato sono incorretti o incompleti";
	break;
case 500:
	message = "Si è verificato un errore da parte nostra, riprova più tardi";
	break;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Errore">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/errore.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<title>Errore</title>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<br>
	<header>ERRORE!</header>
	<br>
	<article><%=message%></article>
</body>
</html>