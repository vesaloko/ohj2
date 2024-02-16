package Kanta;
import static Kanta.SisaltaaTarkistaja.*;

import fi.jyu.mit.ohj2.Mjonot;
/**
 * @author venla
 * @version 6 Apr 2023
 *
 */
public class PvmTarkistus {

    /** Kuukausien maksimipituudet */
    //                                1  2  3  4  5  6  7  8  9 10 11 12   
    public static int[] KUUKAUDET = {31,29,31,30,31,30,31,31,30,31,30,31};


    /**
     * Tarkistetaan päivämäärä sallitaan pv joko vuosi lyhennettynä
     * tai pitkänä.
     *  @param pvm joka tutkitaan.
     * @return null jos oikein, muuten virhettä kuvaava teksti
     * TODO tarkistukset kuntoon myös karkausvuodesta.
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * PvmTarkistus pvm = new PvmTarkistus();
     * pvm.tarkista("12121")       === "Päivämäärä on liian lyhyt";
     * pvm.tarkista("k")           === "Ei ole oikea päivämäärä";
     * pvm.tarkista("12.000000.00")      === "Päivämäärä on liian pitkä";
     * pvm.tarkista("0.12.2023") === "Liian pieni päivämäärä";
     * pvm.tarkista("1.0.23")=== "Liian pieni kuukausi";
     * pvm.tarkista("12.3.0") ==="Liian pieni vuosi";
     * pvm.tarkista("23.4.2023") === null;
     * pvm.tarkista("23.4.22") === null;
     * </pre>
     */  
    public String tarkista(String pvm) {
      if ( !onkoVain(pvm,SALLITUT)) return "Ei ole oikea päivämäärä"; 
        int pituus = pvm.length();
        if ( pituus < 6 ) return "Päivämäärä on liian lyhyt";
        if ( pituus > 10 ) return "Päivämäärä on liian pitkä";
        
        StringBuilder sb = new StringBuilder(pvm);
        int p = Mjonot.erota(sb, '.', 0);
        int k = Mjonot.erota(sb, '.', 0);
        int v = Mjonot.erota(sb, ' ', 0);

        if ( k < 1)  return "Liian pieni kuukausi";
        if ( 12 < k ) return "Liian suuri kuukausi";
        if ( p < 1 )  return "Liian pieni päivämäärä";
        if (p > 32) return "Liian iso päivämäärä";
        if (v < 20) return "Liian pieni vuosi";
        if (v > 2200) return "Liian iso vuosi";
      
        return null;
}
}



