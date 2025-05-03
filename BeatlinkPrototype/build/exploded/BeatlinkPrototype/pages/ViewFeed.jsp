<%@page import="model.*"%>
<%@ page session="true"%>
<%@ page import="beans.*" %>

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
    <title> FEED </title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
	
</head>
<body>
<% 

for (String progettoId : database.getAllProgetti().keySet()) {
    Progetto p = database.getProgetto(progettoId);

%>
<div class="standardDiv">
    <h2><%=p.getTitolo() %></h2>
        <%=p.getDescrizione() %>
    <p><b>Autore:</b> <%=p.getCreatore().getUsername() %> </p>
      
        <form action="<%= baseURL %>pages/ViewProgetto.jsp" method="post">
            <input type="hidden" name="idProgetto" value=<%=progettoId %>>
            <input type="submit" value="Vai al progetto">
        </form>
</div>

<%     
}
%>

<a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>
</body>
</html>