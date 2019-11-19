
/** LZW decompression */

package applications;

import java.io.*;
import dataStructures.*;
import utilities.*;
import wrappers.*;

public class Decompress
{
   // top-level nested class
   private static class Element
   {
      // data members
      private int prefix;
      private int suffix;

      // constructor
      private Element( int thePrefix, int theSuffix)
      {
         prefix = thePrefix;
         suffix = theSuffix;
      }
   }
   
   // class data members
   // constants
   final static int MAX_CODES = 4096;  // 2^12
   final static int BYTE_SIZE = 8;
   final static int EXCESS = 4;    // 12 - ByteSize
   final static int ALPHA = 256;   // 2^ByteSize
   final static int MASK = 15;     // 2^EXCESS - 1
   // variables
   static int [] s;                // used to reconstruct text
   static int size;                // size of reconstructed text
   static Element [] h;            // dictionary
   static int leftOver;            // input bits yet to be output
   static boolean bitsLeftOver;
   static BufferedInputStream in;
   static BufferedOutputStream out;
   
   /** create input and output streams */
   private static void setFiles(String [] argv) throws IOException
   {
      String inputFile, outputFile;
      // see if file name provided
      if (argv.length >= 2)
         outputFile = argv[1];
      else
      {// input file name not provided, ask for it
         System.out.println("Enter name of file to decompress");
         System.out.println("Omit the extension .zzz");
         MyInputStream keyboard = new MyInputStream();
         outputFile = keyboard.readString();
      }

      // establish input and output streams
      inputFile = outputFile + ".zzz";
      in = new BufferedInputStream(new FileInputStream(inputFile));
      out = new BufferedOutputStream(new FileOutputStream(outputFile));
   }
   
   /** Lempel-Ziv-Welch decompressor */
   private static void decompress() throws IOException
   {
      int codesUsed = ALPHA; // codes used so far
      s = new int [MAX_CODES];
      h = new Element [MAX_CODES];
   
      // input and decompress
      int pcode = getCode(),  // previous code
          ccode;              // current code
      
      if (pcode >= 0)
      {// input file is not empty
         s[0] = pcode;     // byte for pcode 
         out.write(s[0]); 
         size = 0; // s[size] is first character of
                   // last string output
                   
         do
         {// get another code
            ccode = getCode();
            if (ccode < 0) break;  // no more codes
            if (ccode < codesUsed)
            {// ccode is defined
               output(ccode);
               if (codesUsed < MAX_CODES)
                  // create new code
                  h[codesUsed++] = new Element(pcode, s[size]);
            }
            else
            {// special case, undefined code
               h[codesUsed++] = new Element(pcode, s[size]);
               output(ccode);
            }
            pcode = ccode;
        }  while(true);
      }
      out.close();
      in.close();
   }
   
   /** @return next code from compressed file
     * @return -1 if there is no next code */
   private static int getCode() throws IOException
   {
      int c = in.read();
      if (c == -1) return -1;  // no more codes
   
      // see if any leftover bits from before
      // if yes, concatenate with leftover bits
      int code;
      if (bitsLeftOver)
         code = (leftOver << BYTE_SIZE) + c;
      else
      {// no leftover bits, need more bits to complete code
         int d = in.read();  // another byte
         code = (c << EXCESS) + (d >> EXCESS);
         leftOver = d & MASK;  // save unused bits
      }
      bitsLeftOver = !bitsLeftOver;
      return code;
   }
      
   /** output the byte sequence that corresponds to code */
   private static void output(int code) throws IOException
   {
      size = -1;
      while (code >= ALPHA)
      {// suffix is in the dictionary
         s[++size] = h[code].suffix;
         code = h[code].prefix;
      }
      s[++size] = code;  // code < ALPHA
   
      // decompressed string is s[size] ... s[0]
      for (int i = size; i >= 0; i--)
         out.write(s[i]);
   }
   
   public static void main(String [] argv) throws IOException
   {
      setFiles(argv);
      decompress();
   }
}
