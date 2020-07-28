function passwordValidator() {
	formValid.pass = false;
	var text = document.forms["formReg"]["pass"].value;
	var el = document.getElementById("infoPass");

	if (text.match(/[A-Z]/) == null) {
		el.style.color = errorColor;
		el.innerHTML = "inserire almeno una <b>lettera maiuscola</b>";
	}
	else if (text.match(/[0-9]/) == null) {
		el.innerHTML = "inserire almeno un <b>numero</b>";
	}
	else {
		formValid.pass = true;
		el.style.color = successColor;
		el.innerHTML = "password accettata";
	}
}