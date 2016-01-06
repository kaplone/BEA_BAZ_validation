package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import utils.MongoAccess;
import models.Auteur;
import models.Client;
import models.Commande;
import models.Messages;
import models.Traitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Fiche_client_controller  implements Initializable{
	
	@FXML
	private ObservableList<String> liste_clients;
	@FXML
	private ObservableList<String> liste_commandes;
	
	@FXML
	private ListView<String> listView_client;
	@FXML
	private ListView<String> listView_commandes;
	@FXML
	private TextField nom_client_textField;
	@FXML
	private TextField nom_complet_client_textField;
	@FXML
	private TextArea remarques_client_textArea;

	
	MongoCursor<Client> clientCursor;
	MongoCursor<Commande> commandeCursor ;
	String clientSelectionne;
	String commandeSelectionne;
	
	Map <String, ObjectId> clients_id;
	Map <String, ObjectId> commandes_id;
	
	Stage currentStage;
	
	Commande commande;
	String client;
	Client c;
	
	private boolean edit = false;

	public void onVersRapport(){
		
		Scene rapport_scene = new Scene((Parent) JfxUtils.loadFxml("/views/rapport_validation.fxml"), 1275, 722);
		rapport_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(rapport_scene);	
	}

	@FXML
	public void onClientSelect(){

		clientSelectionne = listView_client.getSelectionModel().getSelectedItem();
		Messages.setClient(clientSelectionne);
		Messages.setClient_id(clients_id.get(clientSelectionne));
		
		commandes_id = MongoAccess.request("client", Messages.getClient_id()).as(Client.class).next().getCommandes_id();

		Messages.setCommandes_id(commandes_id);
		
		Messages.setAuteur(null);
		Messages.setCommande(null);
		Messages.setFichier(null);
		Messages.setFichiers_id(null);
		Messages.setModel(null);
		Messages.setObservablOeuvresTraitees(null);
		Messages.setOeuvre(null);
		Messages.setOeuvre_index(0);
		Messages.setOeuvresTraitees_id(null);
		Messages.setOeuvreTraitee(null);
		Messages.setTacheTraitement(null);
		Messages.setTacheTraitements_id(null);
		Messages.setTraitementsAttendus(null);
		Messages.setTraitementsAttendus_id(null);
		
		affichageInfos();
		
	}
	
	@FXML
	public void onCommandeSelect(){
		
		commandeSelectionne = listView_commandes.getSelectionModel().getSelectedItem();
		
		Messages.setCommande(commandeSelectionne);
		Messages.setCommande_id(commandes_id.get(commandeSelectionne));
		
		onVersRapport();
		
		
	}
	
    private void affichageInfos(){
    	
    	liste_commandes.clear();
    	
    	if (Messages.getClient() != null){
    		
    		c = MongoAccess.request("client", clients_id.get(clientSelectionne)).as(Client.class).next();
    		
    		nom_client_textField.setText(clientSelectionne);
    		nom_complet_client_textField.setText(c.getNom_complet());
        	remarques_client_textArea.setText(c.getRemarques());
    		
        	commandes_id = c.getCommandes_id(); 
    		liste_commandes.addAll(commandes_id.keySet());
    		
    		listView_commandes.setItems(liste_commandes);
    		
    	}
    	
    	
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		currentStage = Messages.getStage();
		clientSelectionne = Messages.getClient();
		Messages.setCommande(null);
		
		liste_clients = FXCollections.observableArrayList();
		liste_commandes  = FXCollections.observableArrayList();
		
		clients_id = Messages.getClients_id();

		if (clients_id == null){
			
			clients_id = new TreeMap<>();
			
			ArrayList<Client> clients = new ArrayList<>();
			clientCursor = MongoAccess.request("client").as(Client.class);
			
			int indexClient = 0;
			int iClient = 0;
			
			while (clientCursor.hasNext()){
				
				Client c = clientCursor.next();
				
				liste_clients.add(c.getNom());
				clients_id.put(c.getNom(), c.get_id());
				if (client != null && c.getNom().equals(client)){
					indexClient = iClient;
				}
				iClient ++;	
			}
			
			Messages.setClients_id(clients_id);
		}
		else {
			liste_clients.addAll(Messages.getClients_id().keySet());
		}
		
		listView_client.setItems(liste_clients);
		
		if (client != null) {
			listView_client.getSelectionModel().select(liste_clients.indexOf(client));
		}
		else if (liste_clients.size() > 0){
			listView_client.getSelectionModel().select(0);
            clientSelectionne = listView_client.getSelectionModel().getSelectedItem();
			
			Messages.setClient(clientSelectionne);
			Messages.setClient_id(clients_id.get(clientSelectionne));
		}

        if (commandes_id == null){
        	
        	if(Messages.getClient_id() != null){
        	    commandes_id = MongoAccess.request("client", Messages.getClient_id()).as(Client.class).next().getCommandes_id();    
        	}

        	Messages.setCommandes_id(commandes_id);
			
		}
		else {
			liste_commandes.addAll(Messages.getCommandes_id().keySet());
		}

		nom_client_textField.setEditable(false);
		nom_complet_client_textField.setEditable(false);
		remarques_client_textArea.setEditable(false);

		affichageInfos();
	}
}
