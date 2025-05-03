<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="model.*" %>

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
    <title>CREAZIONE PROGETTO</title>
	<link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
	
    <script type="text/javascript" src="../scripts/creazioneProgetto.js"></script>
</head>
<body>
   
  


 <h1>Crea un progetto</h1>
 <p>I campi contrassegnati con asterisco (*) sono parametri obbligatori</p>
 

  	<form action="<%=request.getContextPath()%>/creaProgetto" method="post" enctype="multipart/form-data">
  		<h2>Titolo *</h2>
  		<input type="text" size="20" name="titolo" id="titolo" value="default" required><br><br>
  		
  		
  		<h2>Descrizione *</h2>
  		<textarea class="custom-textarea" id="descrizione" name="descrizione"  required>default</textarea><br><br>
  		
  		<h2>Licenza *</h2>
  		<fieldset>
		  <% for(Licenza lic : Licenza.values()){ %>	  
		    <input type="radio" id="<%= lic %>" name="licenza" value="<%= lic %>" checked="checked"/>
		    <label for="<%= lic %>"><%= lic %></label>
		  <% } %>
		</fieldset><br><br>
		
		<h2>Incarichi * (almeno 1) </h2>
		<input type=button id="createInputButton" value="Aggiungi un incarico" onClick="generaCaselleIncarico()"><br>
		<br>
		<!--  ce ne è uno di base per indicarne, in qualche modo, l'obbligatorietà -->
		<label>Titolo 1:</label>
		<input type="text" id="titoloIncarico1" name="titoloIncarico1" value="default" required>
		<label>Descrizione 1:</label>
		<input type="text" id="descrizioneIncarico1" name="descrizioneIncarico1" value="default" required>
		<br>
		<div id="containerIncarichi" ></div>
		
		<h2>Tag</h2>
		<label for="tag">Inserisci i tag separati da virgole</label><br>
		<textarea id="tag" name="tag"/></textarea><br><br>
		
		<h2>Immagine</h2>
  		<label for="immagine">Immagine (png, jpeg)</label>
		<input type="file" id="immagine" name="immagine" accept="image/png, image/jpeg"/><br><br>

		<h2>Tracce iniziali</h2>
		<label for="audioFiles">Tracce iniziali  </label>
   		<input type="file" id="audioFiles" name="audioFiles" accept="audio/*" multiple><br><br>
		
		<h2>BPM</h2>
		<label for="bpm">BPM</label>
  		<input type="number" size="20" name="bpm" id="bpm" min="5" max="200"><br><br>
	
		
		
  		<br><br><input type="submit" value="Crea">
  	</form>


<br></br>



<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>
</body>
</html>




