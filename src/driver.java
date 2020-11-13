import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import encodeanddecode.Cipher;

public class driver {

  public static void main(String[] args) {

    while (true) {
      Scanner scanner = new Scanner(System.in);
      Map<String, String> dictionary;

      System.out.println("This is a cipher program that will encode or decode text.");
      System.out.println("You will provide a symbol -> code dictionary.  This can be done either "
              + "by providing it in a file or entering it here.");
      System.out.println("Please format the dictionary as 'symbol=code, symbol=code'.");
      System.out.println("Will you enter the dictionary here (Y/N)?");

      boolean flagConsole = validateBoolean(scanner);

      if (flagConsole) {
        System.out.println("Please enter the dictionary.");
        // Read the dictionary.
        String dictionaryString = scanner.nextLine();

        // Break it up into a map.
        try{
          dictionary = Arrays.stream(dictionaryString.split(" "))
                  .map(split -> split.split("="))
                  .collect(Collectors.toMap(
                          a -> a[0], // key
                          a -> a[1]  // value
                  ));

          System.out.println("Dictionary: " + dictionary.toString());

          System.out.println("Please enter the string to encode.  "
                  + "You may only use the symbols provided for the message.");

          String messageToEncode = scanner.nextLine();

          // Build the cipher
          Cipher runCipher = new Cipher(dictionary);
          String encodedMessage = runCipher.encodeText(messageToEncode);

          // Print to the screen
          System.out.println("Encoded message: " + encodedMessage);
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }

      }
    }
  }

  private static boolean validateBoolean(Scanner scanner) {
    String answer = scanner.nextLine().toLowerCase().replaceAll(" ", "");

    while (!(answer.equals("yes")) && !(answer.equals("y"))
            && !(answer.equals("no")) && !(answer.equals("n"))
    ) {
      System.out.println("\tThat input is not valid.");
      answer = scanner.nextLine().toLowerCase().replaceAll(" ", "");
    }

    return (answer.equals("yes") || answer.equals("y"));
  }
}
