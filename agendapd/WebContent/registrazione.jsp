<%@page import="agendapd.model.UtenteBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	List<String> errorsFound = (List<String>) request.getAttribute("errorsFound");
String errorClass = "error";
String role = "", firstName = "", lastName = "", birthday = "", fiscalCode = "", addressName = "", addressCity = "",
		addressCAP = "", email = "";
String accessBar = "selectedProBar", roleBar = "notSelectedProBar", infoBar = "notSelectedProBar";
UtenteBean bean = (UtenteBean) request.getAttribute("utenteBean");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Registrazione">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/registrazione.css" type="text/css" rel="stylesheet">
<link href="CSS/login.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript" src="JS/registrazione.js"></script>
<script type="text/javascript" src="JS/login.js"></script>
<title>AgendaPD</title>
<script type="text/javascript">
	$(document).ready(
			function() {
				document.getElementById('dateReg').max = new Date()
						.toISOString().split('T')[0];
				document.getElementById("userPass").childNodes[1].childNodes[1]
						.addEventListener("blur", onBlurUser);
			});
	var state = 1;
<%if (errorsFound != null && errorsFound.size() > 0) {
	if (errorsFound.contains("user")) {
		accessBar = errorClass;
	} else {%>
	formValid.user = true;
<%}
	if (errorsFound.contains("pass")) {
		accessBar = errorClass;
	} else {%>
	formValid.pass = true;
<%}
	if (errorsFound.contains("role"))
		roleBar = role = errorClass;
	if (errorsFound.contains("firstName"))
		infoBar = firstName = errorClass;
	else {%>
	formValid.firstName = true;
<%}
	if (errorsFound.contains("lastName"))
		infoBar = lastName = errorClass;
	else {%>
	formValid.lastName = true;
<%}
	if (errorsFound.contains("birthday"))
		infoBar = birthday = errorClass;
	else {%>
	formValid.birthday = true;
<%}
	if (errorsFound.contains("fiscalCode"))
		infoBar = fiscalCode = errorClass;
	else {%>
	formValid.fiscalCode = true;
<%}
	if (errorsFound.contains("addressName"))
		infoBar = addressName = errorClass;
	else {%>
	formValid.addressName = true;
<%}
	if (errorsFound.contains("addressCity"))
		infoBar = addressCity = errorClass;
	else {%>
	formValid.addressCity = true;
<%}
	if (errorsFound.contains("addressCAP"))
		infoBar = addressCAP = errorClass;
	else {%>
	formValid.addressCAP = true;
<%}
	if (errorsFound.contains("email"))
		infoBar = email = errorClass;
	else {%>
	formValid.email = true;
<%}
}%>
	
