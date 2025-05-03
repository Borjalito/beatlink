<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="model.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>


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
    <title>HomeCandidato</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
<h1>HOME CANDIDATO</h1>
<% 
User u = (User)session.getAttribute("user");
%>


<h2>Seleziona una proposta da modificare</h2><br/>
<%
	
for(String idProposta : database.getAllAssociazPropostaAutore().keySet()){
	if(database.getUsernameFromProposta(idProposta).equals(u.getUsername())){
		Proposta p = database.getProposta(idProposta);
		
		String incaricoId = database.getIncaricoIdFromProposta(idProposta);
		Incarico i = database.getIncarico(incaricoId);
		
		String progettoId= database.getProgettoIdFromIncarico(incaricoId);
		Progetto progetto = database.getProgetto(progettoId);
	
	%> 
	<div class="standardDiv">
	      <h4>Progetto <%=progetto.getTitolo() %>, Incarico <%= i.getNome() %></h4>
	     
	     <form action="<%= baseURL %>pages/ViewModificaProposta.jsp" method="post">
            <input type="hidden" name="idProposta" value=<%=idProposta %>>
            <input type="submit" value="Seleziona">
         </form>
	</div>
	
<% 	
}}
%>

<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>
</body>
</html>




