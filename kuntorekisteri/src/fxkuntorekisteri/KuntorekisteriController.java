package fxkuntorekisteri;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
/**
 * @author venla
 * @version 15 Feb 2023
 *
 */
public class KuntorekisteriController implements ModalControllerInterface<String> {
    @FXML private TextField textVastaus;
    private String vastaus = null;


@SuppressWarnings("unused")
@FXML void handleJatka(ActionEvent event) {
        vastaus = textVastaus.getText();
        ModalController.closeStage(textVastaus);
    }
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                KuntorekisteriController.class.getResource("KuntorekisteriView.fxml"),
                "Kuntorekisteri",
                modalityStage, oletus);
    }


    @Override
    public String getResult() {
        return vastaus;
    }


    @Override
    public void handleShown() {
      textVastaus.requestFocus();
        
    }

    
    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
        
    }
}