</script>
</head>
<body>
	<div>
		<%@ include file="nav.jsp"%>
		<form action="<%=response.encodeURL("registrazione")%>" method="post"
			name="formReg" id="form" class="shadowNav">
			<h1 id="info">Informazioni per l'accesso</h1>
			<hr>
			<div>
				<%@ include file="login.jsp"%>
				<div id="role">
					<label class="<%=role%>"><input type="radio" name="role" value="1"
						<%=(bean == null || bean.getRole() < 2) ? "checked" : ""%>><span>Paziente</span></label>
					<br> <br> <br> <label class="<%=role%>"><input
						type="radio" name="role" value="2"
						<%=(bean == null || bean.getRole() != 2) ? "" : "checked"%>><span>Segretaria</span></label>
					<br> <br> <br> <label class="<%=role%>"><input
						type="radio" name="role" value="3"
						<%=(bean == null || bean.getRole() != 3) ? "" : "checked"%>><span>Dottore</span></label>
				</div>
				<div id="personalInfo">
					<label class="<%=firstName%>">Nome&nbsp;<input type="text"
						name="firstName" placeholder="Mario" required="required"
						<%=(bean == null || bean.getFirstName() == null) ? "" : String.format("value=\"%s\"", bean.getFirstName())%>
						onkeyup="onlySomethingValidator('firstName', 'firstName', 'infoFirstName', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, false)"
						onblur="onlySomethingValidator('firstName', 'firstName', 'infoFirstName', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, true)">
					</label>
					<p id="infoFirstName">sono ammesse solo lettere e spazi</p>
					<br> <label class="<%=lastName%>">Cognome&nbsp;<input
						type="text" name="lastName" placeholder="Rossi" required="required"
						<%=(bean == null || bean.getLastName() == null) ? "" : String.format("value=\"%s\"", bean.getLastName())%>
						onkeyup="onlySomethingValidator('lastName', 'lastName', 'infoLastName', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, false)"
						onblur="onlySomethingValidator('lastName', 'lastName', 'infoLastName', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, true)">
					</label>
					<p id="infoLastName">sono ammesse solo lettere e spazi</p>
					<br> <label class="<%=birthday%>">Data di nascita&nbsp;<input
						id="dateReg" type="date" name="birthday" required="required"
						<%=(bean == null || bean.getBirthday() == null) ? "" : String.format("value=\"%s\"", bean.getBirthday())%>
						onchange="formValid.birthday=true">
					</label> <br> <br> <label class="<%=fiscalCode%>">Codice
						fiscale&nbsp;<input type="text" name="fiscalCode"
						placeholder="Codice fiscale" required="required"
						<%=(bean == null || bean.getFiscalCode() == null) ? "" : String.format("value=\"%s\"", bean.getFiscalCode())%>
						onkeyup="fiscalCodeValidator()"><span id="infoFiscalCodeChars">inserire
							16 caratteri</span>
					</label>
					<p id="infoFiscalCode">sono ammesse solo lettere e numeri</p>
					<br> <label class="<%=addressName%>">Via&nbsp;<input
						type="text" name="addressName" placeholder="Vittorio Emanuele"
						required="required"
						<%=(bean == null || bean.getAddressName() == null) ? "" : String.format("value=\"%s\"", bean.getAddressName())%>
						onkeyup="onlySomethingValidator('addressName', 'addressName', 'infoAddressName', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, false)"
						onblur="onlySomethingValidator('addressName', 'addressName', 'infoAddressName', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, true)">
					</label>
					<p id="infoAddressName">sono ammesse solo lettere e spazi</p>
					<br> <label class="<%=addressCity%>">Città&nbsp;<input
						type="text" name="addressCity" placeholder="Milano" required="required"
						<%=(bean == null || bean.getAddressCity() == null) ? "" : String.format("value=\"%s\"", bean.getAddressCity())%>
						onkeyup="onlySomethingValidator('addressCity', 'addressCity', 'infoAddressCity', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, false)"
						onblur="onlySomethingValidator('addressCity', 'addressCity', 'infoAddressCity', /[A-Za-zÀ-ÖØ-öø-ÿ]| /, true)">
					</label>
					<p id="infoAddressCity">sono ammesse solo lettere e spazi</p>
					<br> <label class="<%=addressCAP%>">CAP&nbsp;<input
						type="text" name="addressCAP" placeholder="20019" required="required"
						<%=(bean == null || bean.getAddressCAP() == 0) ? "" : String.format("value=\"%s\"", bean.getAddressCAP())%>
						onkeyup="onlySomethingValidator('addressCAP', 'addressCAP', 'infoAddressCAP', /[0-9]/, false)"
						onblur="onlySomethingValidator('addressCAP', 'addressCAP', 'infoAddressCAP', /[0-9]/, true)">
					</label>
					<p id="infoAddressCAP">sono ammessi solo numeri</p>
					<br> <label class="<%=email%>">E-mail&nbsp;<input type="text"
						name="email" placeholder="marioRossi@esempio.it" required="required"
						<%=(bean == null || bean.getEmail() == null) ? "" : String.format("value=\"%s\"", bean.getEmail())%>
						onkeyup="emailValidator()">
					</label>
					<p id="infoEmail">seguire il formato indicato</p>
				</div>
			</div>
			<div id="buttonNext">
				<button id="sendInfo" class="secondButton" type="button"
					onclick="buttonNext()">Avanti</button>
				<p id="infoNextButton"></p>
			</div>
			<div id="progressBar" class="shadowNav">
				<h1 class="<%=accessBar%>">Accesso</h1>
				<hr>
				<h1 class="<%=roleBar%>">Ruolo</h1>
				<hr>
				<h1 class="<%=infoBar%>">Anagrafe</h1>
			</div>
		</form>
	</div>
</body>
</html>