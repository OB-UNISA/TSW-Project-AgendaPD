function updatePersonalInfo() {
	var form = document.formInfoP;
	var pInfo = {
		user: form["user"].value,
		pass: form["pass"].value,
		email: form["email"].value,
		firstName: form["firstName"].value,
		lastName: form["lastName"].value,
		fiscalCode: form["fiscalCode"].value,
		birthday: form["birthday"].value,
		addressName: form["addressName"].value,
		addressCity: form["addressCity"].value,
		addressCAP: form["addressCAP"].value,
	};

	var jsonStr = JSON.stringify(pInfo);

	$.ajax({
		url: "./utente", type: "POST", data: { jsonStr: jsonStr, mode: "updateUser" }, timeout: 4000, success: function(result) {
			if (result != "error") {
				var errors = JSON.parse(result);
				var nodes = document.formInfoP.childNodes;
				if (errors.length > 0) {
					if (errors.user == true)
						nodes[1].style.backgroundColor = "red";
					if (errors.password == true)
						nodes[6].style.backgroundColor = "red";
					if (errors.email == true)
						nodes[11].style.backgroundColor = "red";
					if (errors.firstName == true)
						nodes[17].style.backgroundColor = "red";
					if (errors.lastName == true)
						nodes[22].style.backgroundColor = "red";
					if (errors.fiscalCode == true)
						nodes[27].style.backgroundColor = "red";
					if (errors.birthday == true)
						nodes[32].style.backgroundColor = "red";
					if (errors.addressName == true)
						nodes[37].style.backgroundColor = "red";
					if (errors.addressCity == true)
						nodes[42].style.backgroundColor = "red";
					if (errors.addressCAP == true)
						nodes[47].style.backgroundColor = "red";
					document.getElementById("buttonPersonalInfo").style.backgroundColor = "red";
				}
				else {
					nodes[6].style.backgroundColor = "transparent";
					nodes[11].style.backgroundColor = "transparent";
					nodes[17].style.backgroundColor = "transparent";
					nodes[22].style.backgroundColor = "transparent";
					nodes[27].style.backgroundColor = "transparent";
					nodes[32].style.backgroundColor = "transparent";
					nodes[37].style.backgroundColor = "transparent";
					nodes[42].style.backgroundColor = "transparent";
					nodes[47].style.backgroundColor = "transparent";
					document.getElementById("buttonPersonalInfo").style.backgroundColor = "green";
				}
			}
			else
				document.getElementById("buttonPersonalInfo").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonPersonalInfo").style.backgroundColor = "red";
		}
	});
}

function sideBar(name, nodeNumber) {
	var nodes = document.getElementById("sideBar").childNodes;
	for (let i = 1; i < nodes.length; i = i + 4)
		if (nodes[i].innerHTML.match(name) == null)
			nodes[i].className = "";
		else
			nodes[i].className = "selected";

	var nodes2 = document.getElementById("viewArea").childNodes;
	for (let i = 1; i < nodes2.length; i = i + 2)
		if (i != nodeNumber)
			nodes2[i].className = "visibleNone font"
		else
			nodes2[i].className = "visibleBlock font";
}

function iotOnClick() {
	var user = document.formInfoP["user"].value;
	var json = {
		id: user
	};
	var str = JSON.stringify(json);
	$.ajax({
		url: "./iot", type: "POST", data: { jsonObj: str, mode: "getinfoiot" }, timeout: 4000, success: function(result) {
			var obj = JSON.parse(result);
			if (result != "ERROR" && obj.info == "OK") {
				var obj = JSON.parse(result);
				var form = document.formIot;
				form["idiot"].value = obj.id;
				form["passiot"].value = obj.pass;
				if (obj.freeSeats == -1)
					form["freeSeats"].value = "nessun dato";
				else
					form["freeSeats"].value = obj.freeSeats;
				if (obj.entered == -1)
					form["enteredP"].value = "nessun dato";
				else
					form["enteredP"].value = obj.entered;
				let sel = form["permissions"];
				for (let i = 0; i < 4; i++)
					if (i == obj.permissions)
						sel[i].selected = true;
					else
						sel[i].selected = false;
				document.getElementById("buttonIot").style.backgroundColor = "#7fe1f0";
			}
			else
				document.getElementById("buttonIot").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonIot").style.backgroundColor = "red";
		}
	});

	sideBar("IoT", 3);
}

