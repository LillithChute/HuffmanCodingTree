import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import encodeanddecode.Cipher;
import encodeanddecode.EncodeDecode;

import static org.junit.Assert.assertEquals;

public class TestCipher {
  private Map<String, String> basicDictionary;
  private Map<String, String> nonPrefixDictionary;
  private Map<String, String> randomSymbolDictionary;

  @Before
  public void setup() {
    // Set up a dictionary based on description in assignment
    basicDictionary = new HashMap<>();
    basicDictionary.put("a", "011");
    basicDictionary.put("b", "100");
    basicDictionary.put("c", "11");
    basicDictionary.put("d", "101");
    basicDictionary.put("e", "001");
    basicDictionary.put("f", "010");

    nonPrefixDictionary = new HashMap<>();
    nonPrefixDictionary.put("a", "011");
    nonPrefixDictionary.put("b", "100");
    nonPrefixDictionary.put("c", "11");
    nonPrefixDictionary.put("d", "101");
    nonPrefixDictionary.put("e", "001");
    nonPrefixDictionary.put("f", "10");

    randomSymbolDictionary = new HashMap<>();
    randomSymbolDictionary.put("a", "01");
    randomSymbolDictionary.put("b", "$t");
    randomSymbolDictionary.put("c", "k-");
    randomSymbolDictionary.put("d", "u@");
    randomSymbolDictionary.put("e", "701");
    randomSymbolDictionary.put("f", "p0");
    randomSymbolDictionary.put("g", "%fg5");
    randomSymbolDictionary.put("h", "+=>");
    randomSymbolDictionary.put("i", "<?5");
    randomSymbolDictionary.put("j", "!~");
    randomSymbolDictionary.put("k", "34");
    randomSymbolDictionary.put(" ", "42");
  }

  @Test
  public void addTest() {
    Map<String, String> dictionary = new HashMap<>();
    dictionary.put("a", "011");
    EncodeDecode cipherTree = new Cipher(dictionary);
    String a = cipherTree.decodeText("011");
    assertEquals("a", a);
  }

  @Test
  public void decodeTest() {
    EncodeDecode cipherTree = new Cipher(basicDictionary);
    String text = cipherTree.decodeText("01110010011101010");
    assertEquals("abbcdf", text);
  }

  @Test
  public void decodeRandomTest() {
    EncodeDecode cipherTree = new Cipher(randomSymbolDictionary);
    String text = cipherTree.decodeText("$t01u@42%fg5<?5%fg54234<?5u@");
    assertEquals("bad gig kid", text);
  }

  @Test(expected = NullPointerException.class)
  public void decodeBadCharacterTest() {
    EncodeDecode cipherTree = new Cipher(basicDictionary);
    String text = cipherTree.decodeText("011100100x11101010");
    assertEquals("abbcdf", text);
  }

  @Test
  public void encodeTest() {
    EncodeDecode cipherTree = new Cipher(basicDictionary);
    String text = cipherTree.encodeText("abbcdf");
    assertEquals("01110010011101010", text);
  }

  @Test
  public void encodeRandomSymbolTest() {
    EncodeDecode cipherTree = new Cipher(randomSymbolDictionary);
    String text = cipherTree.encodeText("bad gig kid");
    assertEquals("$t01u@42%fg5<?5%fg54234<?5u@", text);
  }

  @Test
  public void encodeBadTest() {
    EncodeDecode cipherTree = new Cipher(basicDictionary);
    String text = cipherTree.encodeText("abbcdfg");
    assertEquals("01110010011101010", text);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonPrefixTest() {
    EncodeDecode cipherTree = new Cipher(nonPrefixDictionary);
  }

  @Test
  public void encodeEmptyTest() {
    EncodeDecode cipherTree = new Cipher(basicDictionary);
    String encodeText = cipherTree.encodeText("");
    assertEquals("", encodeText);
  }
}
