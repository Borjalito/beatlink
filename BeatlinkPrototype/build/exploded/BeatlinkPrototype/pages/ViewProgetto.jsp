<%@page import="model.Incarico"%>
<%@page import="java.util.*"%>

<%@page import="model.Commento"%>
<%@page import="model.Progetto"%>
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
    <title>VISUALIZZAZIONE PROGETTO</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<link type="text/css" href="<%= baseURL%>styles/progetto.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>

<% 
User u = (User)session.getAttribute("user");
%>

<% 

if(request.getParameter("idProgetto")!=null && !request.getParameter("idProgetto").isEmpty()){
	String id = request.getParameter("idProgetto");
	Progetto p= database.getProgetto(id);
	request.getSession().setAttribute("currentIdProgetto", id);

}

String projectId= (String)request.getSession().getAttribute("currentIdProgetto");
Progetto progetto= database.getProgetto(projectId);

%>

<div class="standardDiv">

   <h1><%=progetto.getTitolo()%></h1>
	<p>Creato da <%=progetto.getCreatore().getUsername() %></p>
	<p>Licenza: <%=progetto.getLicenza()%></p><br>
	<h3>Descrizione</h3>
	<div class="innerDiv">
	<p><%=progetto.getDescrizione() %></p>
	</div>
	<br>
	<% if(progetto.getBpm() != null) { %>
	<p>BPM: <%=progetto.getBpm() %></p>
	<% } %>
	
	
	<%  String imageName = "";
		if(progetto.getImmagine()!=null && progetto.getImmagine().getName()!=null && !progetto.getImmagine().getName().isBlank()) {
			imageName = progetto.getImmagine().getName(); 
			System.out.println("View progetto: "+imageName);
    %>
	<img alt="unavailable img" width="200" height="200"  src="http://localhost:8080/Beatlink/immagini/<%= imageName %>">
	
	<br>
	<%	} %>
	
	<h2> Incarichi </h2>
<div class="innerDiv">
	<% 
	Map<String,String> associazIncProg =(Map<String,String>)database.getAllAssociazIncaricoProgetto();
	List<String> idIncarichi=new ArrayList<>();
	for(String id: associazIncProg.keySet()){
		if(associazIncProg.get(id).equals(projectId)){
			idIncarichi.add(id);
		}
	}
	for(String idIncarico : idIncarichi) { 
    	Incarico i= database.getIncarico(idIncarico); %>
    <h3><%= i.getNome() %></h3>
    <p><%= i.getDescrizione() %></p>
    <form action="<%= baseURL %>pages/ViewEffettuaProposta.jsp" method="post">
        <input type="hidden" name="idIncarico" value=<%= idIncarico %>>
        
        <!-- visualizzo tasto per effettuare proposta se l'utente non è creatore -->
        <% if(!progetto.getCreatore().getUsername().equals(u.getUsername()) ){ %>      	
       		 <input type="submit" value="Effettua proposta per <%= i.getNome() %>">
        <%} %>
    </form>
	<% }//chiusura for %>
</div> <!-- DIV INCARICHI -->
	
<div class="innerDiv">
	<h3> Commenti:</h3>
	<br>
	<%
	for(Commento c : progetto.getCommenti()){
		%>
		
		<p><b><%=c.getAutore().getUsername() %></b> : <%= c.getContenuto() %></p>
		
		<% 
	}
	%>
</div> <!-- DIV COMMENTI -->

</div> <!-- DIV PROGETTO -->

<form action="<%= baseURL %>pages/ViewAscoltaProgetto.jsp" method="post">
<input type="hidden" id="mittente" name="mittente" value="ViewProgetto">
	   <input type="submit" value="Ascolta Progetto">
</form>

<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>
 

</body>
</html>