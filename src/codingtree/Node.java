package codingtree;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a 'group node' of the code tree.
 */
public class Node implements TreeOperations {
  // We need to marry the incoming code with the nodes containing the text character.
  // Essentially this string is mapped to the node.
  private final Map<String, TreeOperations> nodes;

  public Node() {
    nodes = new HashMap<>();
  }

  @Override
  public void addToTree(String cipher, String textCharacter) throws Exception {
    // Strip off the first character.
    String cipherKey = cipher.substring(0, 1);

    // Last character.  This is the end. Put in the leaf the character.
    if (cipher.length() == 1) {
      nodes.put(cipherKey, new Leaf(textCharacter));

      // Do we have this in the tree or not?
      // If not, we need to add a new node and put the key in it.
    } else {
      if (!nodes.containsKey(cipherKey)) {
        nodes.put(cipherKey, new Node());
      }

      // If it already exists, carry on.
      nodes.get(cipherKey).addToTree(cipher.substring(1), textCharacter);
    }
  }

  @Override
  public Tuple2 getCharacterBasedOnCipher(String cipher) {
    // Decoding process.
    return nodes.get(cipher.substring(0, 1))
            .getCharacterBasedOnCipher(cipher.substring(1));
  }
}
