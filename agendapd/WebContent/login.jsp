<%@page import="agendapd.model.UtenteBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String errorClassAccess = "error";
String user = "", pass = "";
List<String> errorsFoundAccess = (List<String>) request.getAttribute("errorsFound");
UtenteBean beanAccess = (UtenteBean) request.getAttribute("utenteBean");
if (errorsFoundAccess != null) {
	if (errorsFoundAccess.contains("user"))
		user = errorClassAccess;
	if (errorsFoundAccess.contains("pass"))
		pass = errorClassAccess;
}
%>
<div id="userPass">
	<label class="<%=user%>">Nome Utente&nbsp;<input type="text"
		<%=(beanAccess == null || beanAccess.getUser() == null) ? ""
		: String.format("value=\"%s\"", beanAccess.getUser())%>
		name="user" placeholder="NomeUtente" required="required"
		onkeyup="validateForm('formReg', 'user', /[A-z]|[0-9]|\?|,|\-|\./, 'infoUser')"></label>
	<p id="infoUser">sono ammessi solo lettere, numeri e i seguenti simboli: ?
		, . -</p>
	<br> <br> <label class="<%=pass%>">Password&nbsp;<input
		type="password"
		<%=(beanAccess == null || beanAccess.getPass() == null) ? ""
		: String.format("value=\"%s\"", beanAccess.getPass())%>
		name="pass" placeholder="Password123" required="required"
		onblur="passwordValidator()">
	</label>
	<p id="infoPass">inserire almeno una lettera maiuscola e un numero</p>
</div>