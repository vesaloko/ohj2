package fxkuntorekisteri;


import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;

/**
 * Tulostuksen hoitava luokka
 * 
 * @author vesal
 * @version 4.1.2016
 */
public class tulostusController implements ModalControllerInterface<String> {
    @FXML TextArea tulostusAlue;
    
    @FXML private void handleOK() {
        ModalController.closeStage(tulostusAlue);
    }

    
    @FXML private void handleTulosta() {
        PrinterJob job = PrinterJob.getPrinterJob();
        if (job != null && job.printDialog()) {
            WebEngine webEngine = new WebEngine();
            webEngine.loadContent("<pre>" + tulostusAlue.getText() + "</pre>");
            job.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex != 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                graphics.drawString(tulostusAlue.getText(), 72, 72); // assuming 72 DPI
                return Printable.PAGE_EXISTS;
            });
            try {
                job.print();
            } catch (PrinterException e) {
                Dialogs.showMessageDialog("Tulostaminen epäonnistui: " + e.getMessage());
            }
        }
    }

   
    @Override
    public String getResult() {
        return null;
    } 

    
    @Override
    public void setDefault(String oletus) {
        // if ( oletus == null ) return;
        tulostusAlue.setText(oletus);
    }

    
    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        //
    }
    
    
    /**
     * @return alue johon tulostetaan
     */
    public TextArea getTextArea() {
        return tulostusAlue;
    }
    
    
    /**
     * Näyttää tulostusalueessa tekstin
     * @param tulostus tulostettava teskti
     * @return kontrolleri, jolta voidaan pyytää lisää tietoa
     */
    public static tulostusController tulosta(String tulostus) {
        tulostusController tulostusCtrl = 
          ModalController.showModeless(tulostusController.class.getResource("Tulostus.fxml"),
                                       "Tulostus", tulostus);
        return tulostusCtrl;
    }

}

