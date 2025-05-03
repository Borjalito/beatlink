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
    <title>REGISTRAZIONE</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<link type="text/css" href="<%= baseURL%>styles/bigPage.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
   <h1>Registrazione</h1>

  	<form action="<%=request.getContextPath()%>/registrazione" method="post">
  		<label for="userName">Username</label><br>
  		<input type="text" size="20" name="userName"><br>
  		<br>
  		<label for="password">Password</label><br>
  		<input type="password" size="20" name="password"><br>
  		<br><br><br><br>
  		<!--   input type="text" size="7" name="gruppo" hint="insert the group"><br> -->
  		<input type="submit" value="Registrati">
  	</form>	


<br><a href="<%=baseURL %>pages/ViewLogin.jsp">Sei già registrato? Accedi</a>

</body>
</html>