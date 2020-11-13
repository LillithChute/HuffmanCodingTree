package encodeanddecode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import codingtree.CharRemainder;
import codingtree.Node;
import codingtree.TreeOperations;

/**
 * This is the implementation of the actual cipher work of encoding and decoding.
 */
public class Cipher implements EncodeDecode{
  // We need the root.
  private final TreeOperations treeBase;

  // We need something to hold the incoming dictionary.
  private final Map<String, String> dictionaryForEncoding;

  public Cipher(Map<String, String> encodingDictionary) {
    if (encodingDictionary == null) {
      throw new IllegalArgumentException("The encoding dictionary can't be null.");
    }

    // Make sure that this is a prefix code.
    prefixCodeValidation(encodingDictionary);

    // Set the encoding dictionary up.
    dictionaryForEncoding = new HashMap<>(encodingDictionary);

    // Add the incoming dictionary to the tree.
    treeBase = initializeTree(encodingDictionary);
  }

  @Override
  public String encodeText(String textToEncode) {
    if (textToEncode == null) {
      throw new IllegalArgumentException("Text to encode can't be null.");
    }

    StringBuilder encodedMessage = new StringBuilder();

    int i = 0;
    while (i < textToEncode.length()) {
      // Grab the character
      String cipherKey = String.valueOf(textToEncode.charAt(i));
      encodedMessage.append(dictionaryForEncoding.get(cipherKey));
      i++;
    }

    return encodedMessage.toString();
  }

  @Override
  public String decodeText(String textToDecode) {
    // Make sure we actually have a text to decode.
    if (textToDecode == null) {
      throw new IllegalArgumentException("Encoded text can't be null.");
    }

    CharRemainder cr = treeBase.getCharacterBasedOnCipher(textToDecode);
    StringBuilder decodedText = new StringBuilder(cr.ch);

    // As a character is removed from the list, we have the rest of the list remaining, so keep
    // looping through it until done.
    while (cr.remainder.length() > 0) {
      cr = treeBase.getCharacterBasedOnCipher(cr.remainder);
      decodedText.append(cr.ch);
    }

    return decodedText.toString();
  }

  // region Private methods
  private void prefixCodeValidation(Map<String, String> encodingDictionary) {
    // Break the dictionary into a list to sort it.
    List<String> cipherValues = new ArrayList<>(encodingDictionary.values());

    // Sort them in order
    Collections.sort(cipherValues);

    // Iterate over the code and check that no code contains the starting value of the next
    // code in the list.

    // We only want prefix codes.  Make sure this is that.
    IntStream.range(0, cipherValues.size() - 1)
            .filter(i -> cipherValues.get(i + 1)
                    .startsWith(cipherValues.get(i)))
            .forEachOrdered(i -> {
      throw new IllegalArgumentException("This is NOT a prefix code.");
    });
  }

  private TreeOperations initializeTree(Map<String, String> encodingDictionary) {
    final TreeOperations treeBase;

    // Set the root of the tree.
    treeBase = new Node();

    // Add the incoming dictionary to the tree as the key value pair.
    encodingDictionary.forEach((key, value) -> {
      try {
        treeBase.addToTree(value, key);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    });

    return treeBase;
  }
  //endregion
}
