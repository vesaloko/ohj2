package Kayttaja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/** cardiotreenien luokka
 * @author venla
 * @version 1 Mar 2023
 *
 */
public class cardioTreenit implements Iterable<cardioTreeni> {
    private static final int MAX_CARDIOT   = 31;
    private int lkm = 0;
    private String tiedostonPerusNimi = "cardiotreenit";
    private cardioTreeni alkiot[] = new cardioTreeni[MAX_CARDIOT];
    private String kokoNimi = "";
    private boolean muutettu = false;

    /**
     * Oletusmuodostaja
     */
    public cardioTreenit() {
        // Attribuuttien oma alustus riittää
    }

    
    /**
     * Lisää uuden cardiotreenin tietorakenteeseen.  Ottaa tämän omistukseensa.
     * @param cardio cardiotreeniin lisätäävän jäsenen viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * </pre>
     * cardioTreenit cardiot = new cardioTreenit(); 
     * cardioTreeni juoksu1 = new cardioTreeni(), juoksu2 = new cardioTreeni();
     * cardiot.getLkm() === 0;
     * cardiot.lisaa(juoksu1); cardiot.getLkm() === 1;
     * cardiot.lisaa(juoksu2); cardiot.getLkm() === 2;
     * cardiot.lisaa(juoksu1); cardiot.getLkm() === 3;
     * Iterator<cardioTreeni> it = cardiot.iterator(); 
     * it.next() === juoksu;
     * it.next() === juoksu2; 
     * it.next() === juoksu1;  
     * cardiot.lisaa(juoksu1); cardiot.getLkm() === 4;
     * cardiot.lisaa(juoksu1); cardiot.getLkm() === 5;
     * </pre>
     */
    public void lisaa(cardioTreeni cardio) throws SailoException {
        if (lkm >= alkiot.length) alkiot =Arrays.copyOf(alkiot, lkm +20); 
        alkiot[lkm] = cardio;
        lkm++;
        muutettu = true;
    }
    
    
    /**
     * Korvaa cardion titeorakenteesta etsitään samalla
     * tunnusnrolla oleva cardio
     * @param cardiotreeni cardio
     * @throws SailoException
     *  <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * cardioTreenit cardiot = new cardioTreenit(); 
     * cardioTreeni juoksu1 = new cardioTreeni(), juoksu2 = new cardioTreeni();
     * juoksu1.rekisteroi(); juoksu2.rekisteroi();  
     * cardiot.getLkm() === 0;
     * cardiot.korvaaTaiLisaa(juoksu1); cardiot.getLkm() === 1;
     * cardiot.korvaaTaiLisaa(juoksu2); cardiot.getLkm() === 2;
     * cardioTreeni juoksu3 = juoksu1.clone();
     * juoksu3.aseta(3,"kkk");
     * Iterator<cardioTreeni> it = cardiot.iterator();
     * it.next() == juoksu1 === true;
     * cardiot.korvaaTaiLisaa(juoksu3); cardiot.getLkm() === 2;
     * it = cardiot.iterator();
     * cardioTreeni j0 = it.next();
     * j0 === juoksu3;
     * j0 == juoksu3 === true;
     * j0 == juoksu1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(cardioTreeni cardiotreeni) throws SailoException {
        int id = cardiotreeni.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = cardiotreeni;
                muutettu = true;
                return;
            }
        }
        lisaa(cardiotreeni);
    }

    
    /**
     * Lukee treeniein tiedostosta.  Kesken.
     * @param tied tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
           

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                cardioTreeni cardiotreeni = new cardioTreeni();
                cardiotreeni.parse(rivi); // voisi olla virhekäsittely
                lisaa(cardiotreeni);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    
    
    /**
     * Luetaan aiemmin annetttu nimi tiedostosta
     * @throws SailoException jos ilmenee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }


    
    /**
     * Tallentaa treenin tiedostoon.  Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (cardioTreeni cardiotreeni : this) {
                fo.println(cardiotreeni.toString());
            }
            //} catch ( IOException e ) { // ei heitä poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }

    
    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko treeniin viite halutaan
     * @return viite treeninn, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    protected cardioTreeni anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) 
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


   /**
    * Palauttaa treeniien lukumäärän
    * @return treenien lukumäärä
    */
   public int getLkm() {
       return lkm;
   }
   
   
   /**
    * Palauttaa Kerhon koko nimen
    * @return Kerhon koko nimi merkkijononna
    */
   public String getKokoNimi() {
       return kokoNimi;
   }
   
   
   /**
    * Palauttaa tiedoston nimen, jota käytetään tallennukseen
    * @return tallennustiedoston nimi
    */
   public String getTiedostonPerusNimi() {
       return tiedostonPerusNimi;
   }


