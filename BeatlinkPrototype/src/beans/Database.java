package beans;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import model.Incarico;
import model.Licenza;
import model.Profilo;
import model.Progetto;
import model.Proposta;
import model.Traccia;
import model.Utente;

public class Database {
	private Map<String, User> utenti;
	private Map<String, Utente> beatlinkUtenti;
	private Map<String, Progetto> progetti;
	private Map<String, Incarico> incarichi;
	private Map<String, Proposta> proposte;
	private Map<String, Traccia> tracce;
	private Map<String, File> immagini;
	private Map<String, String> AssociazProgettoCreatore;
	private Map<String, String> AssociazIncaricoProgetto;
	private Map<String, String> AssociazTracciaProgetto;
	private Map<String, String> AssociazPropostaIncarico;
	private Map<String, String> AssociazTracciaProposta;
	private Map<String, String> AssociazPropostaAutore; //non è nell'ER
	
		
	private AtomicLong atomicProgetti;
	private AtomicLong atomicIncarichi;
	private AtomicLong atomicProposte;
	private AtomicLong atomicTracce;
	private AtomicLong atomicImmagini;
	
	public Database() {
		
		utenti=new HashMap<>();
		beatlinkUtenti=new HashMap<>();
		progetti=new HashMap<>();
		incarichi=new HashMap<>();
		proposte=new HashMap<>();
		tracce = new HashMap<>();
		immagini = new HashMap<>();
		AssociazProgettoCreatore=new HashMap<>();
		AssociazIncaricoProgetto = new HashMap<>();
		AssociazTracciaProgetto = new HashMap<>();
		AssociazPropostaIncarico = new HashMap<>();
		AssociazTracciaProposta=new HashMap<>();
		AssociazPropostaAutore= new HashMap<>();
		
		
		atomicProgetti = new AtomicLong();
		atomicIncarichi = new AtomicLong();
		atomicProposte = new AtomicLong();
		atomicTracce = new AtomicLong();
		atomicImmagini = new AtomicLong();
		
		utenti.put("admin", new User("admin", "admin"));
		utenti.put("admin2", new User("admin2", "admin2"));
		utenti.put("admin3", new User("admin3", "admin3"));
		
		makeSomeActivity();
		//makeOtherActivity();
	}
	
	public void associaTracciaProgetto(String idTraccia, String idProgetto) {
		 AssociazTracciaProgetto.put(idTraccia, idProgetto); 
	}
	
	public void addTracciaAProgetto(String idTraccia, String idProgetto) {
		progetti.get(idProgetto).aggiungiTraccia(tracce.get(idTraccia)); 
	}
	
	public int removeTracciaDaProposta(String idProposta, String idTraccia) {
		//simile a valutaTraccia() nel pdf
		proposte.get(idProposta).remove(tracce.get(idTraccia));  
		return proposte.get(idProposta).getTracceProposte().size();
	}
	
	public void dissociaTracciaProposta(String idTraccia) {
		AssociazTracciaProposta.remove(idTraccia);
	}
	

	/* GET ASSOCIAZIONI */
	
	public List<String> getTraccePropostaId(String idProposta){
		List<String> traccePropostaId = new ArrayList<>();
				
		for(Map.Entry<String, String> entry : AssociazTracciaProposta.entrySet()) {
			if(entry.getValue().equals(idProposta)) {
				traccePropostaId.add(entry.getKey());
			}
		}	
		return traccePropostaId;
	}
	
	
	public List<String> getProposteIncaricoId(String idIncarico){
		List<String> proposteIncaricoId = new ArrayList<>();
				
		for(Map.Entry<String, String> entry : AssociazPropostaIncarico.entrySet()) {
			if(entry.getValue().equals(idIncarico)) {
				proposteIncaricoId.add(entry.getKey());
			}
		}	
		return proposteIncaricoId;
	}
	
