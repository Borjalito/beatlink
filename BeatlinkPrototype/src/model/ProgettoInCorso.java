package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import model.Licenza;
public class ProgettoInCorso extends Progetto {
	List<Incarico> incarichi;

	public ProgettoInCorso(String titolo, String descrizione, int bpm, File immagine, Utente creatore, Licenza licenza) {
		super(titolo, descrizione, bpm, immagine, creatore, licenza);
		this.incarichi= new ArrayList<>();
	}

	public List<Incarico> getIncarichi(){
		return incarichi;
	}
}
