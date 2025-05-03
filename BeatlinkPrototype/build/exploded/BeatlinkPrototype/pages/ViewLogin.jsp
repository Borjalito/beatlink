<%@ page session="true"%>
<%@ page import="beans.*" %>

<html>
<head>
<% 
    String url = request.getRequestURL().toString(); 
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
%>
	<!--   <meta http-equiv="Refresh" content= "2; URL=paginaPrincipale"/> -->
    <title>LOGIN</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<link type="text/css" href="<%= baseURL%>styles/bigPage.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
   <h1>Login</h1>


  	<form action="<%=request.getContextPath()%>/login" method="post">
  		<label for="userName">Username</label><br>
  		<input type="text" size="20" name="userName" id="userName" ><br>
  		<br>
  		<label for="password">Password</label><br>
  		<input type="password" size="20" name="password" id="password"><br>
  		<!--  <input type="text" size="7" name="gruppo" hint="insert the group"><br> -->
  		<br><br><br><br>
  		<input type="submit" value="Login">
  	</form>

<br><a href="<%=baseURL %>pages/ViewRegistrazione.jsp">Torna alla registrazione</a>

</body>
</html>




