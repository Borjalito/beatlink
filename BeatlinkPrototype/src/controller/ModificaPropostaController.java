package controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import beans.Database;
import beans.User;
import model.Traccia;
import model.Utente;

@MultipartConfig
public class ModificaPropostaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Percorso della directory di upload
    private static final String UPLOAD_DIR = "tracce";
    private Database database;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action= request.getParameter("action");

    	if(action.equals("Carica")) {
    		
    		this.aggiungiTracceProposta(request, response);
    		
    	}else {
    		
    		if(action.equals("Rimuovi")) {
    			this.rimuoviTracceProposta(request,response);
    			
    		}else {
    			System.out.println("Bad parameter");
    			
    		}
    		
    	}
    	
        request.getRequestDispatcher("/pages/ViewModificaProposta.jsp").forward(request, response);   

    }

    // Metodo per ottenere il nome del file dalla parte del file
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    
    public void aggiungiTracceProposta(HttpServletRequest request, HttpServletResponse response) {
    	
    	//String url = request.getRequestURL().toString(); 
        //String baseURL = url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath() + "/"; 
    	
    	// Ottieni il percorso reale della directory di upload
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

        // Crea la directory se non esiste
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        } 
       
        User u= (User)request.getSession().getAttribute("user");
        String username=u.getUsername();
        Utente utente=database.getUtente(username); 
   	 	String propostaId=(String)request.getSession().getAttribute("currentIdProposta");  

        
        try {
            // Ottieni tutti i file dalla richiesta
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if (part.getName().equals("audioFiles") && part.getSize() > 0) {
                    String fileName = getFileName(part);
                    String extension = getFileExtension(fileName);
                    Traccia traccia=new Traccia(fileName, null, utente);                    
                    String tracciaId= database.addTraccia(traccia, extension);
                    database.addTracciaAProposta(tracciaId, propostaId);
                    database.associaTracciaProposta(tracciaId, propostaId);
                    String filePath = uploadPath + File.separator+tracciaId;
                    traccia.setTraccia(new File(filePath));
                    part.write(filePath);
                  
                }
            } 
           
        } catch (Exception e) {
            System.out.println("Errore durante l'upload dei file: " + e.getMessage());
        }
    }
    
    public void rimuoviTracceProposta(HttpServletRequest request, HttpServletResponse response) {
    	String[] selectedItems = request.getParameterValues("selectedTracks");
    	
    	 String currentPropId=(String)request.getSession().getAttribute("currentIdProposta");  
    	 
    	 if (selectedItems != null) {
            
    		 for (String trackId : selectedItems) {
            database.dissociaTracciaProposta(trackId);
            database.removeTracciaDaProposta(currentPropId, trackId);
            }
        } else {
            System.out.println("Nessun elemento selezionato.");
        }
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
    
    // Metodo per salvare i dettagli del file nel database (opzionale)
    // private void saveFileToDatabase(String incaricoId, String fileName, String filePath) {
    //     // Implementa la logica per salvare i dettagli nel database
    // }
}
