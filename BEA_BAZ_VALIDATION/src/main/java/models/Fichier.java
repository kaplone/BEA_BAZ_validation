package models;

import java.nio.file.Paths;

import org.bson.types.ObjectId;

import utils.MongoAccess;

public class Fichier extends Commun{
	
	private String fichierLie;
	
	private String legende;
	
	private ObjectId oeuvre_id;
	
	public static void update(Fichier c){

		MongoAccess.update("fichier", c);
	}
	
    public static void save(Fichier c){
		
		MongoAccess.save("fichier", c);
		
	}
    
    @Override
    public String toString(){
    	
    	System.out.println("this.fichierLie : " + this.fichierLie);
    	System.out.println("Paths.get(this.fichierLie).getFileName() : " + Paths.get(this.fichierLie).getFileName());
    	
    	return Paths.get(this.fichierLie).getFileName().toString();
    	
    }

	public String getFichierLie() {
		return fichierLie;
	}

	public void setFichierLie(String fichierLie) {
		this.fichierLie = fichierLie;
	}

	public String getLegende() {
		return legende;
	}

	public void setLegende(String legende) {
		this.legende = legende;
	}
	
	@Override
	public String getNom(){
		return Paths.get(this.fichierLie).getFileName().toString();
	}

	public ObjectId getOeuvre_id() {
		return oeuvre_id;
	}

	public void setOeuvre_id(ObjectId oeuvre_id) {
		this.oeuvre_id = oeuvre_id;
	}
	

}
