# Coding Trees
The goal of this assignment is to design and implement a program that can encode and decode data using prefix encodings.

## Design
For the purposes of this application the idea is to implement the interfaces/classes to be able to 
encode and decode these types of coding prefix sets.  This application can do the following:
1. Decode messages given a *symbol->code* dictionary containing a *prefix code*.
2. Encode messages given a *symbol->code* dictionary containing a *prefix code*.
3. Handle code sets with any number of symbols, not just binary.
4. Read a message of arbitrary length and be able to generate a Huffman encoding (Proven in test suite).
5. Handle any character set including punctuation, line breaks, spaces, etc. 

## How to run the application
This application, from the perspective of the user uses a coding tree for decoding messages and a 
dictionary map for the encoding process.  The user can work with either the console or through the 
use of files containing text, codes, and prefix dictionaries.

The following are the options the user is presented with:
1. On prompting, pick whether to enter the prefix code via console entry or in a file.
2. If user selects console:
    * Enter the prefix code.
    * User is prompted to encode or decode based on the entered dictionary.  
    * Based on either selection the user enters the code or plain text and the program returns the encoded
      message or decoded message to the console.
    
3. If user selects file (via picking 'N'):
    * Enter the name of the file containing the prefix code.
    * User is prompted to encode or decode based on the entered dictionary.
    * User is asked whether they want to enter the text at console or through a file containing
      the text.
    * The user is asked whether they want the result displayed on the console or written to the file.   

There are a lot of combinations of the above that can be done, but these instructions should 
present a general use scenario.
