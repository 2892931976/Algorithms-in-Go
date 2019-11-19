

/** Create an irregular two-dimensional array of type int. */

package misc;

public class IrregularArray 
{

   public static void main(String [] args)
   {
      int numberOfRows = 5;

      // define the size of each of the five rows
      int [] size = {6, 3, 4, 2, 7};

      // declare a two-dimensional array variable
      // and allocate the desired number of rows
      int [][] irregularArray = new int [numberOfRows][];

      // now allocate space for the elements in each row
      for (int i = 0; i < numberOfRows; i++)
         irregularArray[i] = new int [size[i]];

      // use the array like any regular array
      irregularArray[2][3] = 5;
      irregularArray[4][6] = irregularArray[2][3] + 2;
      irregularArray[1][1] += 3;

      // output selected elements
      System.out.println(irregularArray[2][3]);
      System.out.println(irregularArray[4][6]);
      System.out.println(irregularArray[1][1]);
      System.out.println(irregularArray[3][1]);
   }
}
