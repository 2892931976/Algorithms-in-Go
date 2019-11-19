

/** getting and invoking a method */

package misc;

import java.lang.reflect.*;
import wrappers.*;
import utilities.*;

public class NewArray1D
{
   // instance data member
   Object [] a;

   /** input objects of type theClass and store in an array */
   public void inputArray(Method inputMethod, MyInputStream stream)
   {
      try
      {
         // input number of elements and create an array of that size
         System.out.println("Enter number of elements");
         int n = stream.readInteger();
         a = new Object [n];
      
         // input the elements
         Object [] inputMethodArgs = {stream};
         for (int i = 0; i < n; i++)
         {
            System.out.println("Enter element " + (i+1));
            a[i] = inputMethod.invoke(null, inputMethodArgs);
         }
      }
      catch (Exception e)
      {
         System.out.println(e);
         throw new IllegalArgumentException("Array1D.inputArray");
      }
   }

   /** test inputArray */
   public static void main(String [] args)
   {
      NewArray1D z = new NewArray1D();

      // input elements are of type MyInteger, get a
      // reference to MyInteger.input
      Class [] parameterTypes = {MyInputStream.class};
      Method inputMethod = null;
      try
      {
         inputMethod = 
                MyInteger.class.getMethod("input", parameterTypes);
      }
      catch (Exception e)
      {
         // not possible
      }

      // input the elements from the standard input stream
      z.inputArray(inputMethod, new MyInputStream());

      // print the elements
      System.out.print("The array elements are ");
      for (int i = 0; i < z.a.length; i++)
         System.out.print(z.a[i] + " ");
      System.out.println();
   }
}
