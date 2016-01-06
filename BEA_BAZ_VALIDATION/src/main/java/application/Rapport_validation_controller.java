package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.jongo.MongoCursor;

import enums.Progression;
import utils.MongoAccess;
import models.Auteur;
import models.Client;
import models.Commande;
import models.Messages;
import models.Oeuvre;
import models.OeuvreTraitee;
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

public class Rapport_validation_controller  implements Initializable{
	
	@FXML
	private TextArea rapport_textArea;
	
	@FXML
	private Button fermer_button;
	
	Stage currentStage;
	
    public void onVersClient(){
		
		Scene client_scene = new Scene((Parent) JfxUtils.loadFxml("/views/fiche_client.fxml"), 1275, 722);
		client_scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		currentStage.setScene(client_scene);	
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		currentStage = Messages.getStage();
		
		fermer_button.setOnAction(a -> onVersClient());
		
		System.out.println(Messages.getCommande_id());
		
		Commande commande = MongoAccess.request("commande", Messages.getCommande_id()).as(Commande.class).next();
		
		rapport_textArea.setText(commande.getOeuvresTraitees_id()
				                         .values()
				                         .stream()
				                         .map(i -> MongoAccess.request("oeuvreTraitee", i).as(OeuvreTraitee.class).next())
				                         .filter(i_ -> i_.getProgressionOeuvreTraitee().equals(Progression.FAIT_))
				                         .map(j -> MongoAccess.request("oeuvre", j.getOeuvre_id()).as(Oeuvre.class).next())
				                         .filter(k -> k.getTechniquesUtilisees_id().values().contains(null))
				                         .map(l -> l.getCote_archives_6s() + "\n" + l.getTechniquesUtilisees_id())
				                         .collect(Collectors.joining("\n\n")));
	
	}
}
