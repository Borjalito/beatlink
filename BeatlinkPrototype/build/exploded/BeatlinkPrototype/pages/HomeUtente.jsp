<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.
%>

<% 
    String url = request.getRequestURL().toString(); 
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
%>
	<!--   <meta http-equiv="Refresh" content= "2; URL=paginaPrincipale"/> -->
    <title>HOME UTENTE</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
   <h1>Home utente</h1>

<form action="<%= baseURL %>pages/HomeCreatore.jsp" method="post">
   <input type="submit" value="Home Creatore">
</form>

<form action="<%= baseURL %>pages/HomeCandidato.jsp" method="post">
   <input type="submit" value="Home Candidato">
</form>


<form action="<%= baseURL %>pages/ViewCreazioneProgetto.jsp" method="post">
   <input type="submit" value="Crea Progetto">
</form>

<form action="<%= baseURL %>pages/ViewFeed.jsp" method="post">
   <input type="submit" value="Feed">
</form>

<form action="<%= baseURL %>pages/ViewRicercaProfili.jsp" method="post">
   <input type="submit" value="Ricerca Profili (lavori in corso...)">
</form>

<form action="<%= baseURL %>pages/ViewMioProfilo.jsp" method="post">
   <input type="submit" value="Mio Profilo (lavori in corso...)">
</form>

<form action="<%= baseURL %>pages/ViewRicercaProgetti.jsp" method="post">
   <input type="submit" value="Ricerca Progetti (lavori in corso...)">
</form>


<br><br>
<a href="<%=baseURL %>logout">Logout</a>

</body>
</html>




