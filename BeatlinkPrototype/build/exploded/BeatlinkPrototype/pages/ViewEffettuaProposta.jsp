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
    <title>EFFETTUA PROPOSTA</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
	
</head>
<body>
<% 

if(request.getParameter("idIncarico")!=null && !request.getParameter("idIncarico").isEmpty()){
	String incaricoId=request.getParameter("idIncarico");
	Incarico currentInc= database.getIncarico(incaricoId);
	session.setAttribute("currentIdIncarico", incaricoId);
}
 String idInc=(String)session.getAttribute("currentIdIncarico");  
 Incarico currentInc=database.getIncarico(idInc);

  
%>
   <h1>Incarico <%= currentInc.getNome()%> del progetto <%= database.getProgetto((String)session.getAttribute("currentIdProgetto")).getTitolo() %></h1><br/>
   <div class="standardDiv">
   <p><%=currentInc.getDescrizione() %></p>
   </div>
	
	<% 
	User u =(User) request.getSession().getAttribute("user");
	
	boolean trovato=false;
	for(String propId:database.getAllAssociazPropostaIncarico().keySet()){
		if(database.getIncaricoIdFromProposta(propId).equals(idInc) && database.getUsernameFromProposta(propId).equals(u.getUsername())){
			trovato =true;
		}
	}
	if(!trovato){
	%>
	<div class="standardDiv">
	<form action="<%= baseURL %>effettuaProposta" method="post" enctype="multipart/form-data">
    <input type="file" id="audioFiles" name="audioFiles" accept="audio/*" multiple required>
    <br><br>
    <input type="submit" value="Invia la proposta">
	</form>
	</div>
	<% 
			}
	else{
		%>
		
		<b> Proposta Effettuata !</b>
		<% 
		
	}
	%>
	<% 
	 Progetto progetto= (Progetto)request.getSession().getAttribute("currentProgetto"); 
	String projectId= (String)request.getSession().getAttribute("currentIdProgetto");
	
	%>
	<br/>
	<form action="<%= baseURL %>pages/ViewProgetto.jsp" method="post">
    <input type="hidden" id="idProgetto" name="idProgetto" value=<%= projectId %>>
    <br>
    <input type="submit" value="Torna al Progetto">
	</form>

	<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>

</body>
</html>




