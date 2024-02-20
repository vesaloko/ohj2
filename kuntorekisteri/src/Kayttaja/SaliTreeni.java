/**
 * 
 */
package Kayttaja;

import java.io.OutputStream;
import fi.jyu.mit.ohj2.Mjonot;
import java.io.PrintStream;
import java.util.Comparator;

import Kanta.PvmTarkistus;

/**
 * salitreeni 
 * @author taruk
 *
 */
public class SaliTreeni implements Cloneable, Tietue {
	  private   int    tunnusNro;
	  private   String nimi =" ";
	  private   String pvm ="";
	  private String fiilis="";
	  
	  private static int seuraavaNro =1;
	  
	  
	  /**
	     * Salitreenien vertailija 
	     */ 
	    public static class Vertailija implements Comparator<SaliTreeni> { 
	        private int k;  
	         
	        @SuppressWarnings("javadoc") 
	        public Vertailija(int k) { 
	            this.k = k; 
	        } 
	         
	        @Override 
	        public int compare(SaliTreeni treeni1, SaliTreeni treeni2) { 
	            return treeni1.getAvain(k).compareToIgnoreCase(treeni2.getAvain(k)); 
	        } 
	    } 
	     
	    
	    /** 
	     * Antaa k:n kentän sisällön merkkijonona 
	     * @param k monenenko kentän sisältö palautetaan 
	     * @return kentän sisältö merkkijonona 
	     */ 
	    public String getAvain(int k) { 
	        switch ( k ) { 
	        case 0: return "" + tunnusNro; 
	        case 1: return "" + nimi.toUpperCase(); 
	        case 2: return "" + pvm; // vaihda vuosi ja pvm keskenään 
	        case 3: return "" + fiilis; 
	        default: return "virhe"; 
	        } 
	    } 
	       

	  /**
	 * constructor/turha
	 */
	public SaliTreeni() {
		  //eimitään
	  }
	  
	
	  /**
	   * palauttaa treenin kenttien lukumäärän
	   * @return kenttien lukumäärän
	   */
	  @Override
    public int getKenttia() {
		  return 4;
	  }
	  
	  
	  /**
	   * palauttaa ekan kentän
	   * @return eka kenttä
	   */
	  @Override
    public int ekaKentta() {
		  return 1;
	  }
	  

      /**
	   * hateaan treenin nimi
	   * @return treenin nimi
       * @example
       * <pre name="test">
       *   SaliTreeni jalkapäivä = new SaliTreeni();
       *   jalkapäivä.vastaaTreeniSali();
       *   jalkapäivä.getNimi() =R= "Jalka päivä.*";
       * </pre>
       */
	  public String getNimi() {
		  return nimi;
	  }
	  
	  
	  /**
	   * @return treenin päivämä
	   * ärän
	   */
	  public String getPvm() {
		  return pvm;
	  }
	  
	  
	  /**
	   * 
	   * @return treenin fiilisasteikon 
	   */
	  public String getFiilis() {
		  return fiilis;
	  }
	 
	 
		/**
		 * vastaa ohjelma
		 */
		public void vastaaTreeniSali() {
		nimi = "Jalka päivä";
		pvm = "21.2.2023";
		fiilis= "hiki tuli";
		}
		
		
		 /**
	     * Tehdään identtinen klooni treenistä
	     * @return Object kloonattu jäsen
	     * @example
	     * <pre name="test">
	     * #THROWS CloneNotSupportedException 
	     *   SaliTreeni treeni= new SaliTreeni();
	     *   treeni.parse("   3  |  Ankka Aku   | 123");
	     *   SaliTreeni kopio = treeni.clone();
	     *   kopio.toString() === treeni.toString();
	     *   treeni.parse("   4  |  Ankka Tupu   | 123");
	     *   treeni.toString().equals(treeni.toString()) === true;
	     * </pre>
	     */
	    @Override
	    public SaliTreeni clone() throws CloneNotSupportedException {
	        SaliTreeni uusi;
	        uusi = (SaliTreeni) super.clone();
	        return uusi;
	    }
	   
		
		 /**
	     * Asettaa tunnusnumeron ja samalla varmistaa että
	     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
	     * @param nr asetettava tunnusnumero
	     */
	    private void setTunnusNro(int nr) {
	        tunnusNro = nr;
	        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
	    }
	    

