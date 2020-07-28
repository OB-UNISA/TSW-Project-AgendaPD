function newsOnClickAdmin() {
	var obj = {
		user: "admin",
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
				let span = document.createElement("span");
				span.id = "spanSpace";
				div.append(span);
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

	sideBar("Communicazioni", 3);
}

function addNewsAdmin() {

	var obj = {
		user: "admin",
		text: document.formNews["textNews"].value,
		time: new Date().toISOString().split("Z")[0]
	};

	$.ajax({
		url: "./news", type: "POST", data: { news: JSON.stringify(obj), mode: "addnews" }, timeout: 4000, success: function(result) {
			if (result != "ERROR" && JSON.parse(result).info == "OK") {
				let div = document.getElementById("divNews");
				let header = document.createElement("header");
				let button = document.createElement("span");
				button.appendChild(document.createTextNode("× "));
				header.appendChild(button);
				let time = obj.time.split("T");
				let date = new Date();
				header.appendChild(document.createTextNode(obj.user + " - " + time[0] + " " + date.getHours() + ":" +
					date.getMinutes() + ":" + date.getSeconds() + ".0 "));
				let article = document.createElement("article");
				let p = document.createElement("p");
				p.appendChild(document.createTextNode(obj.text));
				article.append(p);
				div.insertBefore(article, div.childNodes[0]);
				div.insertBefore(header, div.childNodes[0]);
				button.onclick = function() {
					let time = obj.time.split("T");
					let newsToRemove = {
						user: obj.user,
						time: time[0] + " " + time[1].split("Z")[0]
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

				document.getElementById("buttonNews").style.backgroundColor = "green";
			}
			else
				document.getElementById("buttonNews").style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.getElementById("buttonNews").style.backgroundColor = "red";
		}
	});
}

function ping() {
	var time = new Date().getTime();
	var ping = document.formStats["ping"];
	$.ajax({
		url: "./ping", type: "GET", timeout: 4000, success: function(result) {
			ping.value = new Date().getTime() - time;
			let value = ping.value;
			if (value < 60)
				ping.style.color = "green";
			else if (value > 60 && value < 150)
				ping.style.color = "yellow";
			else if (value > 150 && value < 500)
				ping.style.color = "orange";
			else
				ping.style.color = "red";
		}, error: function(xhr, status, error) {
			ping.style.color = "red";
			ping.value = "4000+";
		}
	});
}
