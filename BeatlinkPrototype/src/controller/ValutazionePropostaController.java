package controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import beans.*;
import model.Progetto;
import model.Proposta;
import model.Traccia;


public class ValutazionePropostaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Database database;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String idTraccia = request.getParameter("idTraccia");	
		String idProposta = (String)request.getSession().getAttribute("currentIdProposta");
		String idIncarico = (String)request.getSession().getAttribute("currentIdIncarico");
		String idProgetto = (String)request.getSession().getAttribute("currentIdProgetto");
		
		boolean res = request.getParameter("result").equals("accetta") ? true : false;
		if(res) {
			//potrei accorparli ma sto mantenendo tutto separato per evitare ripercussioni
			database.associaTracciaProgetto(idTraccia, idProgetto);
			database.addTracciaAProgetto(idTraccia, idProgetto);
		}
		
		database.dissociaTracciaProposta(idTraccia);
		int tracceRimaste = database.removeTracciaDaProposta(idProposta, idTraccia);
		if(tracceRimaste==0) {
			database.removePropostaDaIncarico(idProposta, idIncarico);
			database.dissociaPropostaIncarico(idProposta);
			database.removeProposta(idProposta);
			database.dissociaPropostaAutore(idProposta);
		}
		
		
		
		
		
		/*

		
		//Test db "model"
		System.out.println("////////////////////    INIZIO      ////////////////////");
		System.out.println("Proposte:\n");
		for(Entry<String, Proposta> entry : database.getAllProposte().entrySet()) {
			System.out.println("Proposta n. " + entry.getKey());
		}
		System.out.println("-----------------------\n");
		System.out.println("Tracce nella proposta:\n");
		Proposta p= database.getProposta(idProposta);
		if(p!=null) {
			for(Traccia t : p.getTracceProposte()) {
				System.out.println(t.getNome());
			}			
		}else {
			System.out.println("proposta nulla");
	
		}
		System.out.println("-----------------------\n");	
		System.out.println("Tracce nel progetto:\n");
		Progetto prog = database.getProgetto(idProgetto);
		for(Traccia t : prog.getTracce()) {
			System.out.println(t.getNome());
		}
		System.out.println("-----------------------\n");
		System.out.println("Tracce:\n");
		List<Traccia> tracce = new ArrayList<>(database.getAllTracce().values());
		for(Traccia t: tracce) {
			System.out.println(t.getNome());
		}
		
		//Test associazioni
		System.out.println("-----------------------\n");
		System.out.println("Associazione traccia proposta:\n");
		Map<String, String> associazioneTracciaProposta = database.getAllAssociazTracciaProposta();
		for(Map.Entry<String, String> entry: associazioneTracciaProposta.entrySet()) {
			System.out.println("Traccia: "+ entry.getKey() + " - Autore: " + entry.getValue());
		}
		
		System.out.println("-----------------------\n");
		System.out.println("Associazione traccia progetto:\n");
		Map<String, String> associazioneTracciaProgetto = database.getAllAssociazTracciaProgetto();
		for(Map.Entry<String, String> entry: associazioneTracciaProgetto.entrySet()) {
			System.out.println("Traccia: "+ entry.getKey() + " - Progetto: " + entry.getValue());
		}
		System.out.println("//////////////////        FINE        //////////////////////");
		System.out.println("////////////////////////////////////////////////////////////\n\n\n");
	
	
		*/
	
	}
	
	
	public void init() {
		 database = (Database)getServletContext().getAttribute("database");
	}
}
