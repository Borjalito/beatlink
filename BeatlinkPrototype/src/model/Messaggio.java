package model;

import java.time.LocalDateTime;

public class Messaggio {
	
	private String contenuto;
	private LocalDateTime dataOra;
	private Utente mittente;
	
	public Messaggio(String contenuto, LocalDateTime dataOra, Utente mittente) {
		this.contenuto = contenuto;
		this.dataOra = dataOra;
		this.mittente = mittente;
	}
	
	
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	public LocalDateTime getDataOra() {
		return dataOra;
	}
	public void setDataOra(LocalDateTime dataOra) {
		this.dataOra = dataOra;
	}
	public Utente getMittente() {
		return mittente;
	}
	public void setMittente(Utente mittente) {
		this.mittente = mittente;
	}
	
	
	
}
