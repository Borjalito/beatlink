<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="model.*" %>

<html>
<head>
<% 
    String url = request.getRequestURL().toString(); 
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
%>
	<!--   <meta http-equiv="Refresh" content= "2; URL=paginaPrincipale"/> -->
    <title>INCARICO</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
   <h1>Seleziona la proposta da valutare</h1>

<%  	
String idIncarico = request.getParameter("idIncarico");
session.setAttribute("currentIdIncarico", idIncarico);
%>

<%  	
for(String idProposta : database.getProposteIncaricoId(idIncarico)){
	Proposta p = database.getProposta(idIncarico);
%>
	<a href="<%=baseURL %>pages/ViewValutazioneProposta.jsp?idProposta=<%=idProposta %>">
	Proposta n. <%=idProposta %></a>
	<br>
<%  	
}
%>

<br/>
	<form action="<%= baseURL %>/pages/ViewProgettoCreatore.jsp" method="post">
    <input type="hidden" id="idProgetto" name="idProgetto" value=<%= session.getAttribute("currentIdProgetto") %>>
    <br>
    <input type="submit" value="Torna al Progetto">
	</form>

<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>

</body>
</html>




