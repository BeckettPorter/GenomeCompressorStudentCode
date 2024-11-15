/******************************************************************************
 *  Compilation:  javac GenomeCompressor.java
 *  Execution:    java GenomeCompressor - < input.txt   (compress)
 *  Execution:    java GenomeCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   genomeTest.txt
 *                virus.txt
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 ******************************************************************************/

/**
 *  The {@code GenomeCompressor} class provides static methods for compressing
 *  and expanding a genomic sequence using a 2-bit code.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 */
public class GenomeCompressor {

    public static final int BITS_PER_CHAR = 2;
    public static final int BITS_PER_LENGTH_STORAGE = 16;

    /**
     * Reads a sequence of 8-bit extended ASCII characters over the alphabet
     * { A, C, T, G } from standard input; compresses and writes the results to standard output.
     */
    public static void compress()
    {
        // Get the initial string from the file.
        String string = BinaryStdIn.readString();
        // Store the length of the string.
        int strLength = string.length();

        // Write the length of the string at the start of the compressed file with a given number of bits.
        BinaryStdOut.write(strLength, BITS_PER_LENGTH_STORAGE);

        // Then go through each character in the string and write it to the compressed file with a given # of bits.
        for (int i = 0; i < strLength; i++)
        {
            BinaryStdOut.write(mapChar(string.charAt(i)), BITS_PER_CHAR);
        }

        BinaryStdOut.close();
    }

    /**
     * Reads a binary sequence from standard input; expands and writes the results to standard output.
     */
    public static void expand()
    {
        // Read the previously written length of the string that we put at the beginning of the compressed file.
        int length = BinaryStdIn.readInt(BITS_PER_LENGTH_STORAGE);

        // Go through the binary file for that length and write it to the new file.
        for (int i = 0; i < length; i++)
        {
            BinaryStdOut.write(reverseMapChar(BinaryStdIn.readChar(BITS_PER_CHAR)));
        }

        BinaryStdOut.close();
    }


    /**
     * Main, when invoked at the command line, calls {@code compress()} if the command-line
     * argument is "-" an {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

    // Helper method that maps the chars of a DNA sequence to values from 0 to 3.
    private static int mapChar(char c)
    {
        switch (c)
        {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
        }
        return -1;
    }

    private static char reverseMapChar(int n)
    {
        switch (n)
        {
            case 0:
                return 'A';
            case 1:
                return 'C';
            case 2:
                return 'G';
            case 3:
                return 'T';
        }
        return '\n';
    }
}