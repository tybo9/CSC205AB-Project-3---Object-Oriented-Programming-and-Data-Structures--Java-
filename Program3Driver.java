package program3;
/*
 *       File: Program3Driver.java
 *      Class: CSC205
 * Programmer: Jennifer Norby
 *    Purpose: To edit this driver to be able to read a serialized file.
 */

// This program will start the program to create and then solve a maze

import java.util.*;
import java.io.*;

public class Program3Driver
{
    // main method
    public static void main(String[ ] args) throws Throwable
    {
        // create a new Scanner and a variable to hold the instance of MazeSolver
        Scanner kb = new Scanner(System.in);
        MazeSolver mySolver;

        // ask the user for a previous instance to restore
        System.out.println("Please enter the file where a previous MazeSolver was saved");
        System.out.println("(or press ENTER to start over with a new MazeSolver and maze)");
        String solverFilename = kb.nextLine();

        // if they just hit ENTER, then create a new instance of a MazeSolver
        if (solverFilename.length() == 0)
        {
            // ask the user how many rows and columns to use
            System.out.println("Please enter the number of rows and columns the maze should have");
            int numRows = kb.nextInt();
            int numCols = kb.nextInt();

            // flush the ENTER that was left on the input stream by the .nextInt()
            kb.nextLine();

            // create the new instance of MazeSolver, passing in the hardcoded numRows and numCols to use with the maze
            mySolver = new MazeSolver(numRows, numCols);
        }
        
        else
        {
            // if users entered a file name, restore the previous MazeSolver and call it mySolver
            //opens up the save file
            FileInputStream FIS = new FileInputStream(solverFilename);
            ObjectInputStream OIS = new ObjectInputStream(FIS);
            //reads the serialized MazeSolver into the mySolver variable
            mySolver = (MazeSolver)OIS.readObject();  
            OIS.close();  //closes OIS
            FIS.close();  //closes FIS
            System.out.println("\nRecovering save file: " + solverFilename + "\n");
        }
        // now mySolver has either been created or restored.   Tell it to solve()
        mySolver.solve();
    }
}
