 package fxkuntorekisteri;
 import fi.jyu.mit.fxgui.StringGrid;
 import java.io.PrintStream;
 import java.net.URL;

 import fi.jyu.mit.fxgui.ComboBoxChooser;
 import fi.jyu.mit.fxgui.Dialogs;
 import fi.jyu.mit.fxgui.ListChooser;
 import fi.jyu.mit.fxgui.ModalController;
 import fi.jyu.mit.fxgui.TextAreaOutputStream;
 import javafx.fxml.FXML;
 import javafx.fxml.Initializable;
 import javafx.scene.control.Label;
 import javafx.scene.control.ScrollPane;
 import javafx.scene.control.TextArea;
 import javafx.scene.control.TextField;
 import javafx.scene.control.TableView;
 import javafx.scene.input.KeyCode;
 
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;


import Kayttaja.Liike;
import Kayttaja.SailoException;
import Kayttaja.cardioTreeni;
import Kayttaja.SaliTreeni;
import Kayttaja.Kuntorekisteri;
import static fxkuntorekisteri.SalitreeniDialogController.getFieldID; 
/** Luokka kuntorekisterin käyttöliittymän tapahtumien hoitamiseksi.
 * @author venla
 * @version 24 Apr 2023
 *
 */
public class paavalikkoController implements Initializable {
    
    @FXML private TextField hakuehto;
    @FXML private TextField cardiohakuehto;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private ComboBoxChooser<String> cbcardioKentat;
    @FXML private Label labelVirhe;
    @FXML private Label labelLkm;
    @FXML private Label labelMaara;
    
    @FXML private ScrollPane panelSaliTreeni;
    @FXML private ListChooser<SaliTreeni> chooserTreenit;
    @FXML private GridPane gridSaliTreeni;
    
    @FXML private ScrollPane panelcardioTreeni;
    @FXML private GridPane gridcardioTreeni;
    @FXML private ListChooser<cardioTreeni> choosercardioTreenit;
    
    @FXML private StringGrid<Liike> tableLiikkeet;
    
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        }
    
    
    @FXML private void handleHakuehto() {
        if ( SaliTreeniKohdalla != null )
            hae(SaliTreeniKohdalla.getTunnusNro());
    }
    
    
    @FXML private void handleCardioHakuehto() {
       if ( CardioTreeniKohdalla != null )
           haecardio(CardioTreeniKohdalla.getTunnusNro());
    }
    
    
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    @SuppressWarnings("unused")
    @FXML void handleLisaaTreeni(ActionEvent event) {
            uusiSaliTreeni();
        }
    
 
    @SuppressWarnings("unused")
    @FXML void handlePoistaTreeni(ActionEvent event) {
        poistaSali();

    }

    
    @SuppressWarnings("unused")
    @FXML void handlePoistaCardio(ActionEvent event) {
        poistaCardio();

    }
    
    
    @FXML private void handleLisaaLiike() {
        uusiLiike();  
    }
    
    
    @SuppressWarnings("unused")
    @FXML void handleLisaaCardioTreeni(ActionEvent event) {
        uusiCardioTreeni();
    }
    
    
    @SuppressWarnings("unused")
    @FXML void handleMuokkaaCardio(ActionEvent event) {
        muokkaaCardio(cKentta);
    }
    
    
    @SuppressWarnings("unused")
    @FXML void handleMuokkaaTreenia(ActionEvent event) {
    muokkaaTreenia(kentta);
    }


/**
 * muokataan salitreenin tietoja
 * @param k
 */
private void muokkaaTreenia(int k) {
    if ( SaliTreeniKohdalla == null ) return; 
    try { 
        SaliTreeni salitreeni; 
        salitreeni = TietueDialogController.kysyTietue(null, SaliTreeniKohdalla.clone(), k);   
        if ( salitreeni == null ) return; 
        kuntorekisteri.korvaaTaiLisaa(salitreeni); 
        hae(salitreeni.getTunnusNro()); 
    } catch (CloneNotSupportedException e) { 
        // 
    } catch (SailoException e) { 
        Dialogs.showMessageDialog(e.getMessage()); 
    } 
}     


/**
 * muokataan cardiotreenin tietoja
 * @param k
 */
