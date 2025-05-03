let counter= 2;

function generaCaselleIncarico() {
   
    const labelTitolo = document.createElement('label');
    labelTitolo.setAttribute('for', 'titoloIncarico' + counter);
    labelTitolo.textContent = 'Titolo ' + counter + ':';

    const inputTitolo = document.createElement('input');
    inputTitolo.setAttribute('type', 'text');
    inputTitolo.setAttribute('id', 'titoloIncarico' + counter);
    inputTitolo.setAttribute('name', 'titoloIncarico' + counter);
    
    const labelDescrizione = document.createElement('label');
    labelDescrizione.setAttribute('for', 'descrizioneIncarico' + counter);
    labelDescrizione.textContent = 'Descrizione ' + counter + ':';

    const inputDescrizione = document.createElement('input');
    inputDescrizione.setAttribute('type', 'text');
    inputDescrizione.setAttribute('id', 'descrizioneIncarico' + counter);
    inputDescrizione.setAttribute('name', 'descrizioneIncarico' + counter);

    // Aggiungi la label e l'input al contenitore
    const container = document.getElementById('containerIncarichi');
    container.appendChild(labelTitolo);
    container.appendChild(inputTitolo);
    container.appendChild(labelDescrizione);
    container.appendChild(inputDescrizione);

    // Aggiungi una linea di separazione tra gli input creati
    const br = document.createElement('br');
    container.appendChild(br);
     
    counter++;
}
