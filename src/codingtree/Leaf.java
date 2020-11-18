package codingtree;

/**
 * This is the leaf of the code tree and it will hold the character associated with the
 * cipher code.
 */
public class Leaf implements TreeOperations {
  private final String textCharacter;

  public Leaf(String characterText) {
    textCharacter = characterText;
  }

  @Override
  public void addToTree(String cipher, String textCharacter) throws Exception {
    // This is the interface tradeoff of having to implement a method that is not
    // necessary or can't be done in this class.
    throw new Exception("This data can't be added to the tree.");

  }

  @Override
  public Tuple2 getCharacterBasedOnCipher(String cipher) {
    return new Tuple2(textCharacter, cipher);
  }
}
