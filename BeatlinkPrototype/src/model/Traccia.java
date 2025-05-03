package model;

import java.io.File;

public class Traccia {
	
	private String nome;
	private File traccia;
	private Utente autore;
	
	public Traccia(String nome, File traccia, Utente autore) {
		this.nome = nome;
		this.traccia = traccia;
		this.autore = autore;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public File getTraccia() {
		return traccia;
	}
	public void setTraccia(File traccia) {
		this.traccia = traccia;
	}
	public Utente getAutore() {
		return autore;
	}
	public void setAutore(Utente autore) {
		this.autore = autore;
	}
	
	
	
}
