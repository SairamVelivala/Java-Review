

/**
 *
 * @author sairam
 * Java Project Kill2 program 
 */
public class Kill2 {

  
    public static void main(String[] args) {
        
 String stringInput; // Defining a variable to store the string. 
       if (args.length != 0) // This Block to verify the length on input string. 
        {
            stringInput = args[0]; // To store the input from commandline arguments. 

            if (stringInput.length() >= 2) 
            {
                /* If  lengeth of the string is equal to or more than two characters
                   Program enters this if loop. 
                */
               StringBuilder secondPosition = new StringBuilder(stringInput); // Input string is copied into StringBuilder object called secondPosition
			   secondPosition.deleteCharAt(1); // To delete the character at second position. 
               System.out.println("After Deleting CharAt 2nd position: " + secondPosition);
            }
            else 
            {
                // If the given input is less than two characters, control passes to else block. 
                System.out.println("String should have atleast two characters");
                System.out.print("Please Re-Run the program");
            }
        }
         else
        {
            // If given string is empty than control passes to this block and program is terminated.
            System.out.println("String cannot be empty");
            System.out.print("Please Re-Run the program");
        } 
    }
}