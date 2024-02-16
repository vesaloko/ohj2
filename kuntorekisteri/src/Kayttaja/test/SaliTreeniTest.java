package Kayttaja.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import Kayttaja.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2023.04.24 16:40:08 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class SaliTreeniTest {



  // Generated by ComTest BEGIN
  /** testGetNimi89 */
  @Test
  public void testGetNimi89() {    // SaliTreeni: 89
    SaliTreeni jalkapäivä = new SaliTreeni(); 
    jalkapäivä.vastaaTreeniSali(); 
    { String _l_=jalkapäivä.getNimi(),_r_="Jalka päivä.*"; if ( !_l_.matches(_r_) ) fail("From: SaliTreeni line: 92" + " does not match: ["+ _l_ + "] != [" + _r_ + "]");}; 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** 
   * testClone130 
   * @throws CloneNotSupportedException when error
   */
  @Test
  public void testClone130() throws CloneNotSupportedException {    // SaliTreeni: 130
    SaliTreeni treeni= new SaliTreeni(); 
    treeni.parse("   3  |  Ankka Aku   | 123"); 
    SaliTreeni kopio = treeni.clone(); 
    assertEquals("From: SaliTreeni line: 135", treeni.toString(), kopio.toString()); 
    treeni.parse("   4  |  Ankka Tupu   | 123"); 
    assertEquals("From: SaliTreeni line: 137", true, treeni.toString().equals(treeni.toString())); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString202 */
  @Test
  public void testToString202() {    // SaliTreeni: 202
    SaliTreeni treeni = new SaliTreeni(); 
    treeni.parse("   3  |  Ankka Aku   | 030201-111C"); 
    assertEquals("From: SaliTreeni line: 205", true, treeni.toString().startsWith("3|Ankka Aku|030201-111C|"));  // on enemmäkin kuin 3 kenttää, siksi loppu |
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testRekisteroi297 */
  @Test
  public void testRekisteroi297() {    // SaliTreeni: 297
    SaliTreeni aku1 = new SaliTreeni(); 
    assertEquals("From: SaliTreeni line: 299", 0, aku1.getTunnusNro()); 
    aku1.rekisteroi(); 
    SaliTreeni aku2 = new SaliTreeni(); 
    aku2.rekisteroi(); 
    int n1 = aku1.getTunnusNro(); 
    int n2 = aku2.getTunnusNro(); 
    assertEquals("From: SaliTreeni line: 305", n2-1, n1); 
  } // Generated by ComTest END
}