import static org.junit.Assert.assertEquals;

import encodeanddecode.Cipher;
import encodeanddecode.EncodeDecode;
import huffman.Huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * This will test functions that a user will use when interacting with the program.
 */
public class TestCipher {
  private Map<String, String> basicDictionary;
  private Map<String, String> nonPrefixDictionary;
  private Map<String, String> randomSymbolDictionary;
  List<Character> cipherCharacters;

  @Before
  public void setup() {
    // Huffman list of characters
    cipherCharacters = new ArrayList<>();
    cipherCharacters.add('0');
    cipherCharacters.add('1');

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

  @Test(expected = IllegalArgumentException.class)
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

  @Test
  public void huffmanTest() {
    Huffman cipherTree = new Huffman();
    TestData testData = new TestData();
    Map<String, String> dictionary = cipherTree.createHuffmanTree(
            testData.getDeclarationOfIndependence(), cipherCharacters);
    assertEquals("{\n"
            + "=10110101,  =111, &=1011110000010, '=1011110000011, ,=000011, -=0111001001, "
            + ".=10111101, :=1011110011, ;=1011110001, A=011100110, B=0000100101, C=011100011, "
            + "D=101111000010, E=101111000011, F=011100111, G=011100001, H=00001011, "
            + "I=00001001101, J=10111100100, K=000010011000, L=101101001, M=10111100101, "
            + "N=0000100111, O=0000101000, P=000010000, Q=000010011001, R=0111001000, S=101101000, "
            + "T=011100010, W=000010101, a=0101, b=000001, c=110110, d=01111, e=001, f=110111, "
            + "g=011101, h=11010, i=0110, j=011100000, k=011100101, l=00010, m=101110, n=1000, "
            + "o=1010, p=101100, q=0000101001, r=0100, s=1001, t=1100, u=00011, v=1011011, "
            + "w=000000, x=000010001, y=1011111, z=0000100100, \uFEFF=101111000000}",
            dictionary.toString());
  }
}
