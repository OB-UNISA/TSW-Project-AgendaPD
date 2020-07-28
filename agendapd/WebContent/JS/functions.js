var errorColor = "#990000";
var successColor = "#006400";

function setDisplay(selector, value) {
	if (selector.charAt(0) == "#")
		document.getElementById(selector.slice(1, selector.lentht)).style.display = value;
	else {
		var nodes = document.getElementByClassName(selector.slice(1, selector.lentht));
		var lenght = nodes.lenght;
		for (let i = 0; i < lenght; i++)
			nodes[i].style.display = value;
	}
}

function validateForm(formName, inputName, pattern, infoId) {
	var text = document.forms[formName][inputName].value;
	var length = text.length;
	var lastChar = text.charAt(length - 1);
	var validator = lastChar.match(pattern);
	var el = document.getElementById(infoId);

	if (validator == null) {
		document.forms[formName][inputName].value = text.slice(0, length - 1);
		el.style.color = errorColor;
		el.innerHTML = "Il carattere <b>" + lastChar + "</b> non è ammesso";
	}
	else {
		for (let i = 0; i < length - 1; i++) {
			let c = text.charAt(i);
			if (c.match(pattern) == null) {
				el.style.color = errorColor;
				el.innerHTML = "Il carattere <b>" + text.charAt(i) + "</b> non è ammesso";
				let str = text.slice(0, i);
				str += text.slice(i + 1, length);
				document.forms[formName][inputName].value = str;
				break;
			}
		}
	}
}

function onlySomethingValidator(prop, inputId, infoId, pattern, blur) {
	validateForm("formReg", inputId, pattern, infoId);
	if (document.forms["formReg"][inputId].value == "")
		formValid[prop] = false;
	else {
		formValid[prop] = true;
		if (blur) {
			let el = document.getElementById(infoId);
			el.style.color = successColor;
			el.innerHTML = "ok";
		}
	}
}

function accountManage(name, op) {
	document.forms["formNav"]["accountName"].value = name;
	document.forms["formNav"]["accountOP"].value = op;
	document.forms["formNav"].submit();
}