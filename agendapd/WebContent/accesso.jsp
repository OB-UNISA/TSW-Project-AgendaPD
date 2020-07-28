<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<String> errorsFound = (List<String>) request.getAttribute("errorsFound");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Accesso">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/registrazione.css" type="text/css" rel="stylesheet">
<link href="CSS/login.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript" src="JS/accesso.js"></script>
<script type="text/javascript" src="JS/login.js"></script>
<title>Accesso</title>
<script type="text/javascript">
	$(document).ready(
			function() {
				document.getElementById("userPass").childNodes[1].childNodes[1]
						.addEventListener("blur", function() {
							onlySomethingValidator("user", "user", "infoUser",
									/[A-z]|[0-9]|\?|,|\-|\./, true);
						});
			});
<%if (errorsFound != null && errorsFound.size() > 0) {
	if (!errorsFound.contains("user")) {%>
	formValid.user = true;
<%}
	if (!errorsFound.contains("pass")) {%>
	formValid.pass = true;
<%}
}%>
	
</script>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<form action="<%=response.encodeURL("accesso")%>" method="post" name="formReg"
		id="form">
		<h1 id="info">Accedi</h1>
		<hr>
		<%@ include file="login.jsp"%>
		<div id="buttonNext">
			<button id="sendInfo" class="secondButton" type="button" onclick="send()">Invia</button>
			<p id="infoNextButton"></p>
		</div>
	</form>
</body>
</html>