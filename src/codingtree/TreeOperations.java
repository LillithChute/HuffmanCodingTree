package codingtree;

/**
 * These are the operations performed by the decode process using a coding tree structure.
 */
public interface TreeOperations {

  /**
   * This takes a cipher and a character and adds it to the tree.
   *
   * @param cipher The cipher.
   * @param textCharacter The character to replace with the cipher.
   */
  void addToTree(String cipher, String textCharacter) throws Exception;

  /**
   * Given a cipher, return the character it is associated with.
   *
   * @param cipher The code.
   * @return The character.
   */
  Tuple2 getCharacterBasedOnCipher(String cipher);
}
