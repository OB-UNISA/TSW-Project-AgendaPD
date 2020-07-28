<%@page import="agendapd.model.UtenteBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	UtenteBean beanNav = (UtenteBean) request.getSession().getAttribute("utenteBean");
String url = request.getRequestURI();
List<String> classes = new ArrayList<>();
classes.add("selected");
classes.add("notSelected");
String home, notizie, iot, profilo;
if (url.contains("home") || url.equals("/agendapd/")) {
	home = classes.get(0);
	notizie = classes.get(1);
	iot = notizie;
	profilo = iot;
} else if (url.contains("notizie")) {
	home = classes.get(1);
	notizie = classes.get(0);
	iot = home;
	profilo = iot;
} else if (url.contains("iotdoc")) {
	home = classes.get(1);
	notizie = home;
	iot = classes.get(0);
	profilo = notizie;
} else if (url.contains("admin") || url.contains("paziente") || url.contains("segretaria") || url.contains("dottore")) {
	home = classes.get(1);
	notizie = home;
	iot = notizie;
	profilo = classes.get(0);
} else {
	home = classes.get(1);
	notizie = home;
	iot = notizie;
	profilo = iot;
}

String profiloDiv;
if (beanNav != null && beanNav.getUser() != null)
	profiloDiv = "visibleInLine";
else
	profiloDiv = "visibleNone";
%>
<%!private String roleString(int role) {
		switch (role) {
		case -1:
			return "admin";
		case 1:
			return "paziente";
		case 2:
			return "segretaria";
		case 3:
			return "dottore";
		default:
			return "#";
		}
	}%>
<nav class="backGroundGradNav shadowNav borderNav"
	onmouseover="$('#logo').css('color', 'rgba(255, 255, 255, 0.7)')"
	onmouseleave="$('#logo').css('color', 'rgba(255, 255, 255, 0.1)')">
	<ul>
		<li class="<%=home%>"><a class="linkNav"
			href="<%=response.encodeURL("home.jsp")%>">&#127968;<span>Home</span></a></li>
		<li class="<%=notizie%>"><a class="linkNav"
			href="<%=response.encodeURL("notizie.jsp")%>">&#128240;<span>Notizie</span></a></li>
		<li id="iotDoc" class="<%=iot%>"><a class="linkNav"
			href="<%=response.encodeURL("iotdoc.jsp")%>">&#128196;<span>IoT
					Doc</span></a></li>
		<li id="profileName" class="<%=profiloDiv + " " + profilo%>"><a
			class="linkNav" href="#" onmouseover="$('#listProfiles').toggle('fast')"
			onclick="$('#listProfiles').toggle('fast')">&#128100;<span>Profilo</span></a>
		<li>
			<form name="formNav" action="<%=response.encodeURL("accmng")%>" method="post">
				<input type="hidden" name="accountName"> <input type="hidden"
					name="accountOP">
			</form>
			<table id="listProfiles" onmouseleave="$('#listProfiles').toggle('fast')">
				<%
					if (beanNav != null && beanNav.getUser() != null) {
				%>
				<tr>
					<td><button type="button" class="secondButton"
							onclick="location.href='./<%=response.encodeURL(roleString(beanNav.getRole()) + ".jsp")%>'"><%=beanNav.getUser()%><br>
							<span>(<%=roleString(beanNav.getRole())%>)
							</span>
						</button></td>
					<td><button type="button" class="firstButton ex"
							onclick="accountManage('<%=beanNav.getUser()%>', 'remove')">&#215;</button></td>
				</tr>
				<%
					}
				List<UtenteBean> accounts = (List<UtenteBean>) request.getSession().getAttribute("accounts");
				if (accounts != null) {
				for (UtenteBean utente : accounts) {
					if (!utente.equals(beanNav)) {
				%>
				<tr>
					<td><button type="button" class="firstButton"
							onclick="accountManage('<%=utente.getUser()%>', 'switch')"><%=utente.getUser()%><br>
							<span>(<%=roleString(utente.getRole())%>)
							</span>
						</button></td>
					<td><button type="button" class="secondButton ex"
							onclick="accountManage('<%=utente.getUser()%>', 'remove')">&#215;</button></td>
				</tr>
				<%
					}
				}
				}
				%>
				<tr>
					<td><button type="button" class="secondButton"
							onclick="location.href='<%=response.encodeURL("accesso.jsp")%>'">
							+&nbsp;Aggiungi&nbsp;profilo</button></td>
				</tr>
			</table>
		</li>
	</ul>
	<span id="logo">AGENDA PD</span>
</nav>
<br>
<br>