package Kayttaja;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;
import Kanta.PvmTarkistus;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author venla
 * @version 1 Mar 2023
 * cardiotreenin luokka
 */
public class cardioTreeni implements Cloneable {
    private int tunnusNro;
    private String laji = "";
    private String pvm = "";
    private String matka = "";
    private String aika = "";
    private String fiilis = "";
    
    private static int seuraavaNro    = 1;
    
    

   /** vertailija, asettaa järjestyksen
 * @author venla
 * @version 24 Apr 2023
 *
 */
public static class Vertailija implements Comparator <cardioTreeni> {
	   private int k;
	   /**
	 * @param k vertaaa
	 */
	public Vertailija(int k) { 
           this.k = k; 
       } 
	
	
        @Override
       public int compare(cardioTreeni cardio1, cardioTreeni cardio2) { 
           return cardio1.getAvain(k).compareToIgnoreCase(cardio2.getAvain(k)); 
       } 
   } 

   
   /**
 * @param k kenttä
 * @return avain
 */
public String getAvain(int k) {
		switch (k) {
		case 0: return "" +tunnusNro;
		case 1: return "" +laji.toUpperCase();
		case 2: return "" +pvm;
		case 3: return "" +matka;
		case 4: return "" +aika;
		case 5: return "" +fiilis; 
		default: return "ok";
		}
		
	} 
    
   
    /**
     * Palauttaa jäsenen kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 6;
    }


	/**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return eknn kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    

    /**
     * @param hk kenttä
     * @return mita annetaan
     */
    public String anna(int hk) {
        switch(hk) {
     case 0: return "" + tunnusNro;
        case 1: return "" + laji;
        case 2: return "" + pvm;
        case 3: return "" + matka;
        case 4: return "" + aika;
        case 5: return "" + fiilis;
        default: return "En anna";
        }
    }
    
    
    /**
     * @param k jäsenelle laitettava katuosoite
     * @return virheilmoitus, null jos ok
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @example
     * <pre name="test">
     *   cardioTreeni car = new cardioTreeni();
     *   car.aseta(1,"juoksu") === null;
     *   car.aseta(2,"juoksu", "12") =R= "Päivämäärä on liian lyhyt"
     *   car.aseta(2,"juoksu", "12.12.2022") === null;
     *   car.aseta(2,"pyöräily", "12.12.2022", "12", "12 x 3") === null; 
     * </pre>
     */

    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            laji = tjono;
            return null;
        case 2:
            PvmTarkistus Paivamaara = new PvmTarkistus();
            String virhe = Paivamaara.tarkista(tjono);
            if(virhe!= null)return virhe;
            pvm=tjono;
            return null;

