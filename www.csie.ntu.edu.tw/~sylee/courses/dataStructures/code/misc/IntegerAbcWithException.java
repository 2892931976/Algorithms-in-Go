

package misc;

public class IntegerAbcWithException
{
   public static int abc(int a, int b, int c)
   {
      if (a <= 0 || b <= 0 || c <= 0)
         throw new IllegalArgumentException
               ("All parameters must be > 0");
      else
         return a + b * c + b / c;
   }
   
   public static void main(String [] args)
   {
      try {System.out.println(abc(2, -3, 4));}
      catch (IllegalArgumentException e)
      {// a bad argument was passed to abc
         System.out.println
            ("The parameters to abc were 2, -3, and 4");
         System.out.println(e);
      }
      catch (Throwable e)
      {// some other exception occurred
         System.out.println(e);
      }
      finally
      {// this code gets executed whether or not
       // an exception is thrown in the try block
         System.out.println("Thanks for trying this program");
      }
   }
}
