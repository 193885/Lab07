package it.polito.tdp.poweroutages;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	private Model model;

	public void setModel(Model model) {
		
		this.model=model;
		
		for(Nerc n : model.getNercList())
		
			choiceNERC.getItems().add(n);
		
	}

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="choiceNERC"
    private ChoiceBox<Nerc> choiceNERC; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAnalisi(ActionEvent event) {
    	
    	txtResult.clear();
    	
		int anni = Integer.parseInt(txtYears.getText());
		
		int ore = Integer.parseInt(txtHours.getText());
    	
    	if(! txtHours.getText().matches("[0-9]+") )
    		
    		txtResult.setText("formato ora non valido!");
    	
    	if(! txtYears.getText().matches("[0-9]+") || anni > model.getListaAnniPwrOut().size() ) 
    		
    		txtResult.setText("selezionare un numero di anni compreso tra 1 e " +  model.getListaAnniPwrOut().size());
    	
    	if(choiceNERC.getValue() == null) 

    		txtResult.setText("Selezionare una NERC!");
    		
    	else {
    		    	    			    		
    		List <PowerOutage>worstCase = model.trovaOttimo(choiceNERC.getValue() , ore, anni);
    		
    		txtResult.appendText("Tot people affected: " + model.totClientiColpiti(worstCase) + "\n");
			txtResult.appendText("Tot hours of outage: " + model.totOrediPwrOut(worstCase) + "\n");
			
			for (PowerOutage ee : worstCase) 
				
				txtResult.appendText(ee.toString()+"\n");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert choiceNERC != null : "fx:id=\"choiceNERC\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }
}