	public List<String> getIncarichiProgettoId(String idProgetto){
		List<String> incarichiProgettoId = new ArrayList<>();
		
		for(Map.Entry<String, String> entry : AssociazIncaricoProgetto.entrySet()) {
			if(entry.getValue().equals(idProgetto)) {
				incarichiProgettoId.add(entry.getKey());
			}
		}	
		return incarichiProgettoId;
		
	}
	
	public List<String> getProgettiCreatoreId(String usernameCreatore){
		List<String> progettiCreatoreId = new ArrayList<>();
		
		for(Map.Entry<String, String> entry : AssociazProgettoCreatore.entrySet()) {
			if(entry.getValue().equals(usernameCreatore)) {
				progettiCreatoreId.add(entry.getKey());
			}
		}	
		return progettiCreatoreId;
	}
	
	public String getIncaricoIdFromProposta(String idProposta) {
		return AssociazPropostaIncarico.get(idProposta);
	} 
	
	public String getUsernameFromProposta(String idProposta) {
		return AssociazPropostaAutore.get(idProposta);
	}
		
	public String getProgettoIdFromIncarico(String incaricoId) {
		return AssociazIncaricoProgetto.get(incaricoId);
	}
	
	/* IMMAGINE */ 
	public String addImmagine(File immagine, String extension) {
		String idImmagine = String.valueOf(atomicImmagini.getAndIncrement() + extension);
		immagini.put(idImmagine, immagine);
		return idImmagine;
	}
	public File getImmagine(String id) {
		return immagini.get(id);
	}
	
	/* TRACCIA */
	public Traccia getTraccia(String id) {
		return tracce.get(id);
	}
	public  Map<String, Traccia> getAllTracce() {
		return tracce;
	}
	public String addTraccia(Traccia traccia, String extension) {
		String idTraccia = String.valueOf(atomicTracce.getAndIncrement() + extension);
		tracce.put(idTraccia, traccia);
		return idTraccia;
	}
	
	/* PROPOSTA */
	public Proposta getProposta(String id) {
		return proposte.get(id);
	}
	public  Map<String, Proposta> getAllProposte() {
		return proposte;
	}
	
	public String addProposta(Proposta proposta) {
		String idProposta = String.valueOf(atomicProposte.getAndIncrement());
		proposte.put(idProposta, proposta);
		return idProposta;
	}
	
	public void associaPropostaAutore(String idProposta, String username) {
		AssociazPropostaAutore.put(idProposta, username);
	}
	
	public void associaPropostaIncarico(String idProposta, String idIncarico) {
		AssociazPropostaIncarico.put(idProposta, idIncarico);
	}
	
	public void addPropostaAIncarico(Proposta proposta, String idIncarico) {
		incarichi.get(idIncarico).aggiungiProposta(proposta);
	}
	
	
	public void addTracciaAProposta(String tracciaId, String propostaId) {
		Proposta proposta = this.getProposta(propostaId);
		Traccia traccia = this.getTraccia(tracciaId);
		proposta.aggiungiTracciaAProposta(traccia);  
	}
	
	public void associaTracciaProposta(String idTraccia, String idProposta) {
		AssociazTracciaProposta.put(idTraccia, idProposta);
	}
	
	public void removePropostaDaIncarico(String idProposta, String idIncarico) {
		Incarico incarico = incarichi.get(idIncarico);
		incarico.rimuoviProposta(proposte.get(idProposta)); //lavora per riferimento equals
	}
	
	public void removeProposta(String idProposta) {
		proposte.remove(idProposta);
	}
	
	public void dissociaPropostaIncarico(String idProposta) {
		AssociazPropostaIncarico.remove(idProposta);
	}
	
	public void dissociaPropostaAutore(String idProposta) {
		AssociazPropostaAutore.remove(idProposta);
	}
	
	
	/* INCARICO */
	public Incarico getIncarico(String id) {
		return incarichi.get(id);
	}
	public  Map<String, Incarico> getAllIncarichi() {
		return incarichi; 
	}
	public String addIncarico(Incarico incarico) {
		String idIncarico = String.valueOf(atomicIncarichi.getAndIncrement());
		incarichi.put(idIncarico, incarico);
		return idIncarico;
	}
	
