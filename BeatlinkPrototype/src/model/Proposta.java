package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Proposta {
	private List<Traccia> tracceProposte;
	
	public Proposta() {
		this.tracceProposte= new ArrayList<>();
	}
	
	public List<Traccia> getTracceProposte(){
		return tracceProposte;
	}
	
	public void aggiungiTracciaAProposta(Traccia traccia) {
		tracceProposte.add(traccia);
	}

	public void remove(Traccia traccia) {
        for (Iterator<Traccia> iterator = tracceProposte.iterator(); iterator.hasNext();) {
            Traccia t = iterator.next();
            if (t.equals(traccia)) {
                iterator.remove();
                break; 
            }
        }
    }
	
}
