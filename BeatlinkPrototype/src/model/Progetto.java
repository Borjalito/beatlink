package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Tag;
public class Progetto {
	
	private String titolo;
	private String descrizione;
	private Integer bpm;
	private File immagine;
	private int numeroMiPiace;
	private List<Tag> tags;
	private Utente creatore;
	private List<Utente> utentiACuiPiace;
	private List<Commento> commenti;
	private List<Traccia> tracce;
	private List<Incarico> incarichi;
	private Licenza licenza;
	
	
	public Progetto(String titolo, String descrizione, Integer bpm, File immagine, Utente creatore, Licenza licenza) {
		this.titolo = titolo;
		this.descrizione = descrizione;
		this.bpm = bpm;
		this.immagine = immagine;
		this.numeroMiPiace = 0;
		this.creatore = creatore;
		this.tags= new ArrayList<>();
		this.utentiACuiPiace =  new ArrayList<>();
		this.commenti =  new ArrayList<>();
		this.tracce =  new ArrayList<>();
		this.incarichi=new ArrayList<>();
		this.licenza=licenza;
	}
	
	public void aggiungiTag(String valore) {
		tags.add(new Tag(valore));
	}
	
	public void miPiace(Utente aCuiPiace) {
		
		if(utentiACuiPiace.contains(aCuiPiace)) {
			utentiACuiPiace.add(aCuiPiace);
			numeroMiPiace++;
		} else {
			utentiACuiPiace.remove(aCuiPiace);
			numeroMiPiace--;
		}
	}
	
	public void aggiungiIncarico(Incarico incarico) {
		incarichi.add(incarico);
	}
	
	public void aggiungiCommento(String contenuto, Utente autore) {
		Commento commento = new Commento(contenuto, autore);
		commenti.add(commento);
	}
	
	public void aggiungiTraccia(Traccia traccia) {
		tracce.add(traccia);
	}
	
	public void rimuoviTraccia(Traccia traccia) {
	    for (Iterator<Traccia> iterator = tracce.iterator(); iterator.hasNext();) {
            Traccia t = iterator.next();
            if (t.equals(traccia)) {
                iterator.remove();
                break; 
            }
        }
	}
	
	public List<Incarico> getIncarichi(){
		return incarichi;
	}
	
	public void setIncarichi(List<Incarico> incarichi) {
		this.incarichi=incarichi;
	}
	
	public Licenza getLicenza() {
		return licenza;
	}
	public String getTitolo() {
		return titolo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Integer getBpm() {
		return bpm;
	}
	public void setBpm(int bpm) {
		this.bpm = bpm;
	}
	public File getImmagine() {
		return immagine;
	}
	public void setImmagine(File immagine) {
		this.immagine = immagine;
	}
	public int getNumeroMiPiace() {
		return numeroMiPiace;
	}
	public void setNumeroMiPiace(int numeroMiPiace) {
		this.numeroMiPiace = numeroMiPiace;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Utente getCreatore() {
		return creatore;
	}
	public List<Utente> getUtentiACuiPiace() {
		return utentiACuiPiace;
	}
	public void setUtentiACuiPiace(List<Utente> utentiMiPiace) {
		this.utentiACuiPiace = utentiMiPiace;
	}
	public List<Commento> getCommenti() {
		return commenti;
	}
	public void setCommenti(List<Commento> commenti) {
		this.commenti = commenti;
	}
	public List<Traccia> getTracce() {
		return tracce;
	}
	public void setTracce(List<Traccia> tracce) {
		this.tracce = tracce;
	}
	
	
	
	
	
	
	
}
