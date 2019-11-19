
package misc;
import wrappers.*;
import utilities.*;

public class GenericAbc
{
   public static Computable abc(Computable a, Computable b, Computable c)
   {
      Computable t = (Computable) a.add(b.multiply(c));
      return (Computable) t.add(b.divide(c));
   }
   
   public static void main(String [] args)
   {
      MyInteger x, y, z;
      x = new MyInteger(2);
      y = new MyInteger(3);
      z = new MyInteger(4);
      System.out.println(abc(x, y, z));
   }
}
