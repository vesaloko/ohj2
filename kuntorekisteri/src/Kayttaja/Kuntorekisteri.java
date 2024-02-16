package Kayttaja;

import java.util.Collection;
import java.util.List;
import java.io.File;


/**
 * Kuntoreksiteri luokka  joka huolehtii cardio, sali ja liike luokistya
 * @author taruk
 *
 *
 * * Testien alustus
 * @example
 * <pre name="testJAVA">
 * #import Kanta.SailoException;
 *  private Kuntorekisteri KR;
 *  private SaliTreeni sali1;
 *  private SaliTreeni sali2;
 *  private int jid1;
 *  private int jid2;
 *  private Liike liike1;
 *  private Liike liike2;
 *  private Liike liike3; 
 *  
 *  // @SuppressWarnings("javadoc")
 *  public void alustaKuntorekisteri() {
 *    KR = new Kuntorekisteri();
 *    sali1 = new SaliTreeni(); sali1.vastaaTreeniSali(); sali1.rekisteroi();
 *    sali2 = new SaliTreeni(); sali2.vastaaTreeniSali(); sali2.rekisteroi();
 *    jid1 = sali1.getTunnusNro();
 *    jid2 = sali2.getTunnusNro();
 *    liike1 = new Liike(jid2); liike1.vastaaLiike(jid2);
 *    liike2 = new Liike(jid1); liike2.vastaaLiike(jid1);
 *    liike3 = new Liike(jid2); liike3.vastaaLiike(jid2); 
 *    try {
 *    KR.lisaa(sali1);
 *    KR.lisaa(sali2);
 *    KR.lisaa(liike1);
 *    KR.lisaa(liike2);
 *    KR.lisaa(liike3);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
*/
public class Kuntorekisteri {
	private  Liikkeet liikkeet =new Liikkeet();
	private  cardioTreenit cardioTreenit = new cardioTreenit();
    private  Treenit treenit =new Treenit();
 
    
    /**
     * palauttaa cardio treenien määrän
     * @return cardiot
     */
    public int getCardioTreeneja(){
    	return cardioTreenit.getLkm();
    }
    
    
    /**
     * Plaauttaa salitreenien määrän
     * @return salitreenit
     */
    public int getTreeneja(){
    	return treenit.getLkm();
    }
    
    
    /**
     * Plaauttaa salitreenien määrän
     * @return salitreenit
     */
    public int getLiikkeita(){
        return liikkeet.getLkm();
    }
    
    
    /**
     * poistaa treenistöstä ja cardioista teityn numeroin mukaan
     * @param salitreeni viitenumero jonka mukaan poistaa
     * @return montako poistettiin
     */
    public int poistaSali (SaliTreeni salitreeni) {
        if ( salitreeni == null ) return 0;
        int ret = treenit.poista(salitreeni.getTunnusNro()); 
        liikkeet.poistaSalitreeninLiikkeet(salitreeni.getTunnusNro()); 
        return ret; 
    }
    
    
    /** poistetaan käsiteltävä treeni
     * @param cardio käsiteltävä
     * @return ret
     */
    public int poistaCardio(cardioTreeni cardio) {
        if ( cardio == null ) return 0;
        int ret = cardioTreenit.poista(cardio.getTunnusNro()); 
        return ret; 
    }

    
    /** poistetaan liike
     * @param liike käsiteltävä
     */
    public void poistaLiike(Liike liike) { 
        liikkeet.poista(liike); 
    }

    
    /**
     * Lisaa uuden salitreenin
     * @param SaliTreeni lisättävä treeni
     * @throws SailoException jos lisäys ei onnistu
     *  * <pre name="test">
     * #THROWS SailoException
     * Kuntorekisteri kuntorekisteri = new Kuntorekisteri();
     * SaliTreeni selka = new SaliTreeni(), jalka = new SaliTreeni();
     * selka.rekisteroi(); jalka.rekisteroi();
     * kuntorekisteri.getTreeneja() === 0;
     * kuntorekisteri.lisaa(selka); kuntorekisteri.getTreeneja() === 1;
     * kuntorekisteri.lisaa(jalka); kuntorekisteri.getTreeneja() === 2;
     * kuntorekisteri.lisaa(selka); kuntorekisteri.getTreeneja() === 3;
     * kuntorekisteri.getTreeneja() === 3;
     * kuntorekisteri.annaSaliTreeni(0) === selka;
     * kuntorekisteri.annaSaliTreeni(1) === jalka;
     * kuntorekisteri.annaSaliTreeni(2) === selka;
     * kuntorekisteri.annaSaliTreeni(3) === selka; #THROWS IndexOutOfBoundsException 
     * kuntorekisteri.lisaa(selka); kuntorekisteri.getTreeneja() === 4;
     * kuntorekisteri.lisaa(jalka); kuntorekisteri.getTreeneja() === 5;
     * </pre>
     */
    public void lisaa(SaliTreeni SaliTreeni)throws SailoException {
        treenit.lisaa(SaliTreeni);
    }
    