	public void associaIncaricoProgetto(String idIncarico, String idProgetto) {
		AssociazIncaricoProgetto.put(idIncarico, idProgetto);
	}
	
	public void addIncaricoAProgetto(String idProgetto, Incarico incarico) {
		progetti.get(idProgetto).aggiungiIncarico(incarico);
	}
	
	/* PROGETTO */
	public Progetto getProgetto(String id) {
		return progetti.get(id);
	}
	public  Map<String, Progetto> getAllProgetti() {
		return progetti;
	}
	public String addProgetto(Progetto progetto) {
		String idProgetto = String.valueOf(atomicProgetti.getAndIncrement());
		progetti.put(idProgetto, progetto);
		return idProgetto;	
	}
	
	public void associaProgettoCreatore(String idProgetto, String creatore) {
		AssociazProgettoCreatore.put(idProgetto, creatore);
	}
	
	
	
	/* UTENTE (dominio beatlink)*/
	public Utente getUtente(String username) {
		return beatlinkUtenti.get(username);
	}
	public  Map<String, Utente> getAllUtenti() {
		return beatlinkUtenti;
	}
	public void addUtente(Utente utente) {
		beatlinkUtenti.put(utente.getUsername(), utente);
	}
	
	/* USER (credenziali) */
	public User getUser(String username) {
		return utenti.get(username);
	}
	public  Map<String, User> getAllUsers() {
		return utenti;
	}
	public void addUser(User user) {
		utenti.put(user.getUsername(), user);
	}
	public void removeUser(String username) {
		utenti.remove(username);
	}
	
	public boolean userAndPasswordCheck(String username, String password) {
		boolean res=false;
		
		User u = utenti.get(username);
		if(u!=null) {
			if(u.getPassword().equals(password)){
				res=true;
			}
		}
		
		return res;
	}
	
	

	public String getPropostaIdFromTraccia(String idTraccia) {
		return AssociazTracciaProposta.get(idTraccia);
	}
	
	public Map<String, String> getAllAssociazTracciaProposta(){
		return AssociazTracciaProposta;
	}
	
	public Map<String, String> getAllAssociazTracciaProgetto(){
		return AssociazTracciaProgetto;
	}
	
	public Map<String, String> getAllAssociazIncaricoProgetto(){
		return AssociazIncaricoProgetto;
	}
	
	public Map<String, String> getAllAssociazPropostaIncarico(){
		return AssociazPropostaIncarico;
	}
	
	public Map<String, String> getAllAssociazPropostaAutore(){
		return AssociazPropostaAutore;
	}
	