   /**
    * Asettaa tiedoston perusnimen ilan tarkenninta
    * @param nimi tallennustiedoston perusnimi
    */
   public void setTiedostonPerusNimi(String nimi) {
       tiedostonPerusNimi = nimi;
   }


   /**
    * Palauttaa tiedoston nimen, jota käytetään tallennukseen
    * @return tallennustiedoston nimi
    */
   public String getTiedostonNimi() {
       return getTiedostonPerusNimi() + ".dat";
   }


   /**
    * Palauttaa varakopiotiedoston nimen
    * @return varakopiotiedoston nimi
    */
   public String getBakNimi() {
       return tiedostonPerusNimi + ".bak";
   }

   
   /** cardiotreeien iteraattori
 * @author venla
 * @version 24 Apr 2023
 *
 */
public class cardioTreenitIterator implements Iterator<cardioTreeni> {
       private int kohdalla = 0;

       /**
        * Onko olemassa vielä seuraavaa treeniä
        * @see java.util.Iterator#hasNext()
        * @return true jos on vielä jäseniä
        */
       @Override
       public boolean hasNext() {
           return kohdalla < getLkm();
       }


       /**
        * Annetaan seuraava cardiotreeni
        * @return seuraava jäsen
        * @throws NoSuchElementException jos seuraava alkiota ei enää ole
        * @see java.util.Iterator#next()
        */
       @Override
       public cardioTreeni next() throws NoSuchElementException {
           if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
           return anna(kohdalla++);
       }


       /**
        * Tuhoamista ei ole toteutettu
        * @throws UnsupportedOperationException aina
        * @see java.util.Iterator#remove()
        */
       @Override
       public void remove() throws UnsupportedOperationException {
           throw new UnsupportedOperationException("Me ei poisteta");
       }
   }


   /**
    * Palautetaan iteraattori jäsenistään.
    * @return jäsen iteraattori
    */
   @Override
   public Iterator<cardioTreeni> iterator() {
       return new cardioTreenitIterator();
   }


   /** 
    * Palauttaa "taulukossa" hakuehtoon vastaavien cardiotreenien viitteet 
    * @param hakuehto hakuehto 
    * @param k etsittävän kentän indeksi  
    * @return tietorakenteen löytyneistä cardioista
    */
   
