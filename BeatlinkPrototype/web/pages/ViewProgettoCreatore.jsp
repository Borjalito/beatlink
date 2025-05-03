<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="model.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry" %>

<html>
<head>
<% 
    String url = request.getRequestURL().toString(); 
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
%>
	<!--   <meta http-equiv="Refresh" content= "2; URL=paginaPrincipale"/> -->
    <title>PROGETTO CREATORE</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>

<% 
User u = (User)session.getAttribute("user");
%>
   
   <h1>Progetto (vista del creatore)</h1>
   <div class="standardDiv">
   <% 
   
   if(request.getParameter("idProgetto")!=null && !request.getParameter("idProgetto").isEmpty()){
		String id = request.getParameter("idProgetto");
		Progetto p= database.getProgetto(id);
		request.getSession().setAttribute("currentIdProgetto", id);

	}
	
	String idProgetto= (String)request.getSession().getAttribute("currentIdProgetto");
	Progetto progetto = database.getProgetto(idProgetto);

   %>
   <h2><%=progetto.getTitolo() %></h2>
   <p><%=progetto.getDescrizione() %></p>
   <h3>Tracce attuali</h3>
   <%
   int countTracce = 0;
	for(Map.Entry<String, String> entry : database.getAllAssociazTracciaProgetto().entrySet()){
		if(entry.getValue().equals(idProgetto)){
			countTracce++;
			Traccia t = database.getTraccia(entry.getKey());
			%><b><%=t.getNome()%> </b>(Autore: <%=t.getAutore().getUsername()%>)<br>
	<% }} %>
	
	<% if(countTracce==0){ %>
   		ancora nessuna traccia...
   	<%} %>
   
    
    <h3>Elenco degli incarichi</h3>
   <div class="innerDiv">
<%  	
for(String idIncarico : database.getIncarichiProgettoId(idProgetto)){
	Incarico i = database.getIncarico(idIncarico);
%>
	<form action="<%= baseURL %>pages/ViewIncarico.jsp" method="post">
	   <input type="hidden" id="idIncarico" name="idIncarico" value=<%=idIncarico %>>
	   <input type="submit" value="<%=i.getNome() %>">
	</form>
	<p><%=i.getDescrizione()%></p>
	<br>
<%  	
}
%>
	</div> <!-- DIV INCARICHI -->

</div> <!-- DIV PROGETTO -->

<form action="<%= baseURL %>pages/ViewAscoltaProgetto.jsp" method="post">
	   <input type="hidden" id="mittente" name="mittente" value="ViewProgettoCreatore">
	   <input type="submit" value="Ascolta Progetto">
</form>

<br>
<br><a href="<%=baseURL %>pages/HomeCreatore.jsp">Torna alla home creatore</a>

<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>

</body>
</html>




