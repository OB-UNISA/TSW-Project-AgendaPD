var formValid = {
	user: false,
	pass: false
}

function send() {
	if (formValid.user && formValid.pass)
		document.formReg.submit();
	else {
		let el = document.getElementById("infoNextButton");
		el.style.color = errorColor;
		el.innerHTML = "inserire i dati richiesti prima di procedere";
	}
}