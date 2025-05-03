package model;

import java.util.ArrayList;
import java.util.List;

public class Chat {
	
	List<Messaggio> messaggi;
	Utente utenteA;
	Utente utenteB;
	//prova
	
	
	public Chat(Utente utenteA, Utente utenteB) {
		this.messaggi=new ArrayList<>();
		this.utenteA = utenteA;
		this.utenteB = utenteB;
	}
	public List<Messaggio> getMessaggi() {
		return messaggi;
	}
	public void setMessaggi(List<Messaggio> messaggi) {
		this.messaggi = messaggi;
	}
	public Utente getUtenteA() {
		return utenteA;
	}
	public void setUtenteA(Utente utenteA) {
		this.utenteA = utenteA;
	}
	public Utente getUtenteB() {
		return utenteB;
	}
	public void setUtenteB(Utente utenteB) {
		this.utenteB = utenteB;
	}
	
	
	
	
}