	private void makeOtherActivity() {
	
		this.addUser(new User("miky", "miky"));
		this.addUser(new User("dani", "dani"));
		this.addUser(new User("riky", "riky"));
		
		Utente miky=new Utente("miky", new Profilo("I love to share my sound", null));
		Utente dani=new Utente("dani", new Profilo("Music is my life", null));
		Utente riky=new Utente ("riky",new Profilo("I am the emperor of music", null));
		
		this.addUtente(miky);
		this.addUtente(dani);
		this.addUtente(riky);
		
		List<Incarico> incharichiMiky= new ArrayList<>();
		List<Incarico> incharichiDani= new ArrayList<>();
		List<Incarico> incharichiRiky= new ArrayList<>();
		
		Incarico inc1=new Incarico("Top Line", "mandate la top line");
		Incarico inc2=new Incarico("Bass line", "mandate qualche linea di basso");
		Incarico inc3=new Incarico("Perc line", "mandate le percussioni");
		
		String idIncarico1 = this.addIncarico(inc1);
		String idIncarico2 = this.addIncarico(inc2);
		String idIncarico3 = this.addIncarico(inc3);
		
		incharichiMiky.add(inc1);
		incharichiDani.add(inc2);
		incharichiRiky.add(inc3);

		Progetto ProgettoMiky=new Progetto("darkrai", "Beat incompleto, che gira attorno a un 808 e un pianoforte", 160, null, miky, Licenza.BY);
		ProgettoMiky.aggiungiIncarico(inc1);
		
		Progetto ProgettoDani = new Progetto("Cinema", "sonorità cinematiche", 120, null, dani , Licenza.BY);
		ProgettoDani.aggiungiIncarico(inc2);
		
		Progetto ProgettoRiky= new Progetto("Foresta incantata", "Melodie oniriche, sarebbe ottimo il clarinetto ma vanno bene altri strumenti a fiato", 70, null, riky , Licenza.BY);
		ProgettoRiky.aggiungiIncarico(inc3);

		String idProgettoMiky = this.addProgetto(ProgettoMiky);
		String idProgettoDani = this.addProgetto(ProgettoDani);
		String idProgettoRiky = this.addProgetto(ProgettoRiky);
	
		this.associaIncaricoProgetto(idIncarico1, idProgettoMiky);
		this.associaIncaricoProgetto(idIncarico2, idProgettoDani);
		this.associaIncaricoProgetto(idIncarico3, idProgettoRiky);
	
	}
		
	
	private void makeSomeActivity() {
		//Creo due utenti di default (che diventano un creatore e un candidato)
		
		User philUser = new User("Phil51", "Phil51"); //creatore
		User davidUser = new User("David46", "David46"); //candidato
		this.addUser(philUser);
		this.addUser(davidUser);
		
		Utente phil = new Utente("Phil51", new Profilo("me la cavo come batterista e cantante", null));
		Utente david = new Utente("David46", new Profilo("piacevolmente insensibile", null));
		this.addUtente(phil);
		this.addUtente(david);
			
		//creazione progetto
		String descrizioneProgetto = "voglio fare un progetto con gli strumenti più tradizionali, niente synth. Invito particolare per cantanti, chitarristi, batteristi, bassisti";
		Progetto rock = new Progetto("Let's Rock", descrizioneProgetto, 130, null, phil, Licenza.BY_NC);
		ArrayList<Incarico> incarichiRock = new ArrayList<>();
		incarichiRock.add(new Incarico("Jam", "Voglio ascoltare qualche idea prima di aprire altri incarichi. Mandate le vostre idee"));
		rock.setIncarichi(incarichiRock);
		
		String idProgetto = this.addProgetto(rock);
		this.associaProgettoCreatore(idProgetto, phil.getUsername());
		for(Incarico i : incarichiRock) {
			String idIncarico = this.addIncarico(i);
			this.associaIncaricoProgetto(idIncarico, idProgetto);
		}
			
		/* cosa brutta ma funziona perché avremo solo un'incarico */
		String idIncarico = new ArrayList<>(this.incarichi.keySet()).get(0);
		
		List<Traccia> tracceProposta = new ArrayList<>();
		File traccia1 = new File("tracce/chitarra_ritmata1.wav");
		File traccia2 = new File("tracce/chitarra_ritmata2.wav");
		
		if (traccia1.exists()) {
            System.out.println("File trovato: " + traccia1.getAbsolutePath());
        } else {
            System.out.println("Il file non esiste.");
        }
		tracceProposta.add(new Traccia("Chitarra1", traccia1, david));
		tracceProposta.add(new Traccia("Chitarra2", traccia2, david));
			
		//david effettua una proposta
		Proposta proposta = new Proposta();
		String idProposta = this.addProposta(proposta);
		for(Traccia t : tracceProposta) {		
			//metto .wav perché so per certo quello che carico e non voglio codice per ottenere l'estensione
			String idTraccia = this.addTraccia(t, ".wav"); 
			proposta.aggiungiTracciaAProposta(t);
			this.associaTracciaProposta(idTraccia, idProposta);
		}
			
		this.associaPropostaIncarico(idProposta, idIncarico);
		this.associaPropostaAutore(idProposta, david.getUsername());
		this.addPropostaAIncarico(proposta, idIncarico);

	}
}
