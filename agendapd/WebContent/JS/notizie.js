function loadNews(user) {
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
				var divNodes = div.childNodes;
				for (let i = 0; i < length; i++) {
					let header = document.createElement("header");
					header.appendChild(document.createTextNode(coll[i].doctorUser + " - " + coll[i].timeDateNews));
					let article = document.createElement("article");
					let p = document.createElement("p");
					p.appendChild(document.createTextNode(coll[i].description));
					article.append(p);
					div.appendChild(header);
					div.appendChild(article);
				}
			}
			else
				document.body.style.backgroundColor = "red";

		}, error: function(xhr, status, error) {
			document.body.style.backgroundColor = "red";
		}
	});
}