    /**
     * Lisaa uuden cardion
     * @param cardioTreeni lisätään
     * @throws SailoException jos ei
     */
    public void lisaa(cardioTreeni cardioTreeni)throws SailoException {
        cardioTreenit.lisaa(cardioTreeni);
    }
    
    
    /**
     * @param liike liike
     * @throws SailoException jos ei
     */
    public void lisaa(Liike liike) throws SailoException {
        liikkeet.lisaa(liike);
    }

    
    /** 
     * Korvaa salitreenin tietorakenteessa.  Ottaa salitreenin omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva sali.  Jos ei löydy, 
     * niin lisätään uutena salinna. 
     * @param salitreeni lisätäävän salin viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(SaliTreeni salitreeni) throws SailoException { 
        treenit.korvaaTaiLisaa(salitreeni); 
    } 

    
    /** 
     * Korvaa jäsenen tietorakenteessa.  Ottaa cardion omistukseensa. 
     * Etsitään samalla tunnusnumerolla oleva cardio.  Jos ei löydy, 
     * niin lisätään uutena treeninä 
     * @param cardiotreeni lisätäävän treenin viite.  Huom tietorakenne muuttuu omistajaksi 
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(cardioTreeni cardiotreeni) throws SailoException { 
        cardioTreenit.korvaaTaiLisaa(cardiotreeni); 
    } 
    
    
    /** korvaa liikkeen tietorakenteessa / lisää uuden
     * @param liike jota käsitellään
     * @throws SailoException jos ei
     */
    public void korvaaTaiLisaa(Liike liike) throws SailoException { 
        liikkeet.korvaaTaiLisaa(liike); 
    } 

    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<SaliTreeni> etsi(String hakuehto, int k) throws SailoException { 
        return treenit.etsi(hakuehto, k); 
    } 
    
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<cardioTreeni> etsicardio(String hakuehto, int k) throws SailoException { 
        return cardioTreenit.etsi(hakuehto, k); 
    } 
    

    
    /**
     * Palauttaa i:n treenin
     * @param i monesko trteeni palautetaan
     * @return viite i:teen treeniin
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public SaliTreeni annaSaliTreeni(int i) throws IndexOutOfBoundsException {
        return treenit.anna(i);
    }
    
    /**
     * Palauttaa i:n treenin
     * @param i monesko trteeni palautetaan
     * @return viite i:teen treeniin
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public cardioTreeni annaCardioTreeni(int i) throws IndexOutOfBoundsException {
        return cardioTreenit.anna(i);
    }
    
    
    /**
     * @param salitreeni käsiteltävö
     * @return liikkeet
     */
    public List<Liike> annaLiike(SaliTreeni salitreeni) {
        return liikkeet.anna(salitreeni.getTunnusNro());
    }

    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        treenit.setTiedostonPerusNimi(hakemistonNimi + "salitreenit");
        liikkeet.setTiedostonOgNimi(hakemistonNimi + "liikkeet");
    }

    

    /**
     * Lukee päiväkirjan/kuntorekisterin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {  
        treenit = new Treenit(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        liikkeet = new Liikkeet();
        cardioTreenit = new cardioTreenit();

        setTiedosto(nimi);
        treenit.lueTiedostosta();
        liikkeet.lueTiedostosta();
        cardioTreenit.lueTiedostosta();

    }
    
    /**
     * Tallettaa kerhon tiedot tiedostoon
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void tallenna() throws SailoException {

            String virhe = "";
            try {
                treenit.tallenna();
            } catch ( SailoException ex ) {
                virhe = ex.getMessage();
            }

            try {
                liikkeet.talleta();
            } catch ( SailoException ex ) {
                virhe += ex.getMessage();
            }
            if ( !"".equals(virhe) ) throw new SailoException(virhe);
            
            
            try {
                cardioTreenit.tallenna();
            } catch ( SailoException ex ) {
                virhe = ex.getMessage();
            }
        }

    
    /**
     * Testiohjelma kerhosta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kuntorekisteri kuntorekisteri = new Kuntorekisteri();

        try {
            cardioTreeni juoksu= new cardioTreeni();
            juoksu.rekisteroi();
            juoksu.vastaaCardioTreeni();
            
            kuntorekisteri.lisaa(juoksu);
           
            SaliTreeni jalka = new SaliTreeni(), selka = new SaliTreeni();
            jalka.rekisteroi();
            jalka.vastaaTreeniSali();
            selka.rekisteroi();
            selka.vastaaTreeniSali();

            kuntorekisteri.lisaa(jalka);
            kuntorekisteri.lisaa(selka);
            
            int id1 = jalka.getTunnusNro();
            int id2 = selka.getTunnusNro();

            Liike liike1 = new Liike(id1);
            liike1.vastaaLiike(id1);
            kuntorekisteri.lisaa(liike1);
            Liike liike2 = new Liike(id2);
            liike2.vastaaLiike(id2);
            kuntorekisteri.lisaa(liike2);
            Liike liike3 = new Liike(id2);
            liike3.vastaaLiike(id2);
            kuntorekisteri.lisaa(liike3);
            Liike liike4 = new Liike(id1);
            liike4.vastaaLiike(id1);
            kuntorekisteri.lisaa(liike4);
      
            System.out.println("============= kuntorekisterin testi =================");

            Collection<SaliTreeni> treenit = kuntorekisteri.etsi("", -1);
            int i = 0;
            for (SaliTreeni salitreeni: treenit) {
                System.out.println("Treeni: " + i);
                salitreeni.tulosta(System.out);
                
                List<Liike> loytyneet = kuntorekisteri.annaLiike(salitreeni);
                for (Liike liike : loytyneet)
                    liike.tulosta(System.out);
                i++;
                
                System.out.println("                                            ");
               
                Collection<cardioTreeni> cardio = kuntorekisteri.etsicardio("", -1);
                int j = 0;
                for (cardioTreeni cardiotreeni: cardio) {
                    System.out.println("cardiotreeni: " + j);
                    cardiotreeni.tulosta(System.out);
                    j++;
                }
            }    
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
