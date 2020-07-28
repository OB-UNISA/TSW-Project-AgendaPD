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
		<h1 onclick="patientsOnClick()">
			&#127973;<span>Pazienti</span>
		</h1>
		<hr>
		<h1 onclick="secretaryOnClick()">
			&#128137;<span>Segretaria</span>
		</h1>
		<hr>
		<h1 onclick="iotOnClick()">
			&#128241;<span>IoT</span>
		</h1>
		<hr>
		<h1 onclick="newsOnClick(document.formInfoP['user'].value)">
			&#128240;<span>Communicazioni</span>
		</h1>
		<hr>
		<h1 onclick="settingsOnClick()">
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
			<label>Nome Dispositivo&nbsp;<input type="text" name="idiot"
				disabled="disabled"></label><br> <br> <label>Password&nbsp;<input
				type="password" name="passiot"> <input type="hidden"
				autocomplete="off"> <!-- to prevent Firefox disable "selected" attribute on select tag -->
			</label><br> <br> <label>Permessi&nbsp;<select name="permissions">
					<option value="0">Nessuno</option>
					<option value="1">Posti</option>
					<option value="2">Entrate</option>
					<option value="3">Entrambi</option>
			</select></label><br> <br> <label>Posti liberi&nbsp;<input type="text"
				name="freeSeats"></label> <br> <br> <label>Persone
				entrate&nbsp;<input type="text" name="enteredP">
			</label>
			<button type="button" class="secondButton" id="buttonIot"
				onclick="updateIot()">Salva</button>
		</form>
		<form id="news" name="formNews" class="visibleNone">
			<label>&nbsp;<textarea name="textNews"></textarea>
				<button type="button" class="secondButton" id="buttonNews"
					onclick="addNews()">Salva</button></label>
			<div id="divNews">
			</div>
		</form>
		<form id="settings" name="formSettings" class="visibleNone">
			<label>Prenotazione visite&nbsp;<input type="checkbox"
				name="bookingVisits"></label><br> <br> <label>Richiesta
				ricette&nbsp;<input type="checkbox" name="requestPrescriptions">
			</label> <br> <br> <label>Codice dottore&nbsp;<input type="text"
				name="doctorCode"></label><br> <br>
			<fieldset>
				<legend>Orario Visite</legend>
				<label>Lunedì dalle <input type="time" name="lunFrom"> alle
					<input type="time" name="lunTo"></label><br> <label>Martedì
					dalle <input type="time" name="marFrom"> alle <input type="time"
					name="marTo">
				</label><br> <label>Mercoledì dalle <input type="time" name="merFrom">
					alle <input type="time" name="merTo"></label><br> <label>Giovedì
					dalle <input type="time" name="gioFrom"> alle <input type="time"
					name="gioTo">
				</label><br> <label>Venerdì dalle <input type="time" name="venFrom">
					alle <input type="time" name="venTo"></label><br> <label>Sabato
					dalle <input type="time" name="sabFrom"> alle <input type="time"
					name="sabTo">
				</label><br>
			</fieldset>
			<button type="button" class="secondButton" id="buttonSettings"
				onclick="settingUpdate()">Salva</button>
		</form>
		<form id="secretary" name="formSecretary" class="visibleNone">
			<label>Nome Utente&nbsp;<input type="text" name="user"></label><br>
			<br> <label>Accesso pazienti&nbsp;<input type="checkbox"
				name="accessPatients">
			</label> <br> <br> <label>Accesso cartelle&nbsp;<input
				type="checkbox" name="accessMedicalRecords">
			</label> <br> <br> <label>Accesso ricette&nbsp;<input
				type="checkbox" name="accessPrescriptions">
			</label> <br> <br> <label>Accesso prenotazioni&nbsp;<input
				type="checkbox" name="accessBookedVisits">
			</label> <br> <br> <label>Modifica ricette&nbsp;<input
				type="checkbox" name="editPrescriptions">
			</label> <br> <br> <label>Aggiunta pazienti&nbsp;<input
				type="checkbox" name="addPatients">
			</label> <br> <br> <label>Eliminazione pazienti&nbsp;<input
				type="checkbox" name="deletePatients">
			</label> <br> <br> <label>Aggiunta communicazioni&nbsp;<input
				type="checkbox" name="addNews">
			</label> <br> <br> <label>Eliminazione communicazioni&nbsp;<input
				type="checkbox" name="deleteNews">
			</label> <br> <br>
			<button type="button" class="secondButton" id="buttonSecretary"
				onclick="secretaryUpdate()">Salva</button>
		</form>
		<form id="patients" name="formPatients" class="visibleNone">
			<label>Nome Utente&nbsp;<input type="text" name="user"></label>
			<button type="button" class="secondButton" id="buttonAddPatient"
				onclick="addPatient()">Aggiungi</button>
			<br> <br>
			<div id="divPatients"></div>
		</form>
		<form name="formFile" method="post" action="<%=response.encodeURL("filemanager")%>" target="_blank">
			<input type="hidden" name="mode"> <input type="hidden" name="obj">
			<input type="hidden" name="docUser"> <input type="hidden"
				name="patUser"> <input type="hidden" name="date"> <input
				type="hidden" name="typology"> <input type="file" name="file"
				accept="application/pdf" class="visibleNone">
		</form>
	</div>
</body>
</html>