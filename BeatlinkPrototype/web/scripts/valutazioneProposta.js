
function valuta(button) {
	
	//l'id di entrambi i button accetta/scarta corrispondono al codice della traccia
	//anche gli oggetti da nascondere in seguito alla valutazione hanno id traccia come valore di classe
   
    //FA SCHIFO MA è UNA PEZZA. L'id della traccia contiene il .wav e crea problemi nella classe 
    const theId = button.id + "";
    const trackId = theId.slice(0, -4); 
   
    const result = button.name; //accetta/scarta
    const xhr = new XMLHttpRequest();
    
       
    // Configura la richiesta AJAX
    xhr.open("POST", "../valutaProposta", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    // Definisci cosa fare alla risposta della servlet
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
        	hideElements("track"+trackId);
        }
    };

    // Invia la richiesta con i parametri
    //qua devo inviare l'id completo con l'estensione per salvare correttamente lato server
    const params = `idTraccia=${encodeURIComponent(theId)}&result=${encodeURIComponent(result)}`;
    xhr.send(params);
}



function hideElements(classId) {
    // Seleziona tutti gli elementi con la classe 'hideable'
  
    const elements = document.querySelectorAll('.'+classId);
   
    elements.forEach(element => {
        element.style.display = 'none';
    });
}