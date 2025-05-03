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
    <title>REGISTRAZIONE NON EFFETTUATA</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
  	<h1>Nome utente errato (non esiste)</h1>

	<br><a href="<%=baseURL %>pages/ViewRegistrazione.jsp">Torna alla pagina di registrazione</a>  

</body>
</html>




