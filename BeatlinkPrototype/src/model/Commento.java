package model;

public class Commento {
	private String contenuto;
	private Utente autore;
	
	
	public Commento(String contenuto, Utente autore) {
		this.contenuto = contenuto;
		this.autore = autore;
	}
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public Utente getAutore() {
		return autore;
	}
	public void setAutore(Utente autore) {
		this.autore = autore;
	}
	
	
	
	
}
