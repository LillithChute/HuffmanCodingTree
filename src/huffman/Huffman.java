package huffman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * This will use the Huffman algorithm to encode plain text.
 */
public class Huffman {
  // Instead of building a priority queue, use the built in.
  private PriorityQueue<String> pQueue;

  public Map<String, String> createHuffmanTree(String text, List<Character> cipherCharacters) {
    // Validation section.
    if (text == null) {
      throw new IllegalArgumentException("The text cannot be null");
    }

    // Huffman algorithm needs enough symbols to do any encoding.  Based on it's implementation,
    // it needs two at minimum.
    if (cipherCharacters.size() < 2) {
      throw new IllegalArgumentException("The number of cipher codes must be two or more.");
    }

    // Huffman utilizes a frequency map.
    Map<String, Integer> frequency = new HashMap<>();
    Map<String, String> dictionaryToEncode = new HashMap<>();

    IntStream.range(0, text.length()).mapToObj(i -> String.valueOf(text.charAt(i)))
            .forEach(cipherKey -> {
      // Count the frequency.  Put it in the frequency Map.  If we haven't seen it,
      // it gets zero and then add one when we see a repeat of a character.
      frequency.put(cipherKey, frequency.getOrDefault(cipherKey, 0) + 1);

      // Now add to the encoding dictionary
      dictionaryToEncode.put(cipherKey, "");
    });

    // Set up the priority queue as required by the assignment.
    initializePriorityQueue(frequency);

    // Implement the actual algorithm as per assignment.
    runHuffman(cipherCharacters, frequency, dictionaryToEncode);

    return dictionaryToEncode;
  }

  // region Private methods.
  private void initializePriorityQueue(Map<String, Integer> frequency) {
    this.pQueue = new PriorityQueue<>((a, b) -> {
      // Based on the algorithm, compare based on frequency first.  If those
      // are equivalent compare by alphabetical.
      if (!frequency.get(a).equals(frequency.get(b))) {
        return frequency.get(a).compareTo(frequency.get(b));
      } else {
        return a.compareTo(b);
      }
    });

    // Add all the stuff to the priority queue.
    pQueue.addAll(frequency.keySet());
  }

  private void runHuffman(List<Character> cipherCharacters, Map<String, Integer> frequency,
                          Map<String, String> dictionaryToEncode) {
    // Loop through the queue.
    while (pQueue.size() > 1) {
      // Tracking variables.
      int freq = 0;
      StringBuilder addCipherText = new StringBuilder();

      // Loop over the cipher codes
      for (Character cipher : cipherCharacters) {
        // pop from the queue
        String pQueueCipher = pQueue.remove();

        // Build the dictionary by putting the cipher code in it.
        for (int i = 0; i < pQueueCipher.length(); i++) {
          dictionaryToEncode.put(String.valueOf(pQueueCipher.charAt(i)),
                  cipher + dictionaryToEncode.get(String.valueOf(pQueueCipher.charAt(i))));
        }

        // Add the frequency for each cipher.
        freq += frequency.get(pQueueCipher);

        // Add to the encoded string.
        addCipherText.append(pQueueCipher);
      }

      // add to the frequency table.
      frequency.put(addCipherText.toString(), freq);

      // Put into the priority queue.
      pQueue.add(addCipherText.toString());
    }
  }
  // endregion
}
