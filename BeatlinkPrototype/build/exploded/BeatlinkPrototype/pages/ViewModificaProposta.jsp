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
    <title> MODIFICA PROPOSTA </title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
	
</head>
<body>
<% 
String incaricoId;
if(request.getParameter("idProposta")!=null && !request.getParameter("idProposta").isEmpty()){
	String propId=request.getParameter("idProposta");
	request.getSession().setAttribute("currentIdProposta", propId);
	incaricoId = database.getIncaricoIdFromProposta(propId);
	request.getSession().setAttribute("currentIdIncarico", incaricoId);

}
 String currentPropId=(String)request.getSession().getAttribute("currentIdProposta");  
 Proposta currentProposta=database.getProposta(currentPropId);
 
  incaricoId = database.getIncaricoIdFromProposta(currentPropId);
 Incarico i = database.getIncarico(incaricoId);
	
 String progettoId= database.getProgettoIdFromIncarico(incaricoId);
 Progetto progetto = database.getProgetto(progettoId);

%>
    <h2><b>Progetto: </b> <%=progetto.getTitolo() %><br><b>Incarico: </b> <%= i.getNome() %></h2>
	
    <form id="checkboxForm" action="<%=baseURL %>modificaProposta" method="post">
        <% 
        for(String trackId : database.getAllAssociazTracciaProposta().keySet()){
        	if(database.getAllAssociazTracciaProposta().get(trackId).equals(currentPropId)){
        		Traccia t = database.getTraccia(trackId);
        %>
        	
        	<label>
            	<input type="checkbox" name="selectedTracks" value=<%=trackId %>> <%= t.getNome() %>
        	</label>
        	<br>
   	
        	<% 			
        	} }
            %>
       

        <!-- Pulsante per inviare il modulo -->
    <input type="submit" name="action" value="Rimuovi">
    </form>
	
	<br>
	<hr>
	<br>
	
	<form action="<%= baseURL %>/modificaProposta" method="post" enctype="multipart/form-data">
    <label for="audioFiles">Seleziona file audio:</label>
    <input type="file" id="audioFiles" name="audioFiles" accept="audio/*" multiple required>
    <br>
    <input type="submit" name="action" value="Carica">
	</form>
	<br>
	<hr>
	<br>
	<br><br>
	<form action="<%= baseURL %>pages/HomeCandidato.jsp" method="post">
    <br>
    <input type="submit"  value="Home Candidato">
	</form>

	

	<form action="<%= baseURL %>pages/ViewAscoltaProgetto.jsp" method="post">
	<input type="hidden" id="mittente" name="mittente" value="ViewModificaProposta">
		   <input type="submit" value="Ascolta Progetto">
	</form>

 	
<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>

</body>
</html>




