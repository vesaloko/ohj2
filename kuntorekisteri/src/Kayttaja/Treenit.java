package Kayttaja;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author taruk
 * 
 */
public class Treenit implements Iterable<SaliTreeni> {
     private static final int MAX_TREENEJA   = 30;
     private static int              lkm           = 0;
     private SaliTreeni  alkiot[]= new SaliTreeni[MAX_TREENEJA];
     private boolean muutettu = false;
     private String kokoNimi = "";
     private String tiedostonPerusNimi = "nimet";

    
    
     /**
      * muodostaja
      */
     public Treenit() {
         //
     }
     
     
    /**
     * Lisää uuden treenin listaan
     * @param salitreeni streni
     ** <pre name="test">
     * #THROWS SailoException 
     * Treenit treenit = new Treenit();
     * SaliTreeni selka = new SaliTreeni(), jalka = new SaliTreeni();
     * treenit.getLkm() === 0;
     * treenit.lisaa(selka); treenit.getLkm() === 1;
     * treenit.lisaa(jalka); treenit.getLkm() === 2;
     * treenit.lisaa(selka); treenit.getLkm() === 3;
     * treenit.anna(0) === selka;
     * treenit.anna(1) === jalka;
     * treenit.anna(2) === jalka;
     * treenit.anna(1) == selka === false;
     * treenit.anna(1) == jalka === true;
     * treenit.anna(3) === selka; #THROWS IndexOutOfBoundsException 
     * treenit.lisaa(selka); treenit.getLkm() === 4;
     * treenit.lisaa(selka); treenit.getLkm() === 5;
     * </pre>
     * @throws SailoException jos ei
     */
     