private void muokkaaCardio(int k) {
    if ( CardioTreeniKohdalla == null ) return; 
    try { 
        cardioTreeni cardiotreeni; 
        cardiotreeni = CardioDialogController.kysyCardio(null, CardioTreeniKohdalla.clone(), k); 
        if ( cardiotreeni == null ) return; 
        kuntorekisteri.korvaaTaiLisaa(cardiotreeni); 
        haecardio(cardiotreeni.getTunnusNro()); 
    } catch (CloneNotSupportedException e) { 
        // 
    } catch (SailoException e) { 
        Dialogs.showMessageDialog(e.getMessage()); 
    } 
}
    
    /**
     * lisätään liike treeniin
     */
    public void LisaaLiike() {
        if ( SaliTreeniKohdalla == null ) return;  
        Liike liike = new Liike();  
        liike.rekisteroi();  
        liike.vastaaLiike(SaliTreeniKohdalla.getTunnusNro());  
        try {
            kuntorekisteri.lisaa(liike);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia lisäämisessä! " + e.getMessage());
        } 
        hae(SaliTreeniKohdalla.getTunnusNro());  
    }


    @FXML private void handleMuokkaaLiikettä() {
        muokkaaLiiketta();
    }
    

    @FXML private void handlePoistaLiike() {
        poistaLiike();
    }
   
    
    /** 
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = KuntorekisteriController.kysyNimi(null, esimerkki);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /** testataan voiko sovelluksen sulkea
     * @return treu/false
     */
    public boolean voikoSulkea() {
      tallenna();
        return true;
    }


    /**
     * poistaa liikkeen tiedostosta
     */
     private void poistaLiike() {
            int rivi = tableLiikkeet.getRowNr();
            if ( rivi < 0 ) return;
            Liike liike = tableLiikkeet.getObject();
            if ( liike == null ) return;
            kuntorekisteri.poistaLiike(liike);
            naytaLiikkeet(SaliTreeniKohdalla);
            int liikkeita = tableLiikkeet.getItems().size(); 
            if ( rivi >= liikkeita ) rivi = liikkeita -1;
            tableLiikkeet.getFocusModel().focus(rivi);
            tableLiikkeet.getSelectionModel().select(rivi);
            naytaLiikkeidenMaara(SaliTreeniKohdalla);
        }


        /**
         * Poistetaan listalta valittu salitreeni
         */
        private void poistaSali() {
            SaliTreeni salitreeni = SaliTreeniKohdalla;
            if ( salitreeni == null ) return;
            if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko salitreeni: " + salitreeni.getNimi(), "Kyllä", "Ei") )
                return;
            kuntorekisteri.poistaSali(salitreeni);
            int index = chooserTreenit.getSelectedIndex();
            hae(0);
            chooserTreenit.setSelectedIndex(index);
        }

        
         /*
         * Poistetaan listalta valittu cardio
         */
       private void poistaCardio() {
            cardioTreeni cardio = CardioTreeniKohdalla;
            if ( cardio == null ) return;
            if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko cardiotreeni: " + cardio.getLaji(), "Kyllä", "Ei") )
                return;
            kuntorekisteri.poistaCardio(cardio);
            int index = choosercardioTreenit.getSelectedIndex();
            haecardio(0);
            choosercardioTreenit.setSelectedIndex(index);
        }

       
        private void setTitle(String title) {
            ModalController.getStage(hakuehto).setTitle(title);
        }
    
        
        /**
         * tulostus tapahtuu tästä
         * @param event
         */
       @FXML void handleTulosta(@SuppressWarnings("unused") ActionEvent event) {
               tulostusController.tulosta(null);
               tulostusController tulostusCtrl = tulostusController.tulosta(null); 
               tulostaValitut(tulostusCtrl.getTextArea()); 
       }
       
       
        private Kuntorekisteri kuntorekisteri;
        private SaliTreeni SaliTreeniKohdalla;
      //  private TextArea areaSaliTreeni = new TextArea();
        private TextField edits[]; 
        private TextField edits1[];
        private static SaliTreeni aputreeni =new SaliTreeni();
        private static cardioTreeni apucardio=new cardioTreeni();
        private cardioTreeni CardioTreeniKohdalla;
        //private TextArea areaCardioTreeni = new TextArea();
        private String esimerkki = "esimerkki";
        private int kentta = 0; 
        private int cKentta = 0; 
        private static Liike apuliike = new Liike(); 
        
        
        /**
         * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
         * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
         * Alustetaan myös jäsenlistan kuuntelija 
         */
        protected void alusta() {  
            chooserTreenit.clear();
            chooserTreenit.addSelectionListener(e -> naytaSaliTreeni());
            edits = TietueDialogController.luoKentat(gridSaliTreeni, new SaliTreeni());   
            for (TextField edit: edits)  
                if ( edit != null ) {  
                    edit.setEditable(false);  
                    edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaTreenia(getFieldID(e.getSource(),0)); });  
                    edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldID(edit,kentta));  
                    edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaTreenia(kentta);}); 
                } 

            choosercardioTreenit.clear();
            choosercardioTreenit.addSelectionListener(e -> naytaCardioTreeni());
            edits1 = CardioDialogController.luoCardioKentat(gridcardioTreeni); 
            for (TextField edit1: edits1)  
                if ( edit1 != null ) {  
                    edit1.setEditable(false);  
                    edit1.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaaCardio(getFieldID(e.getSource(),0)); });  
                    edit1.focusedProperty().addListener((a,o,n) -> cKentta = getFieldID(edit1,cKentta)); 
                    edit1.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaCardio(cKentta);}); 
                }    

            int eka = apuliike.ekaKentta(); 
            int lkm = apuliike.getKenttia(); 
            String[] headings = new String[lkm-eka]; 
            for (int i=0, k=eka; k<lkm; i++, k++) headings[i] = apuliike.getKysymys(k); 
            tableLiikkeet.initTable(headings); 
            tableLiikkeet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
           // tableLiikkeet.setEditable(false); 
            tableLiikkeet.setPlaceholder(new Label("Ei vielä liikkeitä")); 
 
            tableLiikkeet.setColumnSortOrderNumber(1); 
            tableLiikkeet.setColumnSortOrderNumber(2); 
            tableLiikkeet.setColumnWidth(1, 60); 
            
            tableLiikkeet.setColumnWidth(2, 60); 
            
            tableLiikkeet.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaLiiketta(); } );
            tableLiikkeet.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaLiiketta();}); 
        }
        
     
        /**
         * luetaan tieodostosta
         * @param nimi joka on tiedoto
         * @return virheen tai null
         */
        protected String lueTiedosto(String nimi) {
            esimerkki = nimi;
            setTitle("Kuntoilija - " + esimerkki);

            try {
                kuntorekisteri.lueTiedostosta(nimi);
                haecardio(0);
                hae(0);
                return null;
               
            } catch (SailoException e) {
                hae(0);
                haecardio(0);
                String virhe = e.getMessage(); 
                if ( virhe != null ) Dialogs.showMessageDialog(virhe);
                return virhe;
            }
         }

        
        /**
         * tallennetaan tiedostoon
         * @return
         */
        private String tallenna() {
            try {
                kuntorekisteri.tallenna();
                return null;
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
                return ex.getMessage();
            }
        }

        
        /**
         * Näyttää listasta valitun treenin tiedot, yhteen isoon edit-kenttään
         */
        protected void naytaSaliTreeni() {
            SaliTreeniKohdalla = chooserTreenit.getSelectedObject();
            if (SaliTreeniKohdalla == null) return;
            
            TietueDialogController.naytaTietue(edits, SaliTreeniKohdalla);  
            labelMaara.setText("Ei vielä liikkeitä");
            naytaLiikkeet(SaliTreeniKohdalla);
            naytaLkm();
            naytaLiikkeidenMaara(SaliTreeniKohdalla);
        }
        
        
        /**
         * Näyttää treenien lukumäärän
         */
        private void naytaLkm() {
           int lkm =  kuntorekisteri.getTreeneja();
           int lkm1 = kuntorekisteri.getCardioTreeneja();
           int summa = lkm + lkm1;
            
            String s=Integer.toString(summa);  
            labelLkm.setText(s);
        }
        
        
        /**
         * naytetaan salitreenin liikeiden maara
         * @param salitreeni
         */
        private void naytaLiikkeidenMaara(SaliTreeni salitreeni) {
        int lkm  = liikkeidenMaara(salitreeni);
                if (lkm == 0) labelMaara.setText("Ei vielä liikkeitä");
                String s=Integer.toString(lkm);  
                labelMaara.setText(s);
        }
        
        
        /**
         * lasketaan salitreenin liikkeiden määrä
         * @param salitreeni josta n lasketaan
         */
        private int liikkeidenMaara(SaliTreeni salitreeni) {
            int lkm = 0;
            List<Liike> liikkeet = kuntorekisteri.annaLiike(salitreeni);
            for (Liike liike : liikkeet) {
                if ( liike == null ) lkm = lkm + 0 ;
                lkm = lkm + 1;
            }
            return lkm;
        }
                
            
         /**
          * naytetaan salitreenia vastaavat liike
          * @param salitreeni
          */
        private void naytaLiikkeet(SaliTreeni salitreeni) {
            tableLiikkeet.clear();
            if ( salitreeni == null ) return;
            
            List<Liike> liikkeet = kuntorekisteri.annaLiike(salitreeni);
            if ( liikkeet.size() == 0 ) return;
            for (Liike liike: liikkeet)
                naytaLiike(liike); 
            naytaLiikkeidenMaara(salitreeni);
            
            if (kuntorekisteri.getTreeneja() >20) chooserTreenit.setStyle("-fx-background-color: green;");
            if (kuntorekisteri.getCardioTreeneja()> 20) choosercardioTreenit.setStyle("-fx-background-color: green;");
            
        }

        
        /**
         * naytetaan salitreeniavastaava loiike 
         * @param liike
         */
        private void naytaLiike(Liike liike) {
            int kenttia = liike.getKenttia(); 
            String[] rivi = new String[kenttia-liike.ekaKentta()]; 
            for (int i=0, k=liike.ekaKentta(); k < kenttia; i++, k++) 
                rivi[i] = liike.anna(k); 
            tableLiikkeet.add(liike,rivi);
        }

        
        /**
         * näytetään haluttu cardiotreeni oikeassa kohtaa
         */
        protected void naytaCardioTreeni() {
            CardioTreeniKohdalla = choosercardioTreenit.getSelectedObject();
            if (CardioTreeniKohdalla == null) return;
            
            CardioDialogController.naytaCardio(edits1, CardioTreeniKohdalla);
            naytaLkm();
        }

        
        /**
         * näytetään jos tulee virhe ilmoitus siitöä
         * @param virhe
         */
        @SuppressWarnings("unused")
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
         * Hakee treenin tiedot listaan
         * @param jnro jäsenen numero, joka aktivoidaan haun jälkeen
         */
        protected void hae(int jnro) {
            int tnr=jnro;
            if (tnr<=0) {
                SaliTreeni kohdalla =SaliTreeniKohdalla;
                if(kohdalla != null) tnr =kohdalla.getTunnusNro();
            }
            int k = cbKentat.getSelectionModel().getSelectedIndex()+aputreeni.ekaKentta();
            String ehto = hakuehto.getText(); 
            if (ehto.indexOf('*')< 0) ehto= "*"+ehto+"*";
           
            chooserTreenit.clear();
     

            int index = 0;
            
            Collection<SaliTreeni> Treenit;;
            try {
                Treenit = kuntorekisteri.etsi(ehto, k);
                int i = 0;
                for (SaliTreeni salitreeni: Treenit) {
                    if (salitreeni.getTunnusNro() == jnro) index = i;
                    chooserTreenit.add(salitreeni.getNimi(), salitreeni);
                    i++;
                }
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("treenin hakemisessa ongelmia! " + ex.getMessage());

            }
            chooserTreenit.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
        }
        
        
        /**
         * Hakee cardio treenientiedot listaan
         * @param jnro cardiotreenin numero, joka aktivoidaan haun jälkeen
         */
        protected void haecardio(int jnro) {
            int tnr=jnro;
            if (tnr<=0) {
                cardioTreeni kohdalla =CardioTreeniKohdalla;
                if(kohdalla != null) tnr =kohdalla.getTunnusNro();
            }
            int k = cbcardioKentat.getSelectionModel().getSelectedIndex()+apucardio.ekaKentta();
            String ehto = cardiohakuehto.getText(); 
            if (ehto.indexOf('*')< 0) ehto= "*"+ehto+"*";
        
            choosercardioTreenit.clear();

            int index = 0;
            
            Collection<cardioTreeni> cardiot;;
            try {
                cardiot = kuntorekisteri.etsicardio(ehto, k);
                int i = 0;
                for (cardioTreeni cardiotreeni: cardiot) {
                    if (cardiotreeni.getTunnusNro() == jnro) index = i;
                    choosercardioTreenit.add(cardiotreeni.getLaji(), cardiotreeni);
                    i++;
                }
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("treenin hakemisessa ongelmia! " + ex.getMessage());

            }
            choosercardioTreenit.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
        }


        /**
         * Luo uuden jäsenen
         */
        protected void uusiSaliTreeni() {
            try {
                SaliTreeni uusi = new SaliTreeni();
                uusi = TietueDialogController.kysyTietue(null, uusi, 1);    
                if ( uusi == null ) return;
                uusi.rekisteroi();
                kuntorekisteri.lisaa(uusi);
                hae(uusi.getTunnusNro());

            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
            }
        }
        
        
        /**
         * luodaan uusi cardiotreeni 
         */
        protected void uusiCardioTreeni() {
            try {
                cardioTreeni uusi = new cardioTreeni();
                uusi = CardioDialogController.kysyCardio(null, uusi, 1);  
                if ( uusi == null ) return;
                uusi.rekisteroi();
                kuntorekisteri.lisaa(uusi);
                haecardio(uusi.getTunnusNro());
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
            }

        }
        
        /**
         * Tekee uuden tyhjän harrastuksen editointia varten
         */
        @SuppressWarnings("unused")
        private void uusiLiike() {
            if ( SaliTreeniKohdalla == null ) return;
            try {
                Liike uusi = new Liike(SaliTreeniKohdalla.getTunnusNro());
                uusi = TietueDialogController.kysyTietue(null, uusi, 1);
                if ( uusi == null ) return;
                uusi.rekisteroi();
                kuntorekisteri.lisaa(uusi);
                naytaLiikkeet(SaliTreeniKohdalla); 
                tableLiikkeet.selectRow(1000);
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());
            }

        }

        
        /**
         * muokataan haluttua liikettä
         */
        private void muokkaaLiiketta() {
            int r = tableLiikkeet.getRowNr();
            if ( r < 0 ) return; 
            Liike liike = tableLiikkeet.getObject();
            if ( liike == null ) return;
            int k = tableLiikkeet.getColumnNr()+liike.ekaKentta();
            try {
                liike = TietueDialogController.kysyTietue(null, liike.clone(), k);
                if ( liike == null ) return;
                kuntorekisteri.korvaaTaiLisaa(liike); 
                naytaLiikkeet(SaliTreeniKohdalla); 
                tableLiikkeet.selectRow(r);  // järjestetään sama rivi takaisin valituksi
            } catch (CloneNotSupportedException  e) { /* clone on tehty */  
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
            }
        }

        
        /**
         * @param kuntorekisteri rekisteri
         */
        public void setKuntorekisteri(Kuntorekisteri kuntorekisteri) {
            this.kuntorekisteri = kuntorekisteri;
            naytaSaliTreeni();
            naytaCardioTreeni();
        }

        
        /**
         * Tulostaa treenin tiedot
         * @param os tietovirta johon tulostetaan
         * @param salitreeni tulostettava salitreeni
         */
        public void tulosta(PrintStream os, final SaliTreeni salitreeni) {
            os.println("----------------------------------------------");
            salitreeni.tulosta(os);
            os.println("----------------------------------------------");
            
            List<Liike> liikkeet = kuntorekisteri.annaLiike(salitreeni);
            for (Liike liike : liikkeet) 
                liike.tulosta(os); 
           

        }
        
        
         /**
         * Tulostaa cardion tiedot
         * @param os tietovirta johon tulostetaan
         * @param cardiotreeni tulostettava cardio
         */
        public void tulosta(PrintStream os, final cardioTreeni cardiotreeni) {
            os.println("----------------------------------------------");
            cardiotreeni.tulosta(os);
            os.println("----------------------------------------------");
           
        }
        
        
        /**
         * Tulostaa listassa olevat jäsenet tekstialueeseen
         * @param text alue johon tulostetaan
         */
        public void tulostaValitut(TextArea text) {
            try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
                os.println("Tulostetaan kaikki treenit");
                
                //Collection<SaliTreeni>Treenit = kuntorekisteri.etsi("", -1); 
                for (SaliTreeni salitreeni : chooserTreenit.getObjects()) { 

                    tulosta(os, salitreeni);
                    os.println("\n\n");
                }
                
                Collection<cardioTreeni>cardio = kuntorekisteri.etsicardio("", -1); 
                for (cardioTreeni cardiotreeni : cardio) { 

                    tulosta(os, cardiotreeni);
                    os.println("\n\n");
                }
            } catch (SailoException ex) { 
                Dialogs.showMessageDialog("Treenin hakemisessa ongelmia! " + ex.getMessage()); 
        }
}}
