<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	UtenteBean utenteBean = (UtenteBean) request.getSession().getAttribute("utenteBean");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Dottore">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/dottore.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript" src="JS/dottore.js"></script>
<title>Dottore</title>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<br>
	<div id="sideBar">
		<h1 class="selected" onclick="sideBar('Profilo', 1)">
			&#128100;<span>Profilo</span>
		</h1>
		<hr>
		<h1 onclick="sideBar('Pazienti', 11)">
			&#127973;<span>Pazienti</span>
		</h1>
		<hr>
		<h1 onclick="sideBar('Dottore', 9)">
			&#128137;<span>Dottore</span>
		</h1>
		<hr>
		<h1 onclick="sideBar('IoT', 3)">
			&#128241;<span>IoT</span>
		</h1>
		<hr>
		<h1 onclick="sideBar('Communicazioni', 5)">
			&#128240;<span>Communicazioni</span>
		</h1>
		<hr>
		<h1 onclick="sideBar('Impostazioni', 7)">
			&#128295;<span>Impostazioni</span>
		</h1>
	</div>
	<div id="viewArea">
		<form id="personalInfo" name="formInfoP">
			<label>Nome Utente&nbsp;<input type="text" name="user"
				value="<%=utenteBean.getUser()%>" disabled="disabled"></label><br> <br>
			<label>Password&nbsp;<input type="password" name="pass"
				value="<%=utenteBean.getPass()%>"></label><br> <br> <label>Email&nbsp;<input
				type="email" name="email" value="<%=utenteBean.getEmail()%>"></label> <br>
			<br> <label>Nome&nbsp;<input type="text" name="firstName"
				value="<%=utenteBean.getFirstName()%>"></label><br> <br> <label>Cognome&nbsp;<input
				type="text" name="lastName" value="<%=utenteBean.getLastName()%>"></label><br>
			<br> <label>Codice fiscale&nbsp;<input type="text"
				name="fiscalCode" value="<%=utenteBean.getFiscalCode()%>">
			</label><br> <br> <label>Data di nascita&nbsp;<input type="date"
				name="birthday" value="<%=utenteBean.getBirthday()%>"></label><br> <br>
			<label>Via&nbsp;<input type="text" name="addressName"
				value="<%=utenteBean.getAddressName()%>"></label><br> <br> <label>Città&nbsp;<input
				type="text" name="addressCity" value="<%=utenteBean.getAddressCity()%>"></label><br>
			<br> <label>CAP&nbsp;<input type="text" name="addressCAP"
				value="<%=utenteBean.getAddressCAP()%>"></label>
			<button type="button" class="secondButton" id="buttonPersonalInfo"
				onclick="updatePersonalInfo()">Salva</button>
		</form>
		<form id="iot" name="formIot" class="visibleNone">
			<h1>Disponibile prossimamente...</h1>
		</form>
		<form id="news" name="formNews" class="visibleNone">
			<h1>Disponibile prossimamente...</h1>
		</form>
		<form id="settings" name="formSettings" class="visibleNone">
			<h1>Disponibile prossimamente...</h1>
		</form>
		<form id="secretary" name="formSecretary" class="visibleNone">
			<h1>Disponibile prossimamente...</h1>
		</form>
		<form id="patients" name="formPatients" class="visibleNone">
			<h1>Disponibile prossimamente...</h1>
		</form>
	</div>
</body>
</html>