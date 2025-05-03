<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="model.*" %>

<html>
<head>
<script type="text/javascript" src="../scripts/valutazioneProposta.js"></script>
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
    <title>VALUTAZIONE PROPOSTA</title>
	<link type="text/css" href="../styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
	
	<style>
        .inline-button {
            display: inline-block;
            margin: 5px; /* Aggiungi margine se desideri spaziatura tra i pulsanti */
        }
    </style>
</head>
<body>
   
   <h1>Valuta le tracce</h1>
  
<% 
String idProposta = request.getParameter("idProposta");
session.setAttribute("currentIdProposta", idProposta);
%>
 
<%  	
for(String idTraccia : database.getTraccePropostaId(idProposta)){
	Traccia t = database.getTraccia(idTraccia);
%>
 
<label  class="track<%=idTraccia%>"><%=t.getNome()%>(autore: <%=t.getAutore().getUsername()%>)</label>
<span>
<button id="<%=idTraccia%>"  class="track<%=idTraccia.substring(0, idTraccia.length() - 4)%>" name="accetta" onclick="valuta(this)">Accetta</button>
<button id="<%=idTraccia%>"  class="track<%=idTraccia.substring(0, idTraccia.length() - 4)%>" name="scarta" onclick="valuta(this)">Scarta</button>
</span>		

<%  	
}
%>

<form action="<%= baseURL %>pages/ViewIncarico.jsp" method="post">
    <input type="hidden" id="idIncarico" name="idIncarico" value=<%= session.getAttribute("currentIdIncarico") %>>
    <br>
    <input type="submit" value="Torna all'incarico">
	</form>


<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>

</body>
</html>




