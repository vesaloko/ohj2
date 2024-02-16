package Kayttaja;

    

    /**
     * @author venla
     * @version 24 Apr 2023
     * Tietue-rajapinta
     */
    public interface Tietue {

        
        /**
         * @return tietueen kenttien lukumäärä
         * @example
         */
        public abstract int getKenttia();


        /**
         * @return ensimmäinen käyttäjän syötettävän kentän indeksi
         * @example
         * <pre name="test">
         * </pre>
         */
        public abstract int ekaKentta();


        /**
         * @param k minkä kentän kysymys halutaan
         * @return valitun kentän kysymysteksti
         * @example
         * <pre name="test">
         * </pre>
         */
        public abstract String getKysymys(int k);


        /**
         * @param k Minkä kentän sisältö halutaan
         * @return valitun kentän sisältö
         * @example   
         * </pre>
         */
        public abstract String anna(int k);

        
        /**
         * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
         * palautetaan null, muutoin virheteksti.
         * @param k minkä kentän sisältö asetetaan
         * @param s asetettava sisältö merkkijonona
         * @return null jos ok, muuten virheteksti
         * </pre>
         */
        public abstract String aseta(int k, String s);


        /**
         * Tehdään identtinen klooni tietueesta
         * @return kloonattu tietue
         * @throws CloneNotSupportedException jos kloonausta ei tueta
         * </pre>
         */
        public abstract Tietue clone() throws CloneNotSupportedException;


        /**
         * Palauttaa tietueen tiedot merkkijonona jonka voi tallentaa tiedostoon.
         * @return tietue tolppaeroteltuna merkkijonona 
         * @example
         * </pre>
         */
        @Override
        public abstract String toString();

    }


