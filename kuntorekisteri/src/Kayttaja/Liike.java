package Kayttaja;
import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author venla
 * @version 1 Mar 2023
 *
 */
public class Liike implements Cloneable, Tietue {
    private int tunnusNro;
    private int treeniNro;
    private String liikkeenNimi = "";
    private String painot  = "";
    private String maara  = "";


    private static int seuraavaNro    = 1;

    /**
     * Alustetaan liike.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Liike() {
        // Vielä ei tarvita mitään
    }

    /**
     * @return harrastukse kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 5;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 2;
    }
    

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    @Override
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "tunnusNro";
            case 1:
                return "treeniNro";
            case 2:
                return "Liike";
            case 3:
                return "Painot";
            case 4:
                return "Määrä";
            default:
                return "???";
        }
    }

    /**
     * Alustetaan tietyn treenin liike.  
     * @param treeniNro treenin viitenumero 
     */
    public Liike(int treeniNro) {
        this.treeniNro = treeniNro;
    }
    
    
    @Override
    public String anna(int k) {
        switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + treeniNro;
            case 2:
                return liikkeenNimi;
            case 3:
                return "" + painot;
            case 4:
                return "" + maara;
            default:
                return "???";
        }
    }


    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Harrastukselle.
     * Aloitusvuosi arvotaan, jotta kahdella harrastuksella ei olisi
     * samoja tietoja.
     * @param nro viite henkilöön, jonka harrastuksesta on kyse
     */
    public void vastaaLiike(int nro) {
        treeniNro = nro;
        liikkeenNimi = "Uusi liike";
        painot = "Lisää painot";
        maara = "Lisää määrä";
    }

    
    @Override
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                treeniNro = Mjonot.erota(sb, '$', treeniNro);
                return null;
            case 2:
                liikkeenNimi = st;
                return null;
            case 3:
                painot = st;
                return null;

            case 4:
                 maara = st;
                return null;

            default:
                return "Väärä kentän indeksi";
        }
    }

    @Override
    public Liike clone() throws CloneNotSupportedException { 
        return (Liike)super.clone();
    }
    

    
    /**
     * Tulostetaan liikkeen tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3));
        out.println(liikkeenNimi);
        out.println("painot " + painot + " kg");
        out.print("toistot " + maara);
    }

    /**
     * Tulostetaan liikkeen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    
    /**
     * Antaa liikkeelle seuraavan rekisterinumeron.
     * @return liikkeen uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Liike liike1 = new Liike();
     *   liike1.getTunnusNro() === 0;
     *   liike1.rekisteroi();
     *   Liike liike2 = new Liike();
     *   liike2.rekisteroi();
     *   int n1 = liike1.getTunnusNro();
     *   int n2 = liike2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */

    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }


    /**
     * Palauttaa liikkeen tunnusnumeron.
     * @return jliikkeen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palautetaan mille jäsenelle harrastus kuuluu
     * @return jäsenen id
     */
    public int getTreeniNro() {
        return treeniNro;
    }

   

    
    /**
     * Asetetaan tunnusnumero ja varmistetaab että seuraava
     * numero on aina suurempi kuin edellinen
     * @param numero asetettava numero
     */
    private void setTunnusNro(int numero) {
    	tunnusNro=numero;
    	if ( tunnusNro >= seuraavaNro)seuraavaNro= tunnusNro+1;
    }
    
    
    /**
     * palautaa liikkeen tiedot merkkijonona joka talleneetaan tiedostoon. 
     * @return treeni merkkijonona tolppien kanssa
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
     * Selvitetään liikkeen tiedot merkkijonosta 
     * @param rivi mistä saadaan liikkeen tiedot
     */
	public void parse(String rivi) {
	    StringBuffer sb = new StringBuffer(rivi);
	    for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));

			
	}

	/**
	 * equals metodi
	 */
	@Override
    public boolean equals(Object ob) {
		if ( ob == null ) return false;
		return this.toString().equals(ob.toString());
	}
	
	   @Override
	    public int hashCode() {
	        return tunnusNro;
	    }

	 
    /**
     * Testiohjelma Liikkeelle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Liike liike = new Liike();
        liike.vastaaLiike(2);
        liike.tulosta(System.out);
        
    }
	

}
