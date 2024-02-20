package Kanta;

/**
 * Tarkistaja joka tarkistaa että jono sisältää vain valittuja merkkejä.
 * Hyväksyy tyhjän jonon.
 * @author vesaloko
 *
 */
public class SisaltaaTarkistaja  {

    /** Numeroita vastaavat kirjaimet */
    public static final String SALLITUT = "0123456789.:;/,";

    /** Desimaalilukuun käyvät kirjaimet */
    public static final String DESIMAALINUMEROT = "0123456789.,";
   // public static final String EROTIN = ",.:";

    /**
     * Onko jonossa vain sallittuja merkkejä
     * @param jono      tutkittava jono
     * @param sallitut verrattava mitkä sallitaan
     * @return true jos vain sallittuja, false muuten
     * @example
     * <pre name="test">
     *   onkoVain("123","12")   === false;
     *   onkoVain("123","1234") === true;
     *   onkoVain("","1234") === true;
     * </pre> 
     */
    public static boolean onkoVain(String jono, String sallitut) {
        for (int i=0; i<jono.length(); i++)
            if ( sallitut.indexOf(jono.charAt(i)) < 0) return false;
        return true;
    }


    final String sallitut;


    /**
     * Luodaan tarkistaja joka hyväksyy sallitut merkit
     * @param sallitut hyväksyttävät merkit
     */
    public SisaltaaTarkistaja(String sallitut) {
        this.sallitut = sallitut;
    }


    /**
     * Tarkistaa että jono sisältää vain valittuja numeroita
     * @param jono tutkittava jono
     * @return null jos vain valittujan merkkejä, muuten virheilmoitus
     * @example
     * <pre name="test">
     *   SisaltaaTarkistaja tar = new SisaltaaTarkistaja("123");
     *   tar.tarkista("12") === null;
     *   tar.tarkista("14") === "Saa olla vain merkkejä: 123";
     *   tar.tarkista("")   === null;
     * </pre>
     */
    public String tarkista(String jono) {
        if ( onkoVain(jono, sallitut) ) return null;
        return "Saa olla vain merkkejä: " + sallitut;
    }

}