   public Collection<cardioTreeni> etsi(String hakuehto, int k) { 
	   String ehto = "*"; 
       if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
       int hk = k; 
       if ( hk < 0 ) hk = 0;

	   List<cardioTreeni> loytyneet = new ArrayList<cardioTreeni>(); 
       for (cardioTreeni cardiotreeni : this) { 
    	   if (WildChars.onkoSamat(cardiotreeni.anna(hk), ehto))
    		   loytyneet.add(cardiotreeni);     
       } 
       Collections.sort(loytyneet, new cardioTreeni.Vertailija(hk));
  
       return loytyneet; 
   }
   
   
   /**
    * poistaa cardion jolla valittu tunnus nro
    * @param id numero
    * @return luku
    * @example 
    * <pre name="test">
    *  #THROWS SailoException 
    *  cardioTreenit cardiot = new cardioTreenit(); 
    * cardioTreeni juoksu1 = new cardioTreeni(), juoksu2 = new cardioTreeni(), juoksu3 = new cardioTreeni(); 
    * juoksu1.rekisteroi(); juoksu2.rekisteroi(); juoksu3.rekisteroi(); 
    * int id1 = juoksu1.getTunnusNro(); 
    * cardiot.lisaa(juoksu1); cardiot.lisaa(juoksu2); cardiot.lisaa(juoksu3); 
    * cardiot.poista(id1+1)===1;
    * cardiot.poista(id1+1)=== null; cardiot.getLkm()===2;
    */
   public int poista(int id) {
	  int ind= etsiId(id);
	  if (ind <0) return 0;
	  lkm --;
	  for (int i=ind; i< lkm; i++)
		  alkiot[i]= alkiot[i+1];
	   alkiot[lkm]=null;
	   muutettu =true;
	 return 1;
   }
   
   
   /**
    * etsii cardion Id:n perusteella 
    * @param id etsittävä
    * @return
    *   <pre name="test"> 
    * #THROWS SailoException  
    * cardioTreenit cardiot = new cardioTreenit(); 
     * cardioTreeni juoksu1 = new cardioTreeni(), juoksu2 = new cardioTreeni(), juoksu3 = new cardioTreeni(); 
     * juoksu1.rekisteroi(); juoksu2.rekisteroi(); juoksu3.rekisteroi(); 
     * int id1 = juoksu1.getTunnusNro(); 
     * cardiot.lisaa(juoksu1); cardiot.lisaa(juoksu2); cardiot.lisaa(juoksu3); 
     * cardiot.annaId(id1  ) == juoksu1 === true; 
     * cardiot.annaId(id1+1) == juoksu2 === true; 
     * cardiot.annaId(id1+2) == juoksu3 === true; 
     * </pre> 
     */
   public cardioTreeni annaId(int id) {
	   for (cardioTreeni cardio : this) { 
           if (id == cardio.getTunnusNro()) return cardio; 
       } 
       return null;
   }

   
   /**
    * etsii cardion id:n perusteella 
    * @param id etsittävä
    * @return tunnusnro
     * <pre name="test"> 
     * #THROWS SailoException  
      cardioTreenit cardiot = new cardioTreenit(); 
     * cardioTreeni juoksu1 = new cardioTreeni(), juoksu2 = new cardioTreeni(), juoksu3 = new cardioTreeni(); 
     * juoksu1.rekisteroi(); juoksu2.rekisteroi(); juoksu3.rekisteroi(); 
     * int id1 = juoksu1.getTunnusNro();  
     * cardiot.lisaa(juoksu1); cardiot.lisaa(juoksu2); cardiot.lisaa(juoksu3); 
     * cardiot.etsiId(id1+1) === 1; 
     * cardiot.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
   public int etsiId(int id) {
	for (int i=0; i< lkm; i++)
		if (id== alkiot[i].getTunnusNro())return i;
	return -1;
   }

   
    /**
    * Testiohjelma cardio treeneille
    * @param args ei käytössä
    */
   public static void main(String args[]) {
       cardioTreenit cardiotreenit = new cardioTreenit();

       cardioTreeni juoksu = new cardioTreeni(), pyoraily = new cardioTreeni();
       juoksu.rekisteroi();
       juoksu.vastaaCardioTreeni();
       pyoraily.rekisteroi();
       pyoraily.vastaaCardioTreeni();

       try {
           cardiotreenit.lisaa(juoksu);
           cardiotreenit.lisaa(pyoraily);

           System.out.println("============= cardioTreenit testi =================");

           int i = 0;
           for (cardioTreeni cardiotreeni : cardiotreenit) { 
               System.out.println("Treenin nro: " + i++);
               cardiotreeni.tulosta(System.out);

           }
       } catch (SailoException ex) {
           System.out.println(ex.getMessage());
       }
   }
}
 
   
