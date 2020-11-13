package encodeanddecode;

/**
 * This contains the operations for this cryptography application.  Primarily the act of encoding
 * and decoding text.
 */
public interface EncodeDecode {

  /**
   * This takes basic text and encodes it.
   *
   * @param textToEncode The text that is being encoded.
   * @return An encoded string.
   */
  String encodeText(String textToEncode);

  /**
   * This takes a string of encoded text and decodes it.
   *
   * @param textToDecode The encoded text.
   * @return Decoded text.
   */
  String decodeText(String textToDecode);
}
