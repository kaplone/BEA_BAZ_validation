package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import utils.MongoAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Oeuvre extends Commun{
	
	private String key1;
    private String key2;
	
	private String n_d_origine,
	               cote_archives,
	               cote_archives_6s,
	               ville,
	               quartier,
	               titre_de_l_oeuvre,
	               date, 
	               dimensions,
	               _observations,
	               field_25,
	               inscriptions_au_verso,
	               format_de_conditionnement;
	
	private ObjectId auteur;
	
	private Map<String, ObjectId> matieresUtilisees_id;
	private Map<String, ObjectId> techniquesUtilisees_id;
	
	private String etat_current;
	
	public Oeuvre(){
		matieresUtilisees_id = new HashMap<>();
		techniquesUtilisees_id = new HashMap<>();
	}
	
	@Override
	public String toString(){
		return this.cote_archives;
	}
	
	@Override
	public String getNom(){
		
		if(getCote_archives_6s().equals("SN")){
			return String.format("   SN - %s" , titre_de_l_oeuvre);
		}
		else {
			try {
				return String.format("%04d - %s", Integer.parseInt(getCote_archives_6s() != "" ? getCote_archives_6s() : "0") , titre_de_l_oeuvre);
			}
			catch (NumberFormatException nfe) {
				return String.format("%04d - %s", Integer.parseInt(getCote_archives_6s() != "" ? getCote_archives_6s().split("\\.")[0] : "0") , titre_de_l_oeuvre);
			}
		}
		
		
		
	}
	
	public static void update(Oeuvre c){

		MongoAccess.update("oeuvre", c);
	}
	
    public static void save(Oeuvre c){
		
		MongoAccess.save("oeuvre", c);
		
	}
    
    public void addMatiere(String m, ObjectId oid){
    	
        if (! matieresUtilisees_id.keySet().contains(m)){
    		matieresUtilisees_id.put(m, oid);
    	}
    	
    }
    
    public void deleteMatiere(String m){
    	
    	for (String m_ : matieresUtilisees_id.keySet()){
    		if (m.equals(m_)){
    			matieresUtilisees_id.remove(m_);
    			break;
    		}
    	} 	
    }
    
    public void addTechnique(String t, ObjectId oid){
    	

    	if (! techniquesUtilisees_id.keySet().contains(t)){
    		techniquesUtilisees_id.put(t, oid);
    	}
    	
    }
    
    public void deleteTechnique(String t){

    	for (String t_ : techniquesUtilisees_id.keySet()){
    		if (t.equals(t_)){
    			techniquesUtilisees_id.remove(t_);
    			break;
    		}
    	} 	
    }

	public String getN_d_origine() {
		return n_d_origine;
	}

	public void setN_d_origine(String n_d_origine) {
		this.n_d_origine = n_d_origine;
	}

	public String getCote_archives_6s() {
		return cote_archives_6s != null ? cote_archives_6s : "0";
	}

	public void setCote_archives_6s(String cote_archives_6s) {
		this.cote_archives_6s = cote_archives_6s;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getQuartier() {
		return quartier;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public String getTitre_de_l_oeuvre() {
		return titre_de_l_oeuvre;
	}

	public void setTitre_de_l_oeuvre(String titre_de_l_oeuvre) {
		this.titre_de_l_oeuvre = titre_de_l_oeuvre;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public String get_observations() {
		return _observations;
	}

	public void set_observations(String _observations) {
		this._observations = _observations;
	}

	public String getField_25() {
		return field_25;
	}

	public void setField_25(String field_25) {
		this.field_25 = field_25;
	}

	public String getInscriptions_au_verso() {
		return inscriptions_au_verso;
	}

	public void setInscriptions_au_verso(String inscriptions_au_verso) {
		this.inscriptions_au_verso = inscriptions_au_verso;
	}

	public String getFormat_de_conditionnement() {
		return format_de_conditionnement;
	}

	public void setFormat_de_conditionnement(String format_de_conditionnement) {
		this.format_de_conditionnement = format_de_conditionnement;
	}

	public ObjectId getAuteur() {
		return auteur;
	}

	public void setAuteur(ObjectId auteur) {
		this.auteur = auteur;
	}

	public String getEtat_current() {
		return etat_current;
	}

	public void setEtat_current(String etat_current) {
		this.etat_current = etat_current;
	}

	public Set<String> getMatieresUtilisees_names() {
		return matieresUtilisees_id.keySet();
	}

	public void AddMatiereUtilisee(Matiere matiereUtilisee) {
		this.matieresUtilisees_id.put(matiereUtilisee.getNom(), matiereUtilisee.get_id());
	}

	public Set<String> getTechniquesUtilisees_names() {
		return techniquesUtilisees_id.keySet();
	}

	public void addTechniqueUtilisee(Technique techniqueUtilisee) {
		this.techniquesUtilisees_id.put(techniqueUtilisee.getNom(), techniqueUtilisee.get_id());
	}

	public Map<String, ObjectId> getMatieresUtilisees_id() {
		return matieresUtilisees_id;
	}

	public void setMatieresUtilisees_id(Map<String, ObjectId> matieresUtilisees_id) {
		this.matieresUtilisees_id = matieresUtilisees_id;
	}

	public Map<String, ObjectId> getTechniquesUtilisees_id() {
		return techniquesUtilisees_id;
	}

	public void setTechniquesUtilisees_id(Map<String, ObjectId> techniquesUtilisees_id) {
		this.techniquesUtilisees_id = techniquesUtilisees_id;
	}
	
	public String getTechniquesUtilisees_noms_complets(){
		
		return getTechniquesUtilisees_id().entrySet()
                                          .stream()
                                          .map(a -> MongoAccess.request("technique", a.getValue())
                                                               .as(Technique.class)
                                                               .next()
                                                               .getNom_complet())
                                          .collect(Collectors.joining(", "));
	}
	
    public String getMatieresUtilisees_noms_complets(){
		
		return getMatieresUtilisees_id().entrySet()
                                        .stream()
                                        .map(a -> MongoAccess.request("matiere", a.getValue())
                                                             .as(Matiere.class)
                                                             .next()
                                                             .getNom_complet())
                                        .collect(Collectors.joining(", "));
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}
	

	
}
