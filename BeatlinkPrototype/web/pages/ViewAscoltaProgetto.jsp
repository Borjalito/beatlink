<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.*"%>
<%@ page session="true"%>
<%@ page import="beans.*" %>

<html>
<head>
<%
//da fare ;fare pause e stop; collgare i pulsanti mute e solo; aggiungere volume; forma d'onda ; barra del tempo;
// miglioramneti : riproduzione; solo;

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // Proxies.
%>


<% 
    
	String url = request.getRequestURL().toString(); 
    String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
%>
	<!--   <meta http-equiv="Refresh" content= "2; URL=paginaPrincipale"/> -->
    <title> Ascolta Progetto </title>
    <link type="text/css" href="<%= baseURL%>styles/default.css" rel="stylesheet"></link>
	<link type="text/css" href="<%= baseURL%>styles/ascolta.css" rel="stylesheet"></link>
	<script src="https://unpkg.com/tone"></script>
	<script src="https://unpkg.com/wavesurfer.js@7"></script>
	
	
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>

   <span>
  <button id="playButton"> Play </button>
  <button id="pauseButton"> Pause </button>
  <button id="stopButton"> Stop </button>
  </span>
  <br><br><br><br>
<input type="range" id="progressBar" min="0" max="100" value="0"><br>


<br><br><br><br><h1> Tracce attuali del progetto</h1><br><br><br><br>
<% 
String projectId= (String)session.getAttribute("currentIdProgetto");
Progetto progetto = database.getProgetto(projectId);
User u = (User)session.getAttribute("user");
String username= u.getUsername();

String urlBaseTrack= baseURL+"tracce/";

ArrayList<String> TracceDaAscoltare=new ArrayList<>();
int trackIndex = 0;


for(String tracciaId: database.getAllAssociazTracciaProgetto().keySet()){
	if(database.getAllAssociazTracciaProgetto().get(tracciaId).equals(projectId)){
		TracceDaAscoltare.add(urlBaseTrack+tracciaId);
		Traccia t = database.getTraccia(tracciaId);
	%>
	
	<p><b>Nome: </b><%= t.getNome() %>,<b>Autore: </b><%= t.getAutore().getUsername() %> </p> 
	<button class="muteButton" id="muteButton<%= trackIndex %>">Mute</button>
	<button class="soloButton" id="soloButton<%= trackIndex %>">Solo</button>
	 <input type="range" class="volumeBar" id="volumeSlider<%= trackIndex %>" min="0" max="200" value="100">
	 <div id="waveform<%= trackIndex %>"></div>
	<br>
	<hr>
	<br> 
		
	<% 
	trackIndex++;
	}
	
}
//tracce attuali
%>




<% 
int trackIndexNow=trackIndex;
if(username.equals(progetto.getCreatore().getUsername())){
	
%>
<br><h1> Tracce degli incarichi da valutare</h1>
<% 
	// tutte proposte
	for(String incaricoId: database.getAllAssociazIncaricoProgetto().keySet()){
		Incarico inc=database.getIncarico(incaricoId);
	
		if(database.getProgettoIdFromIncarico(incaricoId).equals(projectId)){
			%>
			<h2> Incarico : <%= inc.getNome() %></h2>
			<% 
			for(String propId : database.getAllAssociazPropostaIncarico().keySet()){
				if(database.getIncaricoIdFromProposta(propId).equals(incaricoId)){
					String usernameAutoreProposta=database.getAllAssociazPropostaAutore().get(propId);
					%>
			<h3> Proposta di  : <%= usernameAutoreProposta %></h3>
					
					
					<% 
						for(String tracciaId : database.getAllAssociazTracciaProposta().keySet()){
								if(database.getPropostaIdFromTraccia(tracciaId).equals(propId)){
									TracceDaAscoltare.add(urlBaseTrack+tracciaId);

									Traccia t = database.getTraccia(tracciaId);
		%>
			<p><b>Nome: </b><%= t.getNome() %>,<b>Autore: </b><%= t.getAutore().getUsername() %> </p>	    
			<button class="muteButton" id="muteButton<%= trackIndex %>">Mute</button>
			<button class="soloButton" id="soloButton<%= trackIndex %>">Solo</button>
			<input type="range" class="volumeBar"  id="volumeSlider<%= trackIndex %>" min="0" max="200" value="100"><br>
			<div id="waveform<%= trackIndex %>"></div>
			
			<br>
			<hr>
			<br> 
							<% 	trackIndex++;
								}
						}
				}
			}		
		}
	}
	
	if(trackIndexNow==trackIndex){ %>
		<p>Nessun traccia al momento disponibile</p>
		
	<% }
		
}else{//qualsiasi utente che non sia il creatore 
	
	//di fatto solo i candidati potrebbero avere delle proposte in attesa
	//si potrebbe migliorare mettendo un controllo se sono presenti tracce e non visualizzare proprio la scritta

	%>
			<hr>
			<h1> Tracce delle mie proposte in attesa</h1>
	
	
	<% 
	trackIndexNow = trackIndex;
	for(String incaricoId: database.getAllAssociazIncaricoProgetto().keySet()){
		Incarico inc=database.getIncarico(incaricoId);
	
		if(database.getProgettoIdFromIncarico(incaricoId).equals(projectId)){
			%>
			<h2> Incarico : <%= inc.getNome() %></h2>
			<% 
			
			for(String propId : database.getAllAssociazPropostaIncarico().keySet()){
				if(database.getIncaricoIdFromProposta(propId).equals(incaricoId)){
					String usernameAutoreProposta=database.getAllAssociazPropostaAutore().get(propId);
						if(usernameAutoreProposta.equals(username)){
							for(String tracciaId : database.getAllAssociazTracciaProposta().keySet()){
								if(database.getPropostaIdFromTraccia(tracciaId).equals(propId)){
									TracceDaAscoltare.add(urlBaseTrack+tracciaId);

									Traccia t = database.getTraccia(tracciaId);
		%>
			<p><b>Nome: </b><%= t.getNome() %>,<b>Autore: </b><%= t.getAutore().getUsername() %> </p>	     
			<button class="muteButton" id="muteButton<%= trackIndex %>">Mute</button>
			<button class="soloButton" id="soloButton<%= trackIndex %>">Solo</button>
			<input type="range" class="volumeBar" id="volumeSlider<%= trackIndex %>" min="0" max="200" value="100">
			<div id="waveform<%= trackIndex %>"></div>
			<br>
			<hr>
			<br> 

		
		<% 					trackIndex++;
							}
						}
				
					}	
				}
			}		
		}
	}
	
	if(trackIndexNow==trackIndex){ %>
			<p>Nessun traccia al momento disponibile</p>
			
  <%}%>
		
<% } //else candidato %>	
	
	
	
<%

if(TracceDaAscoltare.size()>=1){
String [] tracceAscoltoDefinitive=TracceDaAscoltare.toArray(new String[0]);
Gson g = new Gson();
String tracceAscoltoGson = g.toJson(tracceAscoltoDefinitive);
 
%>

	
	<div id="audioFiles" data-files='<%=tracceAscoltoGson%>'></div>
	
<% 
}

%>
	<br><br><br><br>
	<form action="<%= baseURL %>pages/<%=request.getParameter("mittente")%>.jsp" method="post">
    <br>
    <input type="submit"  value="Torna al progetto">
	</form>


<br>
<br><a href="<%=baseURL %>pages/HomeUtente.jsp">Torna alla home</a>

<script type="text/javascript" src="../scripts/ascoltaProgetto.js"></script>

</body>
</html>