function updateIot() {
	var sel = document.formIot["permissions"];
	var seats = document.formIot["freeSeats"].value;
	if (seats == "nessun dato")
		seats = -1;
	var entered = document.formIot["enteredP"].value;
	if (entered == "nessun dato")
		entered = -1;
	var iotInfo = {
		id: document.formIot["idiot"].value,
		pass: document.formIot["passiot"].value,
		permissions: sel.options[sel.selectedIndex].value,
		freeSeats: seats,
		entered: entered
	};

	var str = JSON.stringify(iotInfo);

	$.ajax({
		url: "./iot", type: "POST", data: { jsonObj: str, mode: "updateinfoiot" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK")
				document.getElementById("buttonIot").style.backgroundColor = "green";
			else
				document.getElementById("buttonIot").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonIot").style.backgroundColor = "red";
		}
	});
}

function newsOnClick(user) {
	var obj = {
		user: user,
	};

	$.ajax({
		url: "./news", type: "POST", data: { news: JSON.stringify(obj), mode: "getnews" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				var obj = JSON.parse(result);
				var coll = obj.coll;
				let length = coll.length;
				var div = document.getElementById("divNews");
				if (div != undefined)
					div.remove();
				div = document.createElement("div");
				div.id = "divNews";
				document.formNews.appendChild(div);
				var divNodes = div.childNodes;
				var divNodesLength = divNodes.length;
				for (let i = 0; i < divNodesLength; i++)
					divNodes[i].remove;
				for (let i = 0; i < length; i++) {
					let header = document.createElement("header");
					let button = document.createElement("span");
					button.appendChild(document.createTextNode("× "));
					header.appendChild(button);
					header.appendChild(document.createTextNode(coll[i].doctorUser + " - " + coll[i].timeDateNews));
					let article = document.createElement("article");
					let p = document.createElement("p");
					p.appendChild(document.createTextNode(coll[i].description));
					article.append(p);
					div.appendChild(header);
					div.appendChild(article);
					button.onclick = function() {
						let newsToRemove = {
							user: obj.user,
							time: coll[i].timeDateNews
						};

						$.ajax({
							url: "./news", type: "POST", data: { news: JSON.stringify(newsToRemove), mode: "deletenews" }, timeout: 4000, success: function(result) {
								if (result != "ERROR" && JSON.parse(result).info == "OK") {
									header.remove();
									article.remove();
								}
								else
									button.style.backgroundColor = "red";

							}, error: function(xhr, status, error) {
								document.getElementById("buttonNews").style.backgroundColor = "red";
							}
						});
					};
				}
				document.getElementById("buttonNews").style.backgroundColor = "#7fe1f0";
			}
			else
				document.getElementById("buttonNews").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonNews").style.backgroundColor = "red";
		}
	});

	sideBar("Communicazioni", 5);
}

function addNews() {

	var obj = {
		user: document.formInfoP["user"].value,
		text: document.formNews["textNews"].value,
		time: new Date().toISOString().split("Z")[0]
	};

	$.ajax({
		url: "./news", type: "POST", data: { news: JSON.stringify(obj), mode: "addnews" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				newsOnClick(obj.user);

				document.getElementById("buttonNews").style.backgroundColor = "green";
			}
			else
				document.getElementById("buttonNews").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonNews").style.backgroundColor = "red";
		}
	});
}