    public void lisaa(SaliTreeni salitreeni)throws SailoException {
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20); 
            alkiot[lkm] = salitreeni;
            lkm++;
            muutettu = true;
        }

     /**
     * Korvaa salitreenin titeorakenteesta etsitään samalla
     * tunnusnrolla oleva treeni
     * @param salitreeni sali
     * @throws SailoException
     *  <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Treenit treenit = new Treenit(); 
     * SaliTreeni juoksu1 = new SaliTreeni(), juoksu2 = new SaliTreeni();
     * juoksu1.rekisteroi(); juoksu2.rekisteroi();  
     * treenit.getLkm() === 0;
     * treenit.korvaaTaiLisaa(juoksu1); treenit.getLkm() === 1;
     * treenit.korvaaTaiLisaa(juoksu2); treenit.getLkm() === 2;
     * SaliTreeni juoksu3 = juoksu1.clone();
     * juoksu3.aseta(3,"kkk");
     * Iterator<cardioTreeni> it = treenit.iterator();
     * it.next() == juoksu1 === true;
     * treenit.korvaaTaiLisaa(juoksu3); treenit.getLkm() === 2;
     * it = cardiot.iterator();
     * cardioTreeni j0 = it.next();
     * j0 === juoksu3;
     * j0 == juoksu3 === true;
     * j0 == juoksu1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(SaliTreeni salitreeni) throws SailoException {
        int id = salitreeni.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = salitreeni;
                muutettu = true;
                return;
            }
        }
        lisaa(salitreeni);
    }

    
    /**
     * 
     * @param id id
     * @return id
     * @example 
     * <pre name="test">
     *  #THROWS SailoException 
     * Treenit treenit = new Treenit(); 
     * SaliTreeni mave1 = new SaliTreeni(), mave2 = new SaliTreeni(), mave3 = new SaliTreeni(); 
     * mave1.rekisteroi(); mave2.rekisteroi(); mave3.rekisteroi(); 
     * int id1 = mave1.getTunnusNro(); 
     * treenit.lisaa(mave1); treenit.lisaa(mave2); treenit.lisaa(mave3); 
     * treenit.poista(id1+1)===1;
     * treenit.poista(id1+1)=== null; treenit.getLkm()===2;
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    }

    /**
     * antaa viitteen i:nteen treeniin
     * @param i monenko treenin viite halutaan
     * @return viitteen treeniin
     * @throws IndexOutOfBoundsException jos ei
     */
    public SaliTreeni anna(int i) throws IndexOutOfBoundsException {
        if ( i < 0 || lkm <= i ) 
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


       
      /**
       * Luetaan tiedostosta
       * @param tied teidosto joista luetaan
       * @throws SailoException jos ei
       */
      public void lueTiedostosta(String tied) throws SailoException {
            setTiedostonPerusNimi(tied);
            try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
                kokoNimi = fi.readLine();
                if ( kokoNimi == null ) throw new SailoException("Kuntoilijan nimi puuttuu");
                String rivi = fi.readLine();
                if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
                // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

                while ( (rivi = fi.readLine()) != null ) {
                    rivi = rivi.trim();
                    if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                    SaliTreeni salitreeni = new SaliTreeni();
                    salitreeni.parse(rivi); // voisi olla virhekäsittely
                    lisaa(salitreeni);
                }
                muutettu = false;
            } catch ( FileNotFoundException e ) {
                throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei löydy, luodaan uusi");
            } catch ( IOException e ) {
                throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
            }
      }
      
      
          /**
           * 
           * @throws SailoException jos ei
           */
        public void lueTiedostosta() throws SailoException {
                lueTiedostosta(getTiedostonPerusNimi());
            }


    /**
       * Tallentaa treenin tiedostoon 
       * @throws SailoException jos epäonnistui
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
                  for (SaliTreeni SaliTreeni : this) {
                      fo.println(SaliTreeni.toString());
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
           * Asettaa tiedoston perusnimen ilan tarkenninta
           * @param tied tallennustiedoston perusnimi
           */
          public void setTiedostonPerusNimi(String tied) {
              tiedostonPerusNimi = tied;
          }

          
          /**
           * Palauttaa rekisterin koko nimen
           * @return rekisterin koko nimi merkkijononna
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

      /**
       * Palauttaa treenine lukumäärän
       * @return treenien lukumäärä
       */
      public int getLkm() {
            return lkm;
        }

        
      /**
     * @author venla
     * @version 4 Apr 2023
     *
     */
    public class SaliTreeniIterator implements Iterator<SaliTreeni> {
            private int kohdalla = 0;


            /**
             * Onko olemassa vielä seuraavaa jäsentä
             * @see java.util.Iterator#hasNext()
             * @return true jos on vielä jäseniä
             */
            @Override
            public boolean hasNext() {
                return kohdalla < getLkm();
            }


            /**
             * Annetaan seuraava jäsen
             * @return seuraava jäsen
             * @throws NoSuchElementException jos seuraava alkiota ei enää ole
             * @see java.util.Iterator#next()
             */
            @Override
            public SaliTreeni next() throws NoSuchElementException {
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
        public Iterator<SaliTreeni> iterator() {
            return new SaliTreeniIterator();
        }


        /** 
         * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
         * @param hakuehto hakuehto 
         * @param k etsittävän kentän indeksi  
         * @return tietorakenteen löytyneistä jäsenistä 
         * @example 
         * <pre name="test"> 
         * #THROWS SailoException  
         *   Treenit treenit = new Treenit(); 
         *   SaliTreeni jalka1 = new SaliTreeni(); jalka1.parse("1|jalkapäivä|23.2.2023|hyväfiilis|"); 
         *   SaliTreeni  jalka2 = new SaliTreeni (); jalka2.parse("2|kyykkypäivä|24.3.2023|ihan ok|"); 
         *   SaliTreeni  jalka3 = new SaliTreeni (); jalka3.parse("3|pohjepäivä|25.3.2023|hyvä"); 
         *   SaliTreeni jalka4 = new SaliTreeni (); jalka4.parse("4|takareisipäivä|26.3.2023|"); 
         *   SaliTreeni  jalka5 = new SaliTreeni (); jalka5.parse("5|jalkapäivä|23.4.2023|huono"); 
         *   treenit.lisaa(jalka1); treenit.lisaa(jalka2); treenit.lisaa(jalka3);treenit.lisaa(jalka4); treenit.lisaa(jalka5);
         *   // TODO: toistaiseksi palauttaa kaikki jäsenet 
         * </pre> 
         */ 
        
        public Collection<SaliTreeni> etsi(String hakuehto, int k) { 
           String ehto="*";
           if(hakuehto !=null && hakuehto.length()>0)ehto=hakuehto;
           int jk=k;
            if(jk<0)jk=0;
            List<SaliTreeni> loytyneet = new ArrayList<SaliTreeni>();
            for (SaliTreeni salitreeni : this) {
                if(WildChars.onkoSamat(salitreeni.anna(jk), ehto))
                loytyneet.add(salitreeni);  
            } 
            Collections.sort(loytyneet, new SaliTreeni.Vertailija(jk)); 
            return loytyneet; 
        }
        
        
        /**
         * antaa treenin id numeron 
         * @param id id
         * @return id numero
         * <pre name="test"> 
         * #THROWS SailoException  
         * Treenit treenit = new Treenit(); 
         * SaliTreeni juoksu1 = new SaliTreeni(), juoksu2 = new SaliTreeni(), juoksu3 = new SaliTreeni(); 
         * juoksu1.rekisteroi(); juoksu2.rekisteroi(); juoksu3.rekisteroi(); 
         * int id1 = juoksu1.getTunnusNro(); 
         * treenit.lisaa(juoksu1); treenit.lisaa(juoksu2); treenit.lisaa(juoksu3); 
         * treenit.annaId(id1  ) == juoksu1 === true; 
         * treenit.annaId(id1+1) == juoksu2 === true; 
         * treenit.annaId(id1+2) == juoksu3 === true; 
         * </pre> 
         */
        public SaliTreeni annaId(int id) { 
            for (SaliTreeni salitreeni : this) { 
                if (id == salitreeni.getTunnusNro()) return salitreeni; 
            } 
            return null; 
        }

        /**
         * Etsii treenin id numeron 
         * @param id id
         * @return
         * <pre name="test"> 
         * #THROWS SailoException  
         * Treenit treenit = new Treenit(); 
         * treeni juoksu1 = new Treeni(), juoksu2 = new Treeni(), juoksu3 = new Treeni(); 
         * juoksu1.rekisteroi(); juoksu2.rekisteroi(); juoksu3.rekisteroi(); 
         * int id1 = juoksu1.getTunnusNro();  
         * treenit.lisaa(juoksu1); treenit.lisaa(juoksu2); treenit.lisaa(juoksu3); 
         * treenit.etsiId(id1+1) === 1; 
         * treenit.etsiId(id1+2) === 2; 
         * </pre> 
         */ 
        public int etsiId(int id) { 
            for (int i = 0; i < lkm; i++) 
                if (id == alkiot[i].getTunnusNro()) return i; 
            return -1; 
        } 


    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
    Treenit treenit = new Treenit();
     
    SaliTreeni jalat = new SaliTreeni(), jalat2 = new SaliTreeni();
    
     jalat.vastaaTreeniSali();
    
     jalat.vastaaTreeniSali();

     try {
         treenit.lisaa(jalat);
         treenit.lisaa(jalat2);

         System.out.println("============= Treenit testi =================");

         int i = 0;
         for (SaliTreeni salitreeni: treenit) { 
             System.out.println("Treeni nro: " + i++);
             salitreeni.tulosta(System.out);
         }


     } catch (SailoException ex) {
         System.out.println(ex.getMessage());
     }
 }

        
        

    }

