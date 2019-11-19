
/** histogramming using a binary search tree */


package applications;

import java.lang.reflect.*;
import dataStructures.*;
import wrappers.*;
import utilities.*;

public class TreeHistogramming
{
   // top-level member class
   public static class ElementType
   { 
      // data members
      int key,    // element value
          count;  // frequency

      // constructor
      public ElementType(int theKey)
      {
         key = theKey;
         count = 1;
      }

      // output method
      public String toString()
         {return "[" + String.valueOf(key) + " " +
                  String.valueOf(count) + "]";}
   }
   
   // static data member
   static Method theAdd1;  // method to increase count by 1

   // static initializer
   static
   {
      try
      {
         Class histo = TreeHistogramming.class;
         Class [] paramType = {Object.class};
         theAdd1 = histo.getMethod("add1", paramType);
      }
      catch (Exception e) {}
         // exception not possible
   }
   
   /** increment the count of e by 1 */
   public static void add1(Object e)
      {((ElementType) e).count++;}
   
   /** histogram using a search tree */
   public static void main(String [] args)
   {
      // define keyboard to be the standard input stream
      MyInputStream keyboard = new MyInputStream();

      System.out.println("Enter number of elements");
      int n = keyboard.readInteger();
   
      // input elements and enter into theTree
      BinarySearchTreeWithVisit theTree = new BinarySearchTreeWithVisit();
      for (int i = 1; i <= n; i++)
      {
         System.out.println("Enter element " + i);
         ElementType e = new ElementType(keyboard.readInteger());
         // put e in tree unless match already there
         // in latter case increase count by 1
         theTree.put(new MyInteger(e.key), e, theAdd1);
      }
   
      // output distinct elements and their counts
      System.out.println("Distinct elements and frequencies are");
      theTree.ascend();
      System.out.println();
   }
}