function settingUpdate() {
	var form = document.formSettings;
	var obj = {
		doctorUser: document.formInfoP['user'].value,
		bookingVisits: form["bookingVisits"].checked,
		requestPrescriptions: form["requestPrescriptions"].checked,
		doctorCode: form["doctorCode"].value,
		visitingHours: {
			lun: {
				from: form["lunFrom"].value,
				to: form["lunTo"].value
			},
			mar: {
				from: form["marFrom"].value,
				to: form["marTo"].value
			},
			mer: {
				from: form["merFrom"].value,
				to: form["merTo"].value
			},
			gio: {
				from: form["gioFrom"].value,
				to: form["gioTo"].value
			},
			ven: {
				from: form["venFrom"].value,
				to: form["venTo"].value
			},
			sab: {
				from: form["sabFrom"].value,
				to: form["sabTo"].value
			}
		}
	};

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./dottore", type: "POST", data: { jsonObj: str, mode: "updateinfo" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				settingsOnClick();
				setTimeout(function() {
					document.getElementById("buttonSettings").style.backgroundColor = "green";
				}, 1000);
			}
			else
				document.getElementById("buttonSettings").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonSettings").style.backgroundColor = "red";
		}
	});

}

function settingsOnClick() {
	var form = document.formSettings;
	var obj = {
		doctorUser: document.formInfoP['user'].value
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./dottore", type: "POST", data: { jsonObj: str, mode: "getinfo" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				var doctor = JSON.parse(result);
				form["bookingVisits"].checked = doctor.bookingVisits;
				form["requestPrescriptions"].checked = doctor.requestPrescriptions;
				form["doctorCode"].value = doctor.doctorCode;
				form["lunFrom"].value = doctor.visitingHours.lun.from;
				form["lunTo"].value = doctor.visitingHours.lun.to;
				form["marFrom"].value = doctor.visitingHours.mar.from;
				form["marTo"].value = doctor.visitingHours.mar.to;
				form["merFrom"].value = doctor.visitingHours.mer.from;
				form["merTo"].value = doctor.visitingHours.mer.to;
				form["gioFrom"].value = doctor.visitingHours.gio.from;
				form["gioTo"].value = doctor.visitingHours.gio.to;
				form["venFrom"].value = doctor.visitingHours.ven.from;
				form["venTo"].value = doctor.visitingHours.ven.to;
				form["sabFrom"].value = doctor.visitingHours.sab.from;
				form["sabTo"].value = doctor.visitingHours.sab.to;

				document.getElementById("buttonSettings").style.backgroundColor = "#7fe1f0";
			}
			else
				document.getElementById("buttonSettings").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonSettings").style.backgroundColor = "red";
		}
	});

	sideBar("Impostazioni", 7);
}

function secretaryOnClick() {
	var form = document.formSecretary;
	var obj = {
		doctorUser: document.formInfoP['user'].value
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./segretaria", type: "POST", data: { jsonObj: str, mode: "getinfo" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				var secretary = JSON.parse(result);
				form["user"].value = secretary.secretaryUser;
				form["accessPatients"].checked = secretary.accessPatients;
				form["accessMedicalRecords"].checked = secretary.accessMedicalRecords;
				form["accessPrescriptions"].checked = secretary.accessPrescriptions;
				form["accessBookedVisits"].checked = secretary.accessBookedVisits;
				form["editPrescriptions"].checked = secretary.editPrescriptions;
				form["addPatients"].checked = secretary.addPatients;
				form["deletePatients"].checked = secretary.deletePatients;
				form["addNews"].checked = secretary.addNews;
				form["deleteNews"].checked = secretary.deleteNews;

				document.getElementById("buttonSecretary").style.backgroundColor = "#7fe1f0";
			}
			else
				document.getElementById("buttonSecretary").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonSecretary").style.backgroundColor = "red";
		}
	});

	sideBar("Segretaria", 9);

}

function secretaryUpdate() {
	var form = document.formSecretary;
	var obj = {
		doctorUser: document.formInfoP['user'].value,
		secretaryUser: form["user"].value,
		accessPatients: form["accessPatients"].checked,
		accessMedicalRecords: form["accessMedicalRecords"].checked,
		accessPrescriptions: form["accessPrescriptions"].checked,
		accessBookedVisits: form["accessBookedVisits"].checked,
		editPrescriptions: form["editPrescriptions"].checked,
		addPatients: form["addPatients"].checked,
		deletePatients: form["deletePatients"].checked,
		addNews: form["addNews"].checked,
		deleteNews: form["deleteNews"].checked
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./segretaria", type: "POST", data: { jsonObj: str, mode: "updateinfo" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK")
				document.getElementById("buttonSecretary").style.backgroundColor = "green";
			else
				document.getElementById("buttonSecretary").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonSecretary").style.backgroundColor = "red";
		}
	});
}

