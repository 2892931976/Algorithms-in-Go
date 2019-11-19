/** LZW compression */

package applications;

import java.io.*;
import dataStructures.*;
import utilities.*;
import wrappers.*;

public class Compress
{
   // class data members
   // constants
   final static int D = 4099;      // hash function divisor
   final static int MAX_CODES = 4096;  // 2^12
   final static int BYTE_SIZE = 8;
   final static int EXCESS = 4;    // 12 - ByteSize
   final static int ALPHA = 256;   // 2^ByteSize
   final static int MASK1 = 255;   // ALPHA - 1
   final static int MASK2 = 15;    // 2^EXCESS - 1
   // variables
   static int leftOver;      // code bits yet to be output
   static boolean bitsLeftOver;
   static BufferedInputStream in;
   static BufferedOutputStream out;
   
   /** create input and output streams */
   private static void setFiles(String [] argv) throws IOException
   {
      String inputFile, outputFile;
      // see if file name provided
      if (argv.length >= 2)
         inputFile = argv[1];
      else
      {// input file name not provided, ask for it
         System.out.println("Enter name of file to compress");
         MyInputStream keyboard = new MyInputStream();
         inputFile = keyboard.readString();
      }

      // establish input and output streams
      in = new BufferedInputStream(new FileInputStream(inputFile));
      outputFile = inputFile + ".zzz";
      out = new BufferedOutputStream(new FileOutputStream(outputFile));
   }
   
   /** Lempel-Ziv-Welch compressor */
   private static void compress() throws IOException
   {
      // define and initialize the code dictionary
      HashChains h = new HashChains(D);
      for (int i = 0; i < ALPHA; i++)
         // initialize code table
         h.put(new MyInteger(i), new MyInteger(i));

      int codesUsed = ALPHA;
   
      // input and compress
      int c = in.read();          // first byte of input
      if (c != -1)
      {// input file is not empty
         int pcode = c;
         c = in.read();      // second byte
         while (c != -1)     // not at end of file
         {// process byte c
            int k = (pcode << BYTE_SIZE) + c;
            // see if code for k is in the dictionary
            MyInteger e = (MyInteger) h.get(new MyInteger(k));
            if (e == null)
            {// k is not in the table
               output(pcode);
               if (codesUsed < MAX_CODES) // create new code
                  h.put(new MyInteger((pcode << BYTE_SIZE) + c),
                        new MyInteger(codesUsed++));
               pcode = c;
            }
            else pcode = e.intValue();
            c = in.read();
         }
      
         // output last code(s)
         output(pcode);
         if (bitsLeftOver)
            out.write(leftOver << EXCESS);
      }
      in.close();
      out.close();
   }
  
   
   /** output 1 byte and save remaining half byte */
   private static void output(int pcode) throws IOException
   {
      int c, d;
      if (bitsLeftOver)
      {// half byte remains from before
         d = pcode & MASK1; // right BYTE_SIZE bits
         c = (leftOver << EXCESS) + (pcode >> BYTE_SIZE);
         out.write(c);
         out.write(d);
         bitsLeftOver = false;
      }
      else
      {// no bits remain from before
         leftOver = pcode & MASK2; // right EXCESS bits
         c = pcode >> EXCESS;
         out.write(c);
         bitsLeftOver = true;
      }
   }
   
   public static void main(String [] argv) throws IOException
   {
      setFiles(argv);
      compress();
   }
}
