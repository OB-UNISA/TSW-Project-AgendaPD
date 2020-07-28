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
<meta name="description" content="Paziente">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/dottore.css" type="text/css" rel="stylesheet">
<link href="CSS/paziente.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript" src="JS/paziente.js"></script>
<script type="text/javascript" src="JS/dottore.js"></script>
<title>Paziente</title>
</head>
<body>
	<%@ include file="nav.jsp"%>
	<br>
	<div id="sideBar">
		<h1 class="selected" onclick="sideBar('Profilo', 1)">
			&#128100;<span>Profilo</span>
		</h1>
		<hr>
		<h1 onclick="doctorOnClick()">
			&#127973;<span>Dottore</span>
		</h1>
		<hr>
		<h1 onclick="recordsOnClick()">
			&#128194;<span>Cartelle</span>
		</h1>
		<hr>
		<h1 onclick="prescriptionsOnClick()">
			&#128221;<span>Ricette</span>
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
		<form id="patients" name="formPatients" class="visibleNone">
			<div id="divPatients"></div>
		</form>
		<form id="formFile" name="formFile" method="post"
			action="<%=response.encodeURL("filemanager")%>" target="_blank">
			<input type="hidden" name="mode"> <input type="hidden" name="obj">
			<input type="hidden" name="docUser"> <input type="hidden"
				name="patUser"> <input type="hidden" name="date"> <input
				type="hidden" name="typology" placeholder="Tipologia"> <input
				type="hidden" name="place" placeholder="Luogo"> <input type="hidden"
				name="result" placeholder="Esito"> <input type="hidden" name="file"
				accept="application/pdf">
			<button type="button" id="button" class="secondButton visibleNone"
				onclick="sendRecord()">Invia</button>
		</form>
		<form id="settings" name="formDoctor" class="visibleNone">
			<label>Nome Utente&nbsp;<input type="text" name="doctorUser"></label><br>
			<br> <label>Codice dottore&nbsp;<input type="text"
				name="doctorCode"></label><br> <br> <label>Accesso
				cartelle&nbsp;<input type="checkbox" name="accessRecords">
			</label> <br> <br> <label>Prenotazione visite&nbsp;<input
				type="checkbox" name="bookingVisits" disabled="disabled">
			</label><br> <br> <label>Richiesta ricette&nbsp;<input
				type="checkbox" name="requestPrescriptions" disabled="disabled">
			</label> <br> <br> <label>Posti liberi a sedere&nbsp;<input
				type="text" name="freeSeats" disabled="disabled">
			</label> <br> <br> <label>Pazienti entrati&nbsp;<input type="text"
				name="entered" disabled="disabled">
			</label> <br> <br>
			<fieldset>
				<legend>Orario Visite</legend>
				<label>Lunedì dalle <input type="time" name="lunFrom"
					disabled="disabled"> alle <input type="time" name="lunTo"
					disabled="disabled"></label><br> <label>Martedì dalle <input
					type="time" name="marFrom" disabled="disabled"> alle <input
					type="time" name="marTo" disabled="disabled">
				</label><br> <label>Mercoledì dalle <input type="time" name="merFrom"
					disabled="disabled"> alle <input type="time" name="merTo"
					disabled="disabled"></label><br> <label>Giovedì dalle <input
					type="time" name="gioFrom" disabled="disabled"> alle <input
					type="time" name="gioTo" disabled="disabled">
				</label><br> <label>Venerdì dalle <input type="time" name="venFrom"
					disabled="disabled"> alle <input type="time" name="venTo"
					disabled="disabled"></label><br> <label>Sabato dalle <input
					type="time" name="sabFrom" disabled="disabled"> alle <input
					type="time" name="sabTo" disabled="disabled">
				</label><br>
			</fieldset>
			<button type="button" class="secondButton" id="buttonDoctor"
				onclick="doctorUpdate()">Salva</button>
		</form>
	</div>
</body>
</html>