function patientsOnClick() {
	formPatients.childNodes[1].style.display = "inline";
	formPatients.childNodes[3].style.display = "inline";
	var obj = {
		doctorUser: document.formInfoP['user'].value
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./paziente", type: "POST", data: { jsonObj: str, mode: "getinfopatients" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				var patients = JSON.parse(result);
				var coll = patients.patients;
				let length = coll.length;
				var div = document.getElementById("divPatients");
				if (div != undefined)
					div.remove();
				div = document.createElement("div");
				div.id = "divPatients";
				document.formPatients.appendChild(div);
				var labels = ["Rimuovi", "Cartelle", "Ricette", "Nome", "Cognome", "Codice fiscale", "Data di nascita", "Email", "Indirizzo"];
				var labelsIcons = ["❌", "📂", "📝"];
				var table = document.createElement("table");
				div.appendChild(table);
				var tr0 = document.createElement("tr");
				for (let i = 0; i < 3; i++) {
					let th = document.createElement("th");
					let thText = document.createTextNode(labelsIcons[i]);
					let span = document.createElement("span");
					let spanText = document.createTextNode(labels[i])
					span.appendChild(spanText);
					th.appendChild(thText);
					th.appendChild(span);
					tr0.appendChild(th);
				}
				for (let i = 3; i < labels.length; i++) {
					let th = document.createElement("th");
					let thText = document.createTextNode(labels[i]);
					th.appendChild(thText);
					tr0.appendChild(th);
				}
				table.append(tr0);

				var formFile = document.createElement("form");
				formFile.action = "filemanager";
				formFile.method = "post";
				formFile.target = "_blank";
				formFile.name = "formFile";
				var inputMode = document.createElement("input");
				inputMode.name = "mode";
				inputMode.type = "hidden";
				var inputObj = document.createElement("input");
				inputObj.name = "obj";
				inputObj.type = "hidden";
				var inputDocUser = document.createElement("input");
				inputDocUser.name = "docUser";
				inputDocUser.type = "hidden";
				var inputPatUser = document.createElement("input");
				inputPatUser.name = "patUser";
				inputPatUser.type = "hidden";
				var inputDate = document.createElement("input");
				inputDate.name = "date";
				inputDate.type = "hidden";
				var inputTypology = document.createElement("input");
				inputTypology.name = "typology";
				inputTypology.type = "hidden";
				var inputFile = document.createElement("input");
				inputFile.name = "file";
				inputFile.type = "file";
				inputFile.accept = "application/pdf";
				inputFile.style.display = "none";
				var buttonUploadFile = document.createElement("button");
				buttonUploadFile.type = "button";
				buttonUploadFile.className = "secondButton";
				buttonUploadFile.innerHTML = "Invia";
				buttonUploadFile.style.display = "none";
				var br = document.createElement("br");
				formFile.appendChild(inputMode);
				formFile.appendChild(inputObj);
				formFile.appendChild(inputDocUser);
				formFile.appendChild(inputPatUser);
				formFile.appendChild(inputDate);
				formFile.appendChild(inputTypology);
				formFile.appendChild(inputFile);
				formFile.appendChild(buttonUploadFile);
				formFile.appendChild(br);
				div.appendChild(formFile);


				for (let i = 0; i < length; i++) {
					let tr = document.createElement("tr");
					let td1 = document.createElement("td");
					let td1Text = document.createTextNode(labelsIcons[0]);
					td1.appendChild(td1Text);
					td1.className = "button";
					td1.addEventListener("click", function() {
						let pRemove = {
							doctorUser: obj.doctorUser,
							patientRemove: coll[i].user
						}
						let st = JSON.stringify(pRemove);
						$.ajax({
							url: "./paziente", type: "POST", data: { jsonObj: st, mode: "removepatient" }, timeout: 4000, success: function(result) {
								if (result != "ERROR" && JSON.parse(result).info == "OK") {
									tr.remove();
								} else
									td1.style.backgroundColor = "red";
							}, error: function(xhr, status, error) {
								td1.style.backgroundColor = "red";
							}
						});

					});
					tr.appendChild(td1);
					let td2 = document.createElement("td");
					let td2Text = document.createTextNode(labelsIcons[1]);
					td2.appendChild(td2Text);
					td2.className = "button";
					td2.addEventListener("click", function() {
						let user = {
							doctorUser: obj.doctorUser,
							patientUser: coll[i].user
						}
						let st = JSON.stringify(user);
						$.ajax({
							url: "./paziente", type: "POST", data: { jsonObj: st, mode: "getrecords" }, timeout: 4000, success: function(result) {
								if (result != "ERROR" && JSON.parse(result).info == "OK") {
									var records = JSON.parse(result);
									var coll = records.records;
									var length = coll.length;
									div.childNodes[0].remove();
									formPatients.childNodes[1].style.display = "none";
									formPatients.childNodes[3].style.display = "none";
									let labels = ["Data", "Tipologia", "Luogo", "Esito", "Cartella"]
									let table = document.createElement("table");
									div.appendChild(table);
									let tr0 = document.createElement("tr");
									for (let i = 0; i < labels.length; i++) {
										let th = document.createElement("th");
										let thText = document.createTextNode(labels[i]);
										th.appendChild(thText);
										tr0.appendChild(th);
									}
									table.appendChild(tr0);
									for (let i = 0; i < length; i++) {
										let tr = document.createElement("tr");
										let td1 = document.createElement("td");
										let td1Text = document.createTextNode(coll[i].medicalRecordDate);
										td1.appendChild(td1Text);
										tr.appendChild(td1);

										let td2 = document.createElement("td");
										let td2Text = document.createTextNode(coll[i].typology);
										td2.appendChild(td2Text);
										tr.appendChild(td2);

										let td3 = document.createElement("td");
										let td3Text = document.createTextNode(coll[i].place);
										td3.appendChild(td3Text);
										tr.appendChild(td3);

										let td4 = document.createElement("td");
										let td4Text = document.createTextNode(coll[i].result);
										td4.appendChild(td4Text);
										tr.appendChild(td4);

										let td5 = document.createElement("td");
										let td5Text = document.createTextNode("💾");
										td5.appendChild(td5Text);
										td5.className = "button";
										td5.addEventListener("click", function() {
											formFile["mode"].value = "download";
											formFile["obj"].value = "record";
											formFile["docUser"].value = document.formInfoP['user'].value;
											formFile["patUser"].value = coll[i].patientUser;
											formFile["date"].value = coll[i].medicalRecordDate;
											formFile["typology"].value = coll[i].typology;
											formFile.submit();
										});
										tr.appendChild(td5);
										table.appendChild(tr);

									}
								} else
									td2.style.backgroundColor = "red";
							}, error: function(xhr, status, error) {
								td2.style.backgroundColor = "red";
							}
						});
					});
					tr.appendChild(td2);
					let td3 = document.createElement("td");
					let td3Text = document.createTextNode(labelsIcons[2]);
					td3.appendChild(td3Text);
					td3.className = "button";
					td3.addEventListener("click", function() {
						let user = {
							doctorUser: obj.doctorUser,
							patientUser: coll[i].user
						}
						let st = JSON.stringify(user);
						$.ajax({
							url: "./paziente", type: "POST", data: { jsonObj: st, mode: "getprescriptions" }, timeout: 4000, success: function(result) {
								if (result != "ERROR" && JSON.parse(result).info == "OK") {
									var prescriptions = JSON.parse(result);
									var coll = prescriptions.prescriptions;
									var length = coll.length;
									div.childNodes[0].remove();
									formPatients.childNodes[1].style.display = "none";
									formPatients.childNodes[3].style.display = "none";
									formFile["file"].style.display = "inline";
									buttonUploadFile.style.display = "inline";
									buttonUploadFile.addEventListener("click", function() {
										formFile.enctype = "multipart/form-data";
										formFile["mode"].value = "upload";
										formFile["obj"].value = "prescription";
										formFile["docUser"].value = document.formInfoP['user'].value;
										formFile["patUser"].value = user.patientUser;
										formFile["date"].value = new Date().toISOString().split("Z")[0];
										formFile.submit();
									});
									let labels = ["Rimuovi", "Data orario", "Ricetta"];
									let table = document.createElement("table");
									div.appendChild(table);
									let tr0 = document.createElement("tr");
									for (let i = 0; i < labels.length; i++) {
										let th = document.createElement("th");
										let thText = document.createTextNode(labels[i]);
										th.appendChild(thText);
										tr0.appendChild(th);
									}
									table.appendChild(tr0);
									for (let i = 0; i < length; i++) {
										let tr = document.createElement("tr");
										let td1 = document.createElement("td");
										let td1Text = document.createTextNode(labelsIcons[0]);
										td1.appendChild(td1Text);
										td1.addEventListener("click", function() {
											let pRemove = {
												doctorUser: coll[i].doctorUser,
												patientUser: coll[i].patientUser,
												date: coll[i].timeDatePrescription
											}
											let st = JSON.stringify(pRemove);
											$.ajax({
												url: "./paziente", type: "POST", data: { jsonObj: st, mode: "removeprescription" }, timeout: 4000, success: function(result) {
													if (result != "ERROR" && JSON.parse(result).info == "OK") {
														tr.remove();
													} else
														td1.style.backgroundColor = "red";
												}, error: function(xhr, status, error) {
													td1.style.backgroundColor = "red";
												}
											});

										});
										tr.appendChild(td1);

										let td2 = document.createElement("td");
										let td2Text = document.createTextNode(coll[i].timeDatePrescription);
										td2.appendChild(td2Text);
										tr.appendChild(td2);

										let td3 = document.createElement("td");
										let td3Text = document.createTextNode("💾");
										td3.appendChild(td3Text);
										td3.className = "button";
										td3.addEventListener("click", function() {
											formFile.enctype = "multipart/form-data";
											formFile["mode"].value = "download";
											formFile["obj"].value = "prescription";
											formFile["docUser"].value = document.formInfoP['user'].value;
											formFile["patUser"].value = coll[i].patientUser;
											formFile["date"].value = coll[i].timeDatePrescription;
											formFile.submit();
										});
										tr.appendChild(td3);
										table.appendChild(tr);
									}
								} else
									td3.style.backgroundColor = "red";
							}, error: function(xhr, status, error) {
								td3.style.backgroundColor = "red";
							}
						});
					});
					tr.appendChild(td3);
					let td4 = document.createElement("td");
					let td4Text = document.createTextNode(coll[i].firstName);
					td4.appendChild(td4Text);
					tr.appendChild(td4);
					let td5 = document.createElement("td");
					let td5Text = document.createTextNode(coll[i].lastName);
					td5.appendChild(td5Text);
					tr.appendChild(td5);
					let td6 = document.createElement("td");
					let td6Text = document.createTextNode(coll[i].fiscalCode);
					td6.appendChild(td6Text);
					tr.appendChild(td6);
					let td7 = document.createElement("td");
					let td7Text = document.createTextNode(coll[i].birthday);
					td7.appendChild(td7Text);
					tr.appendChild(td7);
					let td8 = document.createElement("td");
					let td8Text = document.createTextNode(coll[i].email);
					td8.appendChild(td8Text);
					tr.appendChild(td8);
					let td9 = document.createElement("td");
					let td9Text = document.createTextNode(coll[i].addressName + ", " + coll[i].addressCity + ", " + coll[i].addressCAP);
					td9.appendChild(td9Text);
					tr.appendChild(td9);
					table.appendChild(tr);
				}

				document.getElementById("buttonAddPatient").style.backgroundColor = "#7fe1f0";
			}
			else
				document.getElementById("buttonAddPatient").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonAddPatient").style.backgroundColor = "red";
		}
	});

	sideBar("Pazienti", 11);
}

function addPatient() {
	var form = document.formPatients;
	var obj = {
		doctorUser: document.formInfoP['user'].value,
		addPatient: form["user"].value
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./paziente", type: "POST", data: { jsonObj: str, mode: "addpatient" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				patientsOnClick();

				document.getElementById("buttonAddPatient").style.backgroundColor = "green";
			}
			else
				document.getElementById("buttonAddPatient").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonAddPatient").style.backgroundColor = "red";
		}
	});

}



