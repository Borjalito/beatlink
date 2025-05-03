package model;

import java.io.File;

public class Profilo {
	private String description;
	private File image;
	
	public Profilo() {
		this.description ="default description";
		image = null;
	}
	
	public Profilo(String description, File image) {
		this.description = description;
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public File getImage() {
		return image;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setImage(File image) {
		this.image = image;
	}
	
	
}
