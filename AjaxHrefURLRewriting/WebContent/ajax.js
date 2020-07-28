function getID() {
	$.ajax({
		url: "./ajax",
		type: "GET",
		timeout: 4000,
		success: function(result) {
			document.body.childNodes[3].childNodes[1].innerHTML = result;
		},
		error: function(xhr, status, error) {
			document.body.childNodes[3].childNodes[1].innerHTML = "error";
		}
	});
}

function getIDRewrite() {
	$.ajax({
		url: "./ajax;jsessionid=" + ID,
		type: "GET",
		timeout: 4000,
		success: function(result) {
			document.body.childNodes[3].childNodes[1].innerHTML = result;
		},
		error: function(xhr, status, error) {
			document.body.childNodes[3].childNodes[1].innerHTML = "error";
		}
	});
}