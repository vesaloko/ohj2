package Kayttaja;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author taruk
 *
 */

public class Liikkeet implements Iterable<Liike> {
	    
	    private String  tiedostonOgNimi = "";
	    private boolean muutettu= false;
	    private final List<Liike> alkiot = new ArrayList<Liike>();
	    
	    
        /**
         * liikkeiden alustaminen
         */
	    public Liikkeet() {
	    	//
	    }
	    
	    
	    /**
	     * Lisaa liikkeen teitorakneteeseen
	     * @param liike liike
	     */
	    public void lisaa(Liike liike){
	        alkiot.add(liike);
	        muutettu=true;
	    }
	    
	    
	    /**
	     *
	     * @param liike käsiteltävä
	     * @throws SailoException jos ei
	     */
	    public void korvaaTaiLisaa(Liike liike) throws SailoException {
	        int id = liike.getTunnusNro();
	        for (int i = 0; i < getLkm(); i++) {
	            if (alkiot.get(i).getTunnusNro() == id) {
	                alkiot.set(i, liike);
	                muutettu = true;
	                return;
	            }
	        }
	        lisaa(liike);
	    }

	    
	    /**
	     * palauttaa viitten i:teen liikkeeseen
	     * @param tunnusnro treenin tunnusnuro jolle liikkeitä haetaan
	     * @return viitteen liikkeeseen
	     * @example 
	     * <pre name="test">
	     * #import java.util.*;
	     *  Liikkeet liikkeet = new Liikkeet();
         *  Liike mave1 = new Liike(2); liikkeet.lisaa(mave1);
         *  Liike soutu = new Liike(1); liikkeet.lisaa(soutu);
         *  Liike kyykky = new Liike(2); liikkeet.lisaa(kyykky);
         *  List <Liike> loytyneet;
         *  loytyneet=liikkeet.anna(1);
         *  loytyneet.size()===1;
         *  loytyneet.get(0)== soutu === true;
         *  loytyneet=liikkeet.anna(3);
         *  loytyneet.size()===0;
         *  </pre>
	     * 
	     */
	    public List<Liike> anna(int tunnusnro) {
	        List<Liike> loydetyt = new ArrayList<Liike>();
	        for (Liike liike : alkiot)
	            if (liike.getTreeniNro() == tunnusnro)loydetyt.add(liike);
	        return loydetyt;
	    }
        
	    
	    /**
	     * poistaa liikkeen listasta
	     * @param liike positettava
	     * @return ret
	     */
	    public boolean poista(Liike liike) {
	        boolean ret = alkiot.remove(liike);
	        if (ret) muutettu = true;
	        return ret;
	    }

	    
	    /***
	     * poistaa treenin liikkeen
	     * @param tunnusNro id
	     * @return poistettu
	     */
	    public int poistaSalitreeninLiikkeet(int tunnusNro) {
	        int n = 0;
	        for (Iterator<Liike> it = alkiot.iterator(); it.hasNext();) {
	            Liike liike = it.next();
	            if ( liike.getTreeniNro() == tunnusNro ) {
	                it.remove();
	                n++;
	            }
	        }
	        if (n > 0) muutettu = true;
	        return n;
	    }

	    
	    /**
	     * Tallentaa liikkeen,. 
	     * @throws SailoException jos ei
	     */
	    public void talleta() throws SailoException {
	       if ( !muutettu )return;
	       File fbak = new File (getBakNimi());
	       File ftied= new File(getTiedostonNimi());
	       fbak.delete();
	       ftied.renameTo(fbak);
	       
	       try(PrintWriter fo= new PrintWriter(new FileWriter(ftied.getCanonicalPath()))){
	    	   for ( Liike liike :this) {
	    		   fo.println(liike.toString());
	    	   }
	       }catch ( FileNotFoundException ex) {
	    	   throw new SailoException("Tiedosto " + ftied.getName()+ " ei aukea");
	       }catch( IOException ex ){
	    	   throw new SailoException("tiedoston " +ftied.getName()+ " kirjoittamisessa ongelmia");
	       }
	       muutettu=false;
	    }
	    
	    
	    /**
	     * Lukee liikkeet teidostosta
	     * @param hakemisto tiedosto
	     * @throws SailoException jos ei
	     * @example 
	     * <pre name="test">
	     * #THROWS SailoException
	     * #import java.io.file;
	     * Liikkeet liikkeet =new Liikkeet();
	     * Liike mave=new Liike();
	     * mave.vastaaLiike(2);
	     * Liike mave2 =new Liike();
	     * mave2.vastaaLiike(1);
	     *   String tiedNimi = "testi";
         *  File ftied = new File(tiedNimi+".dat");
         *  ftied.delete();
         *  liikkeet.lueTiedostosta(tiedNimi); #THROWS SailoException
         *  liikkeet.lisaa(mave);
         *  liikkeet.lisaa(mave2);
         *  liikkeet.talleta();
         *  liikkeet.lueTiedostosta(tiedNimi);
         *  Iterator<Liike> i = liikkeet.iterator();
         *  i.next().toString() === mave.toString();
	     * </pre>
	     */
	    public void lueTiedostosta(String hakemisto) throws SailoException {
	        
	    	setTiedostonOgNimi(hakemisto);
	    	try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi()))){
	    		String rivi;
	    		
	    		while((rivi=fi.readLine())!= null ){
	    			rivi=rivi.trim();
	    			if ("".equals(rivi)|| rivi.charAt(0)== ';')continue;
	    			Liike liike=new Liike();
	    			liike.parse(rivi);
	    			lisaa(liike);
	    		}
	    		muutettu=false;	
	    	}catch ( FileNotFoundException e) {
	    		throw new SailoException("tiedosto " + getTiedostonNimi()+" ei aukea");
	    	} catch ( IOException e ) {
	            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
	        }

	    	}
	    
	    
	    /**
	     * Luetaan aikaisemmin annetun nimisestä tiedostosta
	     * @throws SailoException jos tulee poikkeus
	     */
	    public void lueTiedostosta() throws SailoException {
	        lueTiedostosta(getTiedostonOgNimi());
	    }
	    

	    	
	    /**
	     * Antaa backup tiedoston nimen 
	     * @return backcup tiedostonnimi
	     */
        public String getBakNimi() {
        	return tiedostonOgNimi +".bak";
        }
	    
	    /**
	     * plauattaa liikkeiden lukumäärän
	     * @return alkioiden koko
	     */
	    public int getLkm(){
	    	return alkiot.size();
	    }
	    
	    
	    /**
	     * asettaa tiedostoon original nimen
	     * @param tiedosto käziteltä
	     */
	    public void setTiedostonOgNimi(String tiedosto) {
	    	tiedostonOgNimi=tiedosto;
	    }
	    
	    
	    /**
	     * hakee tiedoston original nimen
	     * @return nimi
	     */
	    public String getTiedostonOgNimi() {
	    	return tiedostonOgNimi;
	    }
	    
	    
	    /**
	     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
	     * @return tallennustiedoston nimi
	     */
	    public String getTiedostonNimi() {
	        return tiedostonOgNimi + ".dat";
	    }

	    
	    /**
	     * Testiohjelma liikkeille
	     * @param args ei käytössä
	     */
	    public static void main(String args[]) {
	       Liikkeet liikkeet = new Liikkeet();

	        Liike maastaveto = new Liike(), soutu = new Liike();
	        maastaveto.rekisteroi();
	        maastaveto.vastaaLiike(1);
	        soutu.rekisteroi();
	        soutu.vastaaLiike(0);

	       
	        liikkeet.lisaa(maastaveto);
	        liikkeet.lisaa(soutu);

	        System.out.println("============= Liikkeet testi =================");

	      
	            List<Liike> liikkeet2 = liikkeet.anna(1);

	            for (Liike liike : liikkeet2) {
	                System.out.print(liike.getTunnusNro() + " ");
	                liike.tulosta(System.out);
	            }
	        }
	    
	    /**
	     * Iteraattori kaikkien liikkeiden läpikäymiseen
	     * @return liikeiteraattori
	     * @example
	     * <pre name="test">
	     * #PACKAGEIMPORT
	     * #import java.util.*;
	     * 
	     *  Liikkeet liikkeet= new Liikkeet();
	     *  Liike mave= new Liike(2); liikkeet.lisaa(mave);
	     *  Liike soutu = new Liike(1); liikkeet.lisaa(soutu);
	     *  Liike kyykky = new Liike(2); liikkeet.lisaa(kyykky);
	     *  Liike soutu2 = new Liike(1); liikkeet.lisaa(soutu2);
	     *  Iterator<Liike> i2=liikkeet.iterator();
	     *  i2.next() === mave;
	     *  i2.next() === soutu;
	     *  i2.next() === kyykky;
	     *  i2.next() === soutu2; 
	     *  
	     *  int n = 0;
	     *  int jnrot[] = {2,1,2,1};
	     *  
	     *  for (Liike liike:liikkeet ) { 
	     *    liike.getTreeniNro() === jnrot[n]; n++;  
	     *  }
	     *  
	     *  n === 4;
	     *  
	     * </pre>
         */
		@Override
		public Iterator<Liike> iterator() {
			return alkiot.iterator();
		}
}
