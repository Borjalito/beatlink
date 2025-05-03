package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import beans.*;
import model.Incarico;
import model.Licenza;
import model.Progetto;
import model.Traccia;

@WebServlet("/upload")
@MultipartConfig
public class CreaProgettoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR_TRACCE = "tracce";
	private static final String UPLOAD_DIR_IMMAGINI = "immagini";
	private Database database;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String titolo = request.getParameter("titolo");
		String descrizione = request.getParameter("descrizione");
		Licenza licenza = Licenza.valueOf(request.getParameter("licenza"));
		Integer bpm = null;
		if(!request.getParameter("bpm").isBlank())
			bpm = Integer.parseInt(request.getParameter("bpm")); //facoltativo
			
		List<String> tags = null;
		if(!request.getParameter("tag").isBlank())
			tags = Arrays.asList(request.getParameter("tag").split("\\s*,\\s*"));//facoltativo
		
		
		/*
		//*** IMMAGINE ***-------------------------------------------------------------------
		//estrazione e salvataggio immagine
		String relativePath = UPLOAD_DIR_IMMAGINI; // Nome della directory
        String absolutePath = getServletContext().getRealPath(relativePath);
        File uploadDirImmagine = new File(absolutePath);
        if (!uploadDirImmagine.exists()) {
            uploadDirImmagine.mkdirs();
        }
        
        // Ottieni l'immagine caricata
        Part partImmagine;
        File immagine = null;
		try {
			partImmagine = request.getPart("immagine");
		
			String fileName = getFileName(partImmagine);
			immagine = new File(uploadDirImmagine, fileName);
        	InputStream input = partImmagine.getInputStream();
            Files.copy(input, immagine.toPath());
		} catch (IOException | ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		File immagine = null; //inizializzio a null per metterlo nel db e ottenere l'id
		try {	
			Part imagePart = request.getPart("immagine");
			String uploadPathImmagini = getServletContext().getRealPath("") +  UPLOAD_DIR_IMMAGINI;	
	    	String fileName = getFileName(imagePart);
	        String extension =  getFileExtension(fileName);
	        String immagineId= database.addImmagine(immagine, extension);
	        //non associo l'immagine al progetto nel db, non esiste la map e non penso che serva (basta prendere l'attributo di progetto)
	        String filePath = uploadPathImmagini + File.separator+immagineId;
	        
	        File f = new File(uploadPathImmagini);
            if (!f.exists()) {
                f.mkdirs();
            }
               
	        immagine = new File(filePath);  //solo ora che ho il filepath creo l'immagine
	        imagePart.write(filePath); 
	        
		} catch (Exception e) {
            System.out.println("Errore durante l'upload dell'immagine: " + e.getMessage());
        }
		
		/* questa parte deve essere qui altrimenti incarichi e tracce non li gestisco bene*/
		User user = (User)request.getSession().getAttribute("user");
		String username = user.getUsername();
		Progetto progetto = new Progetto(titolo, descrizione, bpm, immagine, database.getUtente(username) ,licenza);
		String progettoId = database.addProgetto(progetto);
		database.associaProgettoCreatore(progettoId, username);
		
		
		
		//*** TRACCE ***------------------------------------------------------------------
		List<Traccia> tracceIniziali = new ArrayList<>();
		
		
		// Ottieni il percorso reale della directory di upload
        String uploadPathTracce = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR_TRACCE;
       
        // Crea la directory se non esiste
        File uploadDirAudio = new File(uploadPathTracce);
        if (!uploadDirAudio.exists()) {
            uploadDirAudio.mkdir();
        }
        
        try {
            // Ottieni tutti i file dalla richiesta
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("audioFiles") && part.getSize() > 0) {
                    String fileName = getFileName(part);
                    Traccia traccia=new Traccia(fileName, null, database.getUtente(username));
                    String extension =  getFileExtension(fileName);
                    String tracciaId= database.addTraccia(traccia, extension);
                    database.associaTracciaProgetto(tracciaId, progettoId);
                    tracceIniziali.add(traccia);
                    String filePath = uploadPathTracce + File.separator+tracciaId;
                    traccia.setTraccia(new File(filePath));
                    part.write(filePath); 
                }
            } 
        } catch (Exception e) {
            System.out.println("Errore durante l'upload dei file: " + e.getMessage());
        }
        
        //*** INCARICHI ***-------------------------------------------------------------------
  		List<Incarico> incarichiIniziali = new ArrayList<>();
  		int i=1;
  		while(request.getParameter("titoloIncarico"+i) != null) {
  			String titoloIncarico = request.getParameter("titoloIncarico"+i);
  			String descrizioneIncarico = request.getParameter("descrizioneIncarico"+i);
  			if(!titoloIncarico.isBlank() && !descrizioneIncarico.isBlank()) {
  				Incarico incIniz = new Incarico(titoloIncarico, descrizioneIncarico);
  				String incaricoId = database.addIncarico(incIniz);
  				database.associaIncaricoProgetto(incaricoId, progettoId);
  				incarichiIniziali.add(incIniz);
  			}
  			i++;
  		}
      	
      		
        //"progetto" è stato inserito sul db ma lavorando sul riferimento vengono effettuate le modifiche
  		if(tags!=null) {
  			for(String t : tags)
  				progetto.aggiungiTag(t);
  		}
  		if(incarichiIniziali.size()>0) {
  			//dovrebbe sempre essercene uno ma sono blando nel controllo per evitare problemi
			progetto.setIncarichi(incarichiIniziali);
		}
  		if(tracceIniziali.size()>0) {
  			progetto.setTracce(tracceIniziali);
  		}
		
  		
	    try {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/pages/HomeCreatore.jsp");
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
	
	private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex);
    }
	
	public void init() {
		 database = (Database)getServletContext().getAttribute("database");
	}
}
