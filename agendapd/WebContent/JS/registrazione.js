var formValid = {
	user: false,
	pass: false,
	firstName: false,
	lastName: false,
	birthday: false,
	fiscalCode: false,
	addressName: false,
	addressCity: false,
	addressCAP: false,
	email: false
}

function onBlurUser() {
	userExists("formReg", "user", "infoUser", " già esistente", " accettato", formValid);
}

function fiscalCodeValidator() {
	formValid.fiscalCode = false;
	var text = document.forms["formReg"]["fiscalCode"].value;
	document.forms["formReg"]["fiscalCode"].value = text.toUpperCase();
	validateForm("formReg", "fiscalCode", /[A-Z]|[0-9]/, "infoFiscalCode");

	var length = document.forms["formReg"]["fiscalCode"].value.length
	let el = document.getElementById("infoFiscalCodeChars");
	el.style.color = errorColor;
	if (length < 16) {
		let charsNumber = 16 - length;
		el.innerHTML = "inserire " + charsNumber + " caratteri";
	} else if (length > 16) {
		let charsNumber = length - 16;
		el.innerHTML = "eliminare " + charsNumber + " caratteri";
	} else {
		let el2 = document.getElementById("infoFiscalCode");
		el2.style.color = successColor;
		el2.innerHTML = "ok";
		el.style.color = successColor;
		el.innerHTML = "codice fiscale accettato";
		formValid.fiscalCode = true;
	}
}

function emailValidator() {
	formValid.email = false;
	var el = document.getElementById("infoEmail");
	if (document.forms["formReg"]["email"].value.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/)) {
		el.style.color = successColor;
		el.innerHTML = "email accettata";
		formValid.email = true;
	}
	else {
		el.style.color = errorColor;
		el.innerHTML = "email non corretta";
	}
}

function buttonNext() {
	var el = document.getElementById("infoNextButton");
	var el2 = document.getElementById("info");
	var nodes = document.getElementById("progressBar").childNodes;
	switch (state) {
		case 1: if (formValid.user && formValid.pass) {
			nodes[1].className = "completedProBar";
			nodes[5].className = "selectedProBar";
			document.getElementById("userPass").style.display = "none";
			el.innerHTML = "";
			el2.innerHTML = "Scelta ruolo";
			document.getElementById("role").style.display = "block";
			state++;
		}
		else {
			el.style.color = errorColor;
			el.innerHTML = "inserire i dati richiesti prima di procedere";
		}
			break;

		case 2: document.getElementById("role").style.display = "none";
			nodes[5].className = "completedProBar";
			nodes[9].className = "selectedProBar";
			el2.innerHTML = "Informazioni personali";
			document.getElementById("role").style.display = "none";
			document.getElementById("personalInfo").style.display = "block";
			let button = document.getElementById("sendInfo");
			button.innerHTML = "Invia";
			state++;
			break;

		case 3: let valid = true;
			for (let prop in formValid)
				if (formValid[prop] != true)
					valid = false;
			if (valid) {
				el.innerHTML = "";
				nodes[9].className = "completedProBar";
				document.formReg.submit();
			}
			else {
				el.style.color = errorColor;
				el.innerHTML = "inserire i dati richiesti prima di procedere";
			}
			break;

		default: break;
	}
}

function userExists(formId, inputId, infoId, msgTrue, msgFalse, obj) {
	obj.user = false;
	var user = document.forms[formId][inputId].value;
	var info = document.getElementById(infoId);
	info.innerHTML = "controllo unicità nome utente...";
	$.ajax({
		url: "./accesso", type: "POST", data: { userName: user, mode: "userExists" }, timeout: 4000, success: function(result) {
			if (result == "true") {
				info.style.color = errorColor;
				info.innerHTML = "nome utente" + msgTrue;
			}
			else {
				obj.user = true;
				info.style.color = successColor;
				info.innerHTML = "nome utente" + msgFalse;
			}
		}, error: function(xhr, status, error) {
			info.style.color = errorColor;
			info.innerHTML = "riprova più tardi";
		}
	});
}