        case 3:
            matka = tjono;
            return null;
        case 4:
            aika = tjono;
            return null;
        case 5:
            fiilis = tjono;
            return null;
        default:
            return "Virhe";
        }
    }


    /**
     * @param k numero 
     * @return kysysmys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "Laji";
        case 2: return "Päivämäärä";
        case 3: return "Matka";
        case 4: return "Aika";
        case 5: return "Fiilis";
        default: return "virhe";
        }
    }


    /**
     * @param s jäsenelle laitettava nimi
     * @return virheilmoitus, null jos ok
     */
    public String setLaji(String s) {
        laji = s;
        return null;
    }

    
    
    /**
     * @param s jäsenelle laitettava hetu
     * @return virheilmoitus, null jos ok
    */
    public String setPvm(String s) {
        pvm = s;
        return null;
    } 

    
    /**
     * @param s jäsenelle laitettava katuosoite
     * @return virheilmoitus, null jos ok
     */
    public String setMatka(String s) {
        matka = s;
        return null;
    }
    
    /**
     * @param s jäsenelle laitettava katuosoite
     * @return virheilmoitus, null jos ok
    */
    public String setAika(String s) {
        aika = s;
        return null;
    } 
    
    
    /**
     * @param s jäsenelle laitettava katuosoite
     * @return virheilmoitus, null jos ok
     */
    public String setFiilis(String s) {
        fiilis = s;
        return null;
    }

    

    /**
     * @return treenin laji
     * @example
     * <pre name="test">
     *   cardioTreeni juoksu = new cardioTreeni();
     *   juoksu.vastaaCardioTreeni();
     *   juoksu.getLaji() =R= "juoksu .*";
     * </pre>
     */
    public String getLaji() {
        return laji;
    }
   
    
    /**
     * 
     */
    /*
     * Apumetodi, jolla saadaan täytettyä testiarvot treenille.
     */
    public void vastaaCardioTreeni(){
        laji = "juoksu";
        pvm = "1.1.2022";
        matka = "5.1";
        aika = "37";
        fiilis = "hyvä";
        
    }
    
 
    /**
     * Tulostetaan liikkeen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3));
        out.println(laji);
        out.println(pvm);
        out.println("matka " + matka);
        out.println("aika " + aika);
        out.print(fiilis);
    }

    /**
     * Tulostetaan liikkeen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Antaa cardiotreenille seuraavan rekisterinumeron.
     * @return uusi tunnusNro
     * @example
     * <pre name="test">
     *   cardioTreeni juoksu = new cardioTreeni();
     *   juoksu.getTunnusNro() === 0;
     *   juoksu.rekisteroi();
     *   cardioTreeni juoksu1 = new cardioTreeni();
     *   juoksu1.rekisteroi();
     *   int n1 = juoksu.getTunnusNro();
     *   int n2 = juoksu1.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    

    /**
     * Palauttaa cardiotreenin tunnusnumeron.
     * @return jcardiotreenin tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
 
    
    /**
     * Asetetaan seuraava tunnusnumero aina suuremmaksi kuin edellinen
     * @param numero
     */
    private void setTunnusNro(int numero) {
    	tunnusNro=numero;
    	if(tunnusNro>=seuraavaNro)seuraavaNro=tunnusNro+1;
    }
    
    
    /**
     * Palauttaa cardion tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   cardioTreeni car = new cardioTreeni();
     *   car.parse("   3  |  juoksu   | 12.12.2022");
     *   car.toString().startsWith("3|juoksu|012.12.2022|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
       StringBuffer sb = new StringBuffer("");
       String erotin = "";
       for (int k = 0; k < getKenttia(); k++) {
           sb.append(erotin);
           sb.append(anna(k));
           erotin = "|";
       }
       return sb.toString();
   }

   
    /**
     * Selvitää cardion tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta cardion tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   cardioTreeni car = new cardioTreeni();
     *   car.parse("   3  |  juoksu   | 12.12.2022");
     *   car.getTunnusNro() === 3;
     *   car.toString().startsWith("3|juoksu|12.12.2022|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   car.rekisteroi();
     *   int n = car.getTunnusNro();
     *   car.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   car.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   car.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse( String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }


    /**
     * Tehdään identtinen klooni treenista
     * @return Object kloonattu treeni
     * @example
     * <pre name="test">
     * </pre>
     */
    @Override
    public cardioTreeni clone() throws CloneNotSupportedException {
        cardioTreeni uusi;
        uusi = (cardioTreeni) super.clone();
        return uusi;
    }


        /**
         * Tutkii onko cardion tiedot samat kuin parametrina tuodun cardion tiedot
         * @param cardiotreeni cardio johon verrataan
         * @return true jos kaikki tiedot samat, false muuten
         * @example
         * <pre name="test">
         *   cardioTreeni c1 = new cardioTreeni();
         *   c1.parse("   3  | juoksu   | 12.12.2022");
         *   cardioTreeni c2 = new cardioTreeni();
         *   c2.parse("   3  |  juoksu   | 12.12.2022");
         *   cardioTreeni c3 = new cardioTreeni();
         *   c3.parse("   3  |  juoksu   | 12.12.2021");
         *   
         *   c1.equals(c2) === true;
         *   c2.equals(c1) === true;
         *   c1.equals(c3) === false;
         *   c3.equals(c2) === false;
         * </pre>
         */
    public boolean equals(cardioTreeni cardiotreeni) {
    	if (cardiotreeni == null )return false;
    	for (int i =0; i< getKenttia(); i++)
    		if (!anna(i).equals(cardiotreeni.anna(i)))return false;
    	return true;
    }
   
    
   @Override
   public boolean equals(Object cardio) {
       if ( cardio == null ) return false;
       return this.toString().equals(cardio.toString());
   }


   @Override
   public int hashCode() {
       return tunnusNro;
   }

   
    /**
     * Testipääojkelma cardiotreeneille.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        cardioTreeni juoksu = new cardioTreeni(), pyoraily = new cardioTreeni();
        juoksu.rekisteroi();
        pyoraily.rekisteroi();
        juoksu.tulosta(System.out);
        pyoraily.vastaaCardioTreeni();
       juoksu.tulosta(System.out);

      pyoraily.vastaaCardioTreeni();
      pyoraily.tulosta(System.out);

    }

}
