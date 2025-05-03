<%@ page session="true"%>
<%@ page import="beans.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>

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
    <title>ANALIZZA LOG</title>
	<link type="text/css" href="<%=request.getContextPath() %>/styles/default.css" rel="stylesheet"></link>
	<link type="text/css" href="<%=request.getContextPath() %>/styles/admin.css" rel="stylesheet"></link>
	<jsp:useBean id="database" class="beans.Database" scope="application"/>
</head>
<body>
   
   <h1>Pagina riservata all'admin: analisi dei log</h1>

	
	<table>
        <thead>
            <tr>
                <th>Data e Ora</th>
                <th>Contenuto</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>2024-07-14 10:15:30</td>
                <td>Lettura da file</td>
            </tr>
            <tr>
                <td>2024-07-14 10:16:45</td>
                <td>Connessione al database</td>
            </tr>
            <tr>
                <td>2024-07-14 10:17:50</td>
                <td>Scrittura su file</td>
            </tr>
            <tr>
                <td>2024-07-14 10:19:05</td>
                <td>Chiusura connessione al database</td>
            </tr>
            <tr>
                <td>2024-07-14 10:20:10</td>
                <td>Avvio processo di backup</td>
            </tr>
            <tr>
                <td>2024-07-14 10:21:20</td>
                <td>Completamento processo di backup</td>
            </tr>
            <tr>
                <td>2024-07-14 10:22:35</td>
                <td>Invio email di notifica</td>
            </tr>
            <tr>
                <td>2024-07-14 10:23:40</td>
                <td>Ricezione dati da API</td>
            </tr>
            <tr>
                <td>2024-07-14 10:25:55</td>
                <td>Aggiornamento record nel database</td>
            </tr>
            <tr>
                <td>2024-07-14 10:27:00</td>
                <td>Eliminazione record temporanei</td>
            </tr>
        </tbody>
    </table>
	

	<br><a href="<%=baseURL %>pages/ViewRegistrazione.jsp">Torna alla registrazione</a>
	<a href="<%=baseURL %>logout">Logout</a>

</body>
</html>




