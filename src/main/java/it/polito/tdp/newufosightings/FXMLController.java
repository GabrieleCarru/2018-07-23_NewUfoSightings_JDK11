package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.AvvistamentiStato;
import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;
	private Integer anno;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtAnno;

    @FXML
    private Button btnSelezionaAnno;

    @FXML
    private ComboBox<String> cmbBoxForma;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtAlfa;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	String shape = cmbBoxForma.getValue();
    	
    	if(shape == null) {
    		txtResult.appendText("Errore: selezionare una forma dal menù a tendina per proseguire. \n");
    		return;
    	}
    	
    	this.model.creaGrafo(anno, shape);
    	
    	txtResult.appendText(String.format("Grafo creato! [#Vertici %d, #Archi %d] \n", 
    										this.model.getNumberVertex(), this.model.getNumberEdge()));
    	
    	List<AvvistamentiStato> avvistamentiStato = this.model.avvistamentiPerStato();
    	
    	for(AvvistamentiStato as : avvistamentiStato) {
    		txtResult.appendText(as.toString());
    	}
    	
    }

    @FXML
    void doSelezionaAnno(ActionEvent event) {

    	txtResult.clear();
    	
    	try {
    		anno = Integer.parseInt(txtAnno.getText());
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Errore: inserire un valore intero positivo per l'anno. \n");
    	}
    	
    	if(anno < 1910 || anno > 2014) {
    		txtResult.appendText("Errore: inserire un anno compreso tra il 1910 e il 2014. \n");
    		return;
    	}
    	
    	cmbBoxForma.getItems().addAll(this.model.getAllShapeByAnno(anno));
    	
    }

    @FXML
    void doSimula(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
