package model;

import java.util.ArrayList;
import java.util.List;

public class Utente {
	 
	private String username;
	private Profilo profilo;
	private List<Chat> chat;
	private List<Utente> seguiti;
	private List<Utente> seguaci;
	private int numeroSeguaci;
	//progetto, commento, tracce non li inserisco perché la navigabilità in progettazione non c'è
	//in analisi non è specificata...
	
	
	public Utente(String username, Profilo profilo) {
		this.username = username;
		this.profilo = profilo;
		this.chat = new ArrayList<>();
		this.seguiti =  new ArrayList<>();
		this.seguaci =  new ArrayList<>();
		numeroSeguaci=0;
	}
	

	public void seguiUtente(Utente daSeguire) {
		seguiti.add(daSeguire);
		daSeguire.getSeguaci().add(this);
		daSeguire.setNumeroSeguaci(daSeguire.getNumeroSeguaci()+1);
	}
	
	public int getNumeroSeguaci() {
		return numeroSeguaci;
	}
	
	public void setNumeroSeguaci(int numeroSeguaci) {
		this.numeroSeguaci=numeroSeguaci;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Profilo getProfilo() {
		return profilo;
	}
	public void setProfilo(Profilo profilo) {
		this.profilo = profilo;
	}
	public List<Chat> getChat() {
		return chat;
	}
	public void setChat(List<Chat> chat) {
		this.chat = chat;
	}
	public List<Utente> getSeguiti() {
		return seguiti;
	}
	public void setSeguiti(List<Utente> seguiti) {
		this.seguiti = seguiti;
	}
	public List<Utente> getSeguaci() {
		return seguaci;
	}
	public void setSeguaci(List<Utente> seguaci) {
		this.seguaci = seguaci;
	}
	
	 
	 
}
