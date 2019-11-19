package dataStructures;

/** class used by BinSort */
public class StudentRecord extends ScoreObject
{
   String name;  // student name

   /** constructor */
   public StudentRecord(String theName, int theScore)
   {
      super(theScore);
      name = theName;
   }

   /** convert to a string */
   public String toString()
      {return String.valueOf(score);}
}
