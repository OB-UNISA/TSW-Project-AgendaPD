<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.lang.Boolean"%>
<%
	if (request.getSession().getAttribute("utenteBean") != null && !request.isSecure()) {
	response.sendRedirect(response.encodeRedirectURL("https://localhost:8443/agendapd"));
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="Homepage">
<meta name="author" content="Oleg Bilovus">
<link rel="icon" href="images/icon.png" type="image/png" sizes="48x48">
<link href="CSS/nav.css" type="text/css" rel="stylesheet">
<link href="CSS/home.css" type="text/css" rel="stylesheet">
<link href="CSS/overwrite.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="JS/jquery-3.5.1.js"></script>
<script type="text/javascript" src="JS/functions.js"></script>
<script type="text/javascript" src="JS/home.js"></script>
<title>AgendaPD</title>
<script type="text/javascript">
	$(document).ready(function() {
		resizeImage();
		window.addEventListener("resize", resizeImage);
	});
</script>
</head>
<body>
	<div>
		<%@ include file="nav.jsp"%>
		<article id="articleLeft" class="shadowNav borderNav">
			<p>Agenda PD (paziente, dottore) ha l’obbiettivo principale quello di
				evitare la formazione di lunghe code e affollamento negli studi dei medici
				generali e lo si può fare facendo svolgere le operazioni più comuni online e
				con l’aggiunta di dispositivi IoT. Il sito si rivolge a medici, pazienti e
				segreterie dei medici sul territorio italiano</p>
		</article>
		<%
			String leftAticle = "visibleBlock";
		if (beanNav != null && beanNav.getUser() != null)
			leftAticle = "visibleNone";
		%>
		<div id="leftSideInfo" class="<%=leftAticle%>">
			<article id="articleRight" class="shadowNav borderNav">
				<p>Registrati o accedi per iniziare ad usare il servizio. Se il tuo
					medico dispone già di un profilo e hai il suo codice profilo, potrete
					connettervi immediatamente</p>
			</article>
			<div id="buttons">
				<button class="firstButton" type="button"
					onclick="location.href='<%=response.encodeURL("accesso.jsp")%>'">&#128272;Accedi</button>
				<button class="secondButton" type="button"
					onclick="location.href='<%=response.encodeURL("registrazione.jsp")%>'">&#128221;Registrati</button>
			</div>
		</div>
		<img id="homepage" alt="homepage" src="images/homepage.jpg">
	</div>
</body>
</html>