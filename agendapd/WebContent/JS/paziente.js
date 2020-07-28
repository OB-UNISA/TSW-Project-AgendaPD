function prescriptionsOnClick() {
	var user = {
		doctorUser: "",
		patientUser: document.formInfoP['user'].value
	}
	let st = JSON.stringify(user);
	$.ajax({
		url: "./paziente", type: "POST", data: { jsonObj: st, mode: "getprescriptions" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				var prescriptions = JSON.parse(result);
				var coll = prescriptions.prescriptions;
				var length = coll.length;
				var div = document.getElementById("divPatients");
				if (div != undefined)
					div.remove();
				div = document.createElement("div");
				div.id = "divPatients";
				document.formPatients.appendChild(div);

				var formFile = document.formFile;
				formFile["date"].type = "hidden";
				formFile["typology"].type = "hidden";
				formFile["place"].type = "hidden";
				formFile["result"].type = "hidden";
				formFile["file"].type = "hidden";

				let labels = ["Data orario", "Ricetta"];
				var table = document.createElement("table");
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
					let td1Text = document.createTextNode(coll[i].timeDatePrescription);
					td1.appendChild(td1Text);
					tr.appendChild(td1);

					let td2 = document.createElement("td");
					let td2Text = document.createTextNode("💾");
					td2.appendChild(td2Text);
					td2.className = "button";
					td2.addEventListener("click", function() {
						formFile.enctype = "multipart/form-data";
						formFile["mode"].value = "download";
						formFile["obj"].value = "prescription";
						formFile["docUser"].value = coll[i].doctorUser;
						formFile["patUser"].value = coll[i].patientUser;
						formFile["date"].value = coll[i].timeDatePrescription;
						formFile.submit();
					});
					tr.appendChild(td2);
					table.appendChild(tr);
				}
			} else
				document.formPatients.style.backgroundColor = "red";
		}, error: function(xhr, status, error) {
			document.formPatients.style.backgroundColor = "red";
		}
	});

	sideBar("Ricette", 3);
}

function recordsOnClick() {
	var user = {
		doctorUser: "",
		patientUser: document.formInfoP['user'].value
	}

	var formFile = document.formFile;
	formFile["date"].type = "date";
	formFile["typology"].type = "text";
	formFile["place"].type = "text";
	formFile["result"].type = "text";
	formFile["file"].type = "file";
	document.getElementById("button").className = "secondButton visibleInline"

	let st = JSON.stringify(user);
	$.ajax({
		url: "./paziente", type: "POST", data: { jsonObj: st, mode: "getrecords" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				var records = JSON.parse(result);
				var coll = records.records;
				var length = coll.length;
				var div = document.getElementById("divPatients");
				if (div != undefined)
					div.remove();
				div = document.createElement("div");
				div.id = "divPatients";
				document.formPatients.appendChild(div);

				let labels = ["Rimuovi", "Data", "Tipologia", "Luogo", "Esito", "Cartella"]
				var table = document.createElement("table");
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
					let td1Text = document.createTextNode("❌");
					td1.appendChild(td1Text);
					td1.className = "button";
					td1.addEventListener("click", function() {
						let rRemove = {
							doctorUser: "",
							patientUser: user.patientUser,
							date: coll[i].medicalRecordDate,
							typology: coll[i].typology
						}
						let st = JSON.stringify(rRemove);
						$.ajax({
							url: "./paziente", type: "POST", data: { jsonObj: st, mode: "removerecord" }, timeout: 4000, success: function(result) {
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
					let td2Text = document.createTextNode(coll[i].medicalRecordDate);
					td2.appendChild(td2Text);
					tr.appendChild(td2);

					let td3 = document.createElement("td");
					let td3Text = document.createTextNode(coll[i].typology);
					td3.appendChild(td3Text);
					tr.appendChild(td3);

					let td4 = document.createElement("td");
					let td4Text = document.createTextNode(coll[i].place);
					td4.appendChild(td4Text);
					tr.appendChild(td4);

					let td5 = document.createElement("td");
					let td5Text = document.createTextNode(coll[i].result);
					td5.appendChild(td5Text);
					tr.appendChild(td5);

					let td6 = document.createElement("td");
					let td6Text = document.createTextNode("💾");
					td6.appendChild(td6Text);
					td6.className = "button";
					td6.addEventListener("click", function() {
						formFile["mode"].value = "download";
						formFile["obj"].value = "record";
						formFile["docUser"].value = document.formInfoP['user'].value;
						formFile["patUser"].value = coll[i].patientUser;
						formFile["date"].value = coll[i].medicalRecordDate;
						formFile["typology"].value = coll[i].typology;
						formFile.submit();
						formFile["date"].value = "";
						formFile["typology"].value = "";
					});
					tr.appendChild(td6);
					table.appendChild(tr);

				}
			} else {

			}

		}, error: function(xhr, status, error) {
			document.formPatients.style.backgroundColor = "red";
		}
	});

	sideBar("Cartelle", 3);

	document.formFile.className = "visibleBlock";
}

function sendRecord() {
	var formFile = document.formFile;
	formFile.enctype = "multipart/form-data";
	formFile["mode"].value = "upload";
	formFile["obj"].value = "record";
	formFile["docUser"].value = "";
	formFile["patUser"].value = document.formInfoP['user'].value;;
	formFile.submit();
}

function doctorOnClick() {

	var form = document.formDoctor;
	var obj = {
		doctorUser: "",
		patientUser: document.formInfoP['user'].value
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./dottore", type: "POST", data: { jsonObj: str, mode: "getinfo" }, timeout: 4000, success: function(result) {
			if (result != "ERROR") {
				var doctor = JSON.parse(result);
				if (doctor.info == "OK") {
					form["bookingVisits"].checked = doctor.bookingVisits;
					form["requestPrescriptions"].checked = doctor.requestPrescriptions;
					form["doctorCode"].value = doctor.doctorCode;
					form["doctorUser"].value = doctor.doctorUser;
					if (doctor.accessRecords != undefined)
						form["accessRecords"].checked = doctor.accessRecords;
					else
						form["accessRecords"].checked = false;

					if (doctor.freeSeats != undefined)
						form["freeSeats"].value = doctor.freeSeats;
					else
						form["freeSeats"].value = "";

					if (doctor.entered != undefined)
						form["entered"].value = doctor.entered;
					else
						form["entered"].value = "";
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

					document.getElementById("buttonDoctor").style.backgroundColor = "#7fe1f0";
				} else if (doctor.patient == true);
				else
					document.getElementById("buttonDoctor").style.backgroundColor = "red";
			}
			else
				document.getElementById("buttonDoctor").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonDoctor").style.backgroundColor = "red";
		}
	});

	sideBar("Dottore", 7);
}

function doctorUpdate() {
	var form = document.formDoctor;
	var obj = {
		doctorUser: form["doctorUser"].value,
		patientUser: document.formInfoP['user'].value,
		doctorCode: form["doctorCode"].value,
		accessRecords: form["accessRecords"].checked
	}

	var str = JSON.stringify(obj);

	$.ajax({
		url: "./paziente", type: "POST", data: { jsonObj: str, mode: "updatedoctor" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				doctorOnClick();
				setTimeout(function() {
					document.getElementById("buttonDoctor").style.backgroundColor = "green"
				}, 1000);
			}
			else
				document.getElementById("buttonDoctor").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonDoctor").style.backgroundColor = "red";
		}
	});
}