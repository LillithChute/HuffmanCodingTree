import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import encodeanddecode.Cipher;

/**
 * A main program to run the coding tree model.
 */
public class Driver {
  // File input and output variables.
  private static File file;
  private static Map<String, String> dictionary;

  // Console input
  private static String dictionaryString;

  /**
   * This kicks off a simulation of the encode and decode process.
   *
   * @param args THe arguments to run the application.
   */
  public static void main(String[] args) {
    // Get how the user wants to interact.
    gatherUserPreferences();
  }

  /**
   * Use this method to primarily interact with the user and decide how they are going to encode or
   * decode via console or file.
   */
  public static void gatherUserPreferences() {
    Scanner scanner = new Scanner(System.in);
    //Map<String, String> dictionary;

    System.out.println("This is a cipher program that will encode or decode text.");
    System.out.println("You will provide a symbol -> code dictionary.  This can be done either "
            + "by providing it in a file or entering it here.");
    System.out.println("Please format the dictionary as 'symbol=code~symbol=code'.");
    System.out.println("Example formatting: o=0010~y=0011~s=01000.");
    System.out.println("Will you enter the dictionary at the console (Y/N)?");
    boolean flagConsole = validateBoolean(scanner);

    // If true, do everything via the console.
    if (flagConsole) {
      System.out.println("Please enter the dictionary.");
      // Read the dictionary.
      String dictionaryString = scanner.nextLine();

      // Break it up into a map.
      try {
        dictionary = Arrays.stream(dictionaryString.split("~"))
                .map(split -> split.split("="))
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));

        System.out.println("This is the dictionary: " + dictionary.toString());

        System.out.println("Do you want to encode or decode based on the input dictionary "
                + "(encode / decode)?");
        boolean encodeDecode = determineEncodeDecode(scanner);

        System.out.println("Will you enter the text at the console (Y/N)?");
        boolean flagTextAtConsole = validateBoolean(scanner);

        if (flagTextAtConsole) {
          encodeDecodeFromConsoleInput(scanner, dictionary, encodeDecode);
        } else {
          encodeDecodeFromFileInput(scanner, dictionary, encodeDecode);
        }

      } catch (Exception ex) {
        System.out.println("There was a problem with the dictionary entry: " + ex.getMessage());
      }
    } else {
      extractDictionaryFromFile(scanner);
      System.out.println("Do you want to encode or decode based on the input dictionary "
              + "(encode / decode)?");
      boolean encodeDecode = determineEncodeDecode(scanner);

      System.out.println("Will you enter the text at the console (Y/N)?");
      boolean flagTextAtConsole = validateBoolean(scanner);

      if (flagTextAtConsole) {
        encodeDecodeFromConsoleInput(scanner, dictionary, encodeDecode);
      } else {
        encodeDecodeFromFileInput(scanner, dictionary, encodeDecode);
      }
    }
  }

  // region Encoding and decoding helper functions
  private static void encodeDecodeFromFileInput(Scanner scanner, Map<String, String> dictionary,
                                                boolean encodeDecode) {
    String fileContents = null;

    // Read in the dictionary
    System.out.println("\nPlease enter the filename for the plain text or coded text "
            + "(ex: message.txt): ");
    String fileName = scanner.nextLine();

    try {
      fileContents = new String(Files.readAllBytes(Paths.get(fileName)));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Build the cipher
    Cipher runCipher = new Cipher(dictionary);

    if (encodeDecode) {
      String encodedMessage = runCipher.encodeText(fileContents);

      System.out.println("Do you want to write the results to the console (Y) or File (N)");
      boolean flagConsole = validateBoolean(scanner);

      if (flagConsole) {
        // Print to the screen
        System.out.println("Encoded message: " + encodedMessage);
      } else {
        System.out.println("\nPlease enter the filename to write to (ex: message.txt): ");

        while (true) {
          file = new File(getFileName(scanner));

          try {
            if (file.createNewFile()) {
              FileWriter myWriter = new FileWriter(file.getName());
              System.out.println("Encoded message being written to " + file.getName());
              myWriter.write(encodedMessage);
              myWriter.close();
              System.out.println("Success!");
              break;
            } else {
              System.out.println("File already exists. Please use another filename.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred while writing file.");
            e.printStackTrace();
          }
        }
      }
    } else {
      String decodedMessage = runCipher.decodeText(fileContents);

      System.out.println("Do you want to write the results to the console (Y) or File (N)");
      boolean flagConsole = validateBoolean(scanner);

      if (flagConsole) {
        // Print to the screen
        System.out.println("Decoded message: " + decodedMessage);
      } else {
        System.out.println("\nPlease enter the filename to write to (ex: message.txt): ");

        while (true) {
          file = new File(getFileName(scanner));

          try {
            if (file.createNewFile()) {
              FileWriter myWriter = new FileWriter(file.getName());
              System.out.println("Encoded message being written to " + file.getName());
              myWriter.write(decodedMessage);
              myWriter.close();
              System.out.println("Success!");
              break;
            } else {
              System.out.println("File already exists. Please use another filename.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred while writing file.");
            e.printStackTrace();
          }
        }
      }
    }
  }

  private static void encodeDecodeFromConsoleInput(Scanner scanner, Map<String, String> dictionary,
                                                   boolean encodeDecode) {
    if (encodeDecode) {
      System.out.println("Please enter the string to encode.  "
              + "You may only use the symbols provided for the message as displayed above.");

      String messageToEncode = scanner.nextLine();

      // Build the cipher
      Cipher runCipher = new Cipher(dictionary);
      String encodedMessage = runCipher.encodeText(messageToEncode);

      // Print to the screen
      System.out.println("Encoded message: " + encodedMessage);
    } else {
      System.out.println("Please enter the string to decode.  "
              + "You may only use the symbols provided for the message as displayed above.");

      String messageToDecode = scanner.nextLine();

      // Build the cipher
      Cipher runCipher = new Cipher(dictionary);
      String decodedMessage = runCipher.decodeText(messageToDecode);

      // Print to the screen
      System.out.println("Decoded message: " + decodedMessage);
    }
  }
  // endregion

  /**
   * Helper function to get direction from scanner.
   */
  private static boolean determineEncodeDecode(Scanner scanner) {
    String type = scanner.nextLine().toLowerCase().replaceAll(" ", "");

    while (!(type.equals("encode")) && !(type.equals("decode"))) {
      System.out.println("\tInvalid Input. Please enter (encode / decode).");
      type = scanner.nextLine().toLowerCase().replaceAll(" ", "");
    }

    return (type.equals("encode"));
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

  // region File helpers
  private static void extractDictionaryFromFile(Scanner scanner) {
    // Read in the dictionary
    System.out.println("\nPlease enter the filename for the dictionary (ex: dictionary.txt): ");
    String fileName = scanner.nextLine();

    try {
      dictionaryString = new String(Files.readAllBytes(Paths.get(fileName)));

      dictionary = Arrays.stream(dictionaryString.split("~"))
              .map(split -> split.split("="))
              .collect(Collectors.toMap(a -> a[0], a -> a[1]));

      System.out.println("This is the dictionary: " + dictionary.toString());

    } catch (FileNotFoundException e) {
      System.out.println("\tInvalid filename.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String getFileName(Scanner scanner) {
    String fileName = scanner.nextLine().toLowerCase().replaceAll(" ", "");
    while (!(fileName.endsWith(".txt"))) {
      System.out.println("\tInvalid file. Please implement in txt file");
      fileName = scanner.nextLine().toLowerCase().replaceAll(" ", "");
    }

    return fileName;
  }
  // endregion
}
