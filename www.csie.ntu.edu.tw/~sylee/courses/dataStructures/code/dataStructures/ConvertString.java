
/** convert a character string into a long and an integer */

package dataStructures;

public class ConvertString
{
   /** covert characters 0, 1, and 2 of s to a long 
     * @throws StringIndexOutOfBoundsException when
     * s has fewer than three characters */
   public static long threeToLong(String s)
   {
      // leftmost char
      long answer = s.charAt(0);

      // shift left 16 bits and add in next char
      answer = (answer << 16) + s.charAt(1);

      // shift left 16 bits and add in next char
      return (answer << 16) + s.charAt(2);
   }

   /** covert string s into an integer that depends on all
     * characters of s */
   public static int integer(String s)
   {
      int length = s.length();   // number of characters in s
      int answer = 0;
      if (length % 2 == 1)
      {// length is odd
         answer = s.charAt(length - 1);
         length--;
      }

      // length is now even
      for (int i = 0; i < length; i += 2)
      {// do two characters at a time
         answer += s.charAt(i);
         answer += ((int) s.charAt(i + 1)) << 16;
      }

      return (answer < 0) ? -answer : answer;
   }

   /** test program */
   public static void main(String [] args)
   {
       String s = "abc";
       for (int i = 0; i < 3; i++)
          System.out.println(s.charAt(i) + " " + ((int) s.charAt(i)));

       System.out.println(threeToLong(s));

       s = "abcde";
       System.out.println(s + " " +  integer(s));
   }
}
