package Kayttaja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Kayttaja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.03.14 14:18:37 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class KuntorekisteriTest {


  // Generated by ComTest BEGIN
  /** 
   * testLisaa56 
   * @throws SailoException when error
   */
  @Test
  public void testLisaa56() throws SailoException {    // Kuntorekisteri: 56
    Kuntorekisteri kuntorekisteri = new Kuntorekisteri(); 
    SaliTreeni selka = new SaliTreeni(), jalka = new SaliTreeni(); 
    selka.rekisteroi(); jalka.rekisteroi(); 
    assertEquals("From: Kuntorekisteri line: 61", 0, kuntorekisteri.getTreeneja()); 
    kuntorekisteri.lisaa(selka); assertEquals("From: Kuntorekisteri line: 62", 1, kuntorekisteri.getTreeneja()); 
    kuntorekisteri.lisaa(jalka); assertEquals("From: Kuntorekisteri line: 63", 2, kuntorekisteri.getTreeneja()); 
    kuntorekisteri.lisaa(selka); assertEquals("From: Kuntorekisteri line: 64", 3, kuntorekisteri.getTreeneja()); 
    assertEquals("From: Kuntorekisteri line: 65", 3, kuntorekisteri.getTreeneja()); 
    assertEquals("From: Kuntorekisteri line: 66", selka, kuntorekisteri.annaSaliTreeni(0)); 
    assertEquals("From: Kuntorekisteri line: 67", jalka, kuntorekisteri.annaSaliTreeni(1)); 
    assertEquals("From: Kuntorekisteri line: 68", selka, kuntorekisteri.annaSaliTreeni(2)); 
    try {
    assertEquals("From: Kuntorekisteri line: 69", selka, kuntorekisteri.annaSaliTreeni(3)); 
    fail("Kuntorekisteri: 69 Did not throw IndexOutOfBoundsException");
    } catch(IndexOutOfBoundsException _e_){ _e_.getMessage(); }
    kuntorekisteri.lisaa(selka); assertEquals("From: Kuntorekisteri line: 70", 4, kuntorekisteri.getTreeneja()); 
    kuntorekisteri.lisaa(jalka); assertEquals("From: Kuntorekisteri line: 71", 5, kuntorekisteri.getTreeneja()); 
  } // Generated by ComTest END
}