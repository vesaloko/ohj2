package fxkuntorekisteri;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Kayttaja.cardioTreeni;
 
/**
 * @author venla
 * @version 24 Apr 2023
 * controlleri cardion dialogille
 */
public class CardioDialogController implements ModalControllerInterface<cardioTreeni>,Initializable  {
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelCardio;
    @FXML private GridPane gridCardio;
    
    private static cardioTreeni apucardio = new cardioTreeni(); // cardiotreeni tietojen kyselyyn
    private int kentta = 0;
    
	@Override
public void initialize(URL url, ResourceBundle arg1) {
	alusta();
	}
	
	
private void alusta() {
	edits1 = luoCardioKentat(gridCardio);
	    for (TextField edit : edits1)
	        if ( edit != null )
	            edit.setOnKeyReleased( e -> kasitteleMuutosCardioon((TextField)(e.getSource())));
	    panelCardio.setFitToHeight(true);
}


@FXML private void handleOK() {
    if ( cardioKohdalla != null && cardioKohdalla.getLaji().trim().equals("") ) {
        naytaVirhe("Liikaa tyhjiä kenttiä");
        return;
            }
    ModalController.closeStage(labelVirhe);
}
	

@FXML private void handleCancel() {
	cardioKohdalla = null;
	ModalController.closeStage(labelVirhe);
}

	  
private TextField edits1[]; 
private cardioTreeni cardioKohdalla;
	  
  
@Override
public cardioTreeni getResult() {
	return cardioKohdalla;
}

	
/**
 * Tyhjentään tekstikentät 
 * @param  edits tyhjennettävät kentät
 */
public void tyhjenna(TextField[] edits) {
    for (TextField edit: edits) 
        if ( edit != null ) edit.setText(""); 
}


private void naytaVirhe(String virhe) {
    if ( virhe == null || virhe.isEmpty() ) {
        labelVirhe.setText("");
        labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
    labelVirhe.setText(virhe);
    labelVirhe.getStyleClass().add("virhe");
}
    
       
/**
 * @param obj obj
 * @param oletus oletus
 * @return oletus
 */
public static int getFieldId(Object obj, int oletus) {
    if ( !( obj instanceof Node)) return oletus;
       Node node = (Node)obj;
       return Mjonot.erotaInt(node.getId().substring(1),oletus);
}
 
/**
 * Käsitellään jäseneen tullut muutos
 * @param edit muuttunut kenttä
 */
protected void kasitteleMuutosCardioon(TextField edit) {
    if (cardioKohdalla == null) return;
       int k = getFieldId(edit,apucardio.ekaKentta());
       String s = edit.getText();
       String virhe = null;
       
    virhe = cardioKohdalla.aseta(k,s); 
       if (virhe == null) {
           Dialogs.setToolTipText(edit,"");
           edit.getStyleClass().removeAll("virhe");
           naytaVirhe(virhe);
       } else {
           Dialogs.setToolTipText(edit,virhe);
           edit.getStyleClass().add("virhe");
           naytaVirhe(virhe);
       }
}
   

/**
 * @param gridcardioTreenit 
 * alustetaan gridpaneen kentätä cardiolle
 * @return edits taulukko
 */
public static TextField[] luoCardioKentat(GridPane gridcardioTreenit) {
    gridcardioTreenit.getChildren().clear();
    TextField[] edits = new TextField[apucardio.getKenttia()];
        
        for (int i=0, k = apucardio.ekaKentta(); k < apucardio.getKenttia(); k++, i++) {
            Label label = new Label(apucardio.getKysymys(k));
            gridcardioTreenit.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridcardioTreenit.add(edit, 1, i);
        }
        return edits;
    }
    

@Override
public void handleShown() {
	kentta = Math.max(apucardio.ekaKentta(), Math.min(kentta, apucardio.getKenttia()-1));
    edits1[kentta].requestFocus();
	}


@Override
public void setDefault(cardioTreeni oletus) {
	cardioKohdalla =oletus;
	naytaCardio(edits1,cardioKohdalla);	
	}
	
    private void setKentta(int kentta) {
	    this.kentta = kentta;
	}
	    

/** näyttää
 * @param edits1 taulukko
 * @param cardio treeni
 */
public static void naytaCardio(TextField[] edits1, cardioTreeni cardio) {
	for (int k = cardio.ekaKentta(); k < cardio.getKenttia(); k++) {
	    edits1[k].setText(cardio.anna(k));
	    }
}
 
	/**
	 * @param modalityStage mille modaalisia
	 * @param oletus oletus
	 * @param kentta mitä käsitellään
	 * @return kenttä
	 */
	public static cardioTreeni kysyCardio (Stage modalityStage, cardioTreeni oletus, int kentta) {
	        return ModalController.<cardioTreeni,
	                     CardioDialogController>showModal(CardioDialogController.class.getResource("CardioDialogView.fxml"),
	     	                    "Kuntorekisteri",
	     	                   modalityStage, oletus,
	     	                    ctrl -> ctrl.setKentta(kentta));
	    }
}
