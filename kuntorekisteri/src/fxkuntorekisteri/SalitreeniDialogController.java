package fxkuntorekisteri;

    import java.net.URL;
    import java.util.ResourceBundle;

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
    import Kayttaja.SaliTreeni;
    import fi.jyu.mit.fxgui.Dialogs;

    /**
     * Kysytään salitreeninn tiedot luomalla sille uusi dialogi
     * 
     * @author vesal
     * @version 11.1.2016
     *
     */
    public class SalitreeniDialogController implements ModalControllerInterface<SaliTreeni>,Initializable  {

      /**  @FXML private TextField editNimi;
        @FXML private TextField editPvm;
        @FXML private TextField editFiilis;*/
    	@FXML private ScrollPane panelSalitreenit;
    	@FXML private GridPane gridSalitreeni;
        @FXML private Label labelVirhe;
        

        
        @Override
        public void initialize(URL url, ResourceBundle bundle) {
            alusta();  
        }
        
        
        @FXML private void handleOk() {
            if ( SaliTreeniKohdalla != null && SaliTreeniKohdalla.getNimi().trim().equals("") ) {
                naytaVirhe("Nimi ei saa olla tyhjä");
                return;
            }
            ModalController.closeStage(labelVirhe);
        }

        
        @FXML private void handleCancel() {
            ModalController.closeStage(labelVirhe);
            SaliTreeniKohdalla = null;
        }

    // ========================================================    
        private SaliTreeni SaliTreeniKohdalla;
        private TextField edits[];
        private static SaliTreeni aputreeni=new SaliTreeni();
        private int kentta=0;
        
        
        
        /**
         * @param gridSaliTreeni jota käsitellään
         * @return taulukko
         */
        public static TextField[] luoKentat(GridPane gridSaliTreeni) {
        	gridSaliTreeni.getChildren().clear();
        	TextField[] edits=new TextField[aputreeni.getKenttia()];
        	for(int i=0, k=aputreeni.ekaKentta();k< aputreeni.getKenttia();k++,i++) {
        	Label label =new Label(aputreeni.getKysymys(k));
        	gridSaliTreeni.add(label, 0, i);
        	TextField edit = new TextField();
        	edits[k]=edit;
        	edit.setId("e"+k);
        	gridSaliTreeni.add(edit,1,i);
        	
        	}
        	return edits;
        			
        }

        /**
         * Tyhjentään tekstikentät 
         * @param edits1 taulukko
         */
        public void tyhjenna(TextField[] edits1) {
            for (TextField edit : edits1)
             if(edit!=null)edit.setText("");
        }


        /**
         * Tekee tarvittavat muut alustukset.
         */
        protected void alusta() {
        	edits=luoKentat(gridSalitreeni);
        	for(TextField edit:edits)
        		if(edit!= null)edit.setOnKeyReleased(e -> kasitteleMuutosTreeniin((TextField)(e.getSource())));
        	panelSalitreenit.setFitToHeight(true);

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
         * @param obj mistä
         * @param oletus oletus
         * @return oletus
         */
        public static int getFieldID(Object obj, int oletus) {
        	if (!(obj instanceof Node))return oletus;
        	Node node=(Node)obj;
        	return Mjonot.erotaInt(node.getId().substring(1), oletus);
        }
        

        
        
        /**
         * Käsitellään jäseneen tullut muutos
         * @param edit muuttunut kenttä
         */
        protected void kasitteleMuutosTreeniin( TextField edit) {
            if (SaliTreeniKohdalla == null) return;
            int i= getFieldID(edit,aputreeni.ekaKentta());
            String s = edit.getText();
            String virhe = null;
      
            virhe=SaliTreeniKohdalla.aseta(i,s);
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
        

        
        @Override
        public void setDefault(SaliTreeni oletus) {
            SaliTreeniKohdalla = oletus;
            naytaSaliTreeni(edits, SaliTreeniKohdalla);
        }

        
        @Override
        public SaliTreeni getResult() {
            return SaliTreeniKohdalla;
        }
        
        
        /**
         * Mitä tehdään kun dialogi on näytetty
         */
        @Override
        public void handleShown() {
        	kentta=Math.max(aputreeni.ekaKentta(), Math.min(kentta, aputreeni.getKenttia()-1));
        	edits[kentta].requestFocus();
            //editNimi.requestFocus();
        }
        
        
        /**
         * Näytetään treeninn tiedot TextField komponentteihin
         * @param edits taulukko
         * @param salitreeni näytettävä salitreeni
         */
        public static void naytaSaliTreeni(TextField[] edits, SaliTreeni salitreeni) {
            if (salitreeni == null) return;
            
            /**edits[0].setText(salitreeni.getNimi());
            edits[1].setText(salitreeni.getPvm());
            edits[2].setText(salitreeni.getFiilis());*/
            for (int k=salitreeni.ekaKentta(); k< salitreeni.getKenttia(); k++){
            	edits[k].setText(salitreeni.anna(k));
            }
            
      

        }
        
        
        /**
         * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
         * TODO: korjattava toimimaan
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä dataan näytetään oletuksena
         * @param kentta jotka käsitellään
         * @return null jos painetaan Cancel, muuten täytetty tietue
         */
        public static SaliTreeni kysySaliTreeni(Stage modalityStage, SaliTreeni oletus, int kentta) {
            
        	return ModalController.<SaliTreeni,SalitreeniDialogController>showModal(
                    SalitreeniDialogController.class.getResource("SalitreeniDialogView.fxml"),
                    "Kuntorekisteri",
                    modalityStage, oletus,
                    ctrl -> ctrl.setKentta(kentta)
                );
        	

        }
        
        private void setKentta(int kentta) {
        	this.kentta=kentta;
        }

    }