	    @Override
        public String anna(int k) {
	    	switch(k) {
	    	 case 0: return "" + tunnusNro;
	         case 1: return "" + nimi;
	         case 2: return "" + pvm;
	         case 3: return "" + fiilis;
	         default: return "En anna";

	    	}
	    }
	    
	    
	    @Override
        public String aseta(int ok, String jono) {
	    	String uusiJono= jono.trim();
	    	StringBuilder sb=new StringBuilder (uusiJono);
	    	switch(ok) {
	    	case 0:
	    		setTunnusNro(Mjonot.erota(sb, 'k',getTunnusNro()));
	    		return null;
	    	case 1:
	    		nimi=uusiJono; return null;
	    	case 2:
	    		   PvmTarkistus Paivamaara = new PvmTarkistus();
	    		   String virhe = Paivamaara.tarkista(uusiJono);
	    		   if(virhe!= null)return virhe;
	    		   pvm=uusiJono;
	    		   return null;
	    	case 3: 
	    		fiilis=uusiJono;
	    		return null;
            default:
                break;
	    	}
	    	return "";
	    }
	   
	    
	    /**
	     * Palauttaa jäsenen tiedot merkkijonona jonka voi tallentaa tiedostoon.
	     * @return jäsen tolppaeroteltuna merkkijonona 
	     * @example
	     * <pre name="test">
	     *   SaliTreeni treeni = new SaliTreeni();
	     *   treeni.parse("   3  |  Ankka Aku   | 030201-111C");
	     *   treeni.toString().startsWith("3|Ankka Aku|030201-111C|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
	     * </pre>  
	     */
	    @Override
	    public String toString() {
	        return "" +
	                getTunnusNro() + "|" +
	                nimi + "|" +
	                pvm + "|" +
	                fiilis;
	    }
	    
	    
	    /**
	     * palauttaa k:tta treeniä vastaavan kysymyksen
	     * @param k minkä kentän kysymys palautetaan
	     * @return kysymys
	     */
	    @Override
        public String getKysymys(int k) {
	    	switch(k) {
	    	case 0:return "Tunnus nro";
	    	case 1: return "Treenin nimi";
	    	case 2: return "Päivämäärä";
	    	case 3: return "Fiilis";
	    	default:return "Moikkis";
	    	}
	    }

	    
	    /**
	     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
	     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
	     * @param rivi käsiteltäävä
	     */
	    public void parse(String rivi) {
	        StringBuffer sb = new StringBuffer(rivi);
	        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
	        nimi = Mjonot.erota(sb, '|', nimi);
	        pvm = Mjonot.erota(sb, '|', pvm);
	        fiilis = Mjonot.erota(sb, '|', fiilis);
	    }

	    
	    @Override
	    public boolean equals(Object salitreeni) {
	        if ( salitreeni instanceof SaliTreeni ) return equals((SaliTreeni)salitreeni);
	       return false;
	    }
	    
	    /**
	     * tutkii onko treenissä samat tiedot kuin parametrina tuodusssa treenissä
	     * @param treeni vertaa
	     * @return onko
	     */
	    public boolean equals(SaliTreeni treeni) {
	    	if(treeni ==null)return false;
	    	for(int i=0; i<getKenttia();i++)
	    		if(!anna(i).equals(treeni.anna(i)))return false;
	    	return true;
	    }


	    @Override
	    public int hashCode() {
	        return tunnusNro;
	    }

	/**
	 *
	 * Tulostetanan tietyn treenin tiedot 
	 * @param out virta
	 */
	public  void tulosta(PrintStream out) {
		 out.println(String.format("%03d", tunnusNro, 3 ) + "  " + nimi +" " + pvm +" " + fiilis);
	    }

	   /**
	    * tulostus 
	    * @param os virta
	    */
	 public void tulosta(OutputStream os) {
	        tulosta(new PrintStream(os));
	    }


	 /**
	     * Antaa jäsenelle seuraavan rekisterinumeron.
	     * @return jäsenen uusi tunnusNro
	     * @example
	     * <pre name="test">
	     *   SaliTreeni aku1 = new SaliTreeni();
	     *   aku1.getTunnusNro() === 0;
	     *   aku1.rekisteroi();
	     *   SaliTreeni aku2 = new SaliTreeni();
	     *   aku2.rekisteroi();
	     *   int n1 = aku1.getTunnusNro();
	     *   int n2 = aku2.getTunnusNro();
	     *   n1 === n2-1;
	     * </pre>
	     */
	 public int rekisteroi() {
	        tunnusNro = seuraavaNro;
	        seuraavaNro++;
	        return tunnusNro;
	    }


	
	/** tallennus
	 * @return 0
	 */
	public int tallenna() {
		return 0; 
	}

	/**
	 * @param args ei kaytossa
	 */
	public static void main(String[] args) {
		SaliTreeni treeni= new SaliTreeni();  
		treeni.rekisteroi();
        
        treeni.vastaaTreeniSali();
 
		treeni.tulosta(System.out); 
        treeni.tallenna(); // tallentaa treenin tiedot
	}

	
    /**
     * @return tunnusnro
     */
    public int getTunnusNro() {
        return tunnusNro;
    }


}
