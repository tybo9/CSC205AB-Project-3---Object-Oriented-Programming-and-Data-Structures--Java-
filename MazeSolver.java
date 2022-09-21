package program3;
/*
 *       File: MazeSolver.java
 *      Class: CSC205
 * Programmer: Jennifer Norby
 *    Purpose: To write a class that solves a maze!
 */

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MazeSolver implements Serializable
{
    //-----DATA-----
    private Maze aMaze;
    boolean [][] mazeArray;
    Stack<Maze.Direction> mazeStack;
    
    //-----CONSTRUCTORS-----
    public MazeSolver(int numRows, int numCols)
    {
        aMaze = new Maze(numRows, numCols);  //creates Maze
        mazeArray = new boolean[numRows][numCols]; //Creates a default boolean array
        mazeStack = new Stack<Maze.Direction>();  //creates a new stack to hold directions
        aMaze.buildMaze(10);  //smaller delay, so it can build faster
    }

    //-----METHODS-----
    public void solve() //solves maze
    {
        MazeDisplay display = new MazeDisplay(aMaze);  //creates MazeDisplay 
        aMaze.setSolveAnimationDelay(150);  //sets the solve rate in milliseconds
        Scanner kb = new Scanner(System.in);  //Create a new Scanner.
        String sqExit;
        
        //prompts user to either save, quit, or hit enter
        System.out.println("Press 'Q' to quit the game.");
        System.out.println("Press 'S' to save current progress.");
        System.out.println("Press 'ENTER' to keep going.\n");
        
        //default starting location is set to true
        mazeArray [aMaze.getCurrentRow()][aMaze.getCurrentCol()] = true;

        //will loop everything until maze is solved
        do
        {
            int arrayRows = aMaze.getCurrentRow();  //sets rows to current row
            int arrayCols = aMaze.getCurrentCol();  //sets cols to current column
            sqExit = kb.nextLine();           //flushes input stream

            if (sqExit.equalsIgnoreCase("S"))       //saves the maze
            {
                try 
                {
                    System.out.println("\nPlease enter a file name to save progress:"); //prompts user for file name
                    String saveFile = kb.nextLine();    //stores user input in variable
                    FileOutputStream FOS = new FileOutputStream(saveFile);  //creates new save file
                    ObjectOutputStream OOS = new ObjectOutputStream(FOS);   //writes object to file
                    OOS.writeObject(this);
                    OOS.close();    //closes OOS
                    FOS.close();    //closes FOS
                    System.out.println("\nSuccessfully saved the file as \"" + saveFile + "\".\n");                    
                } 
                
                catch (IOException ex)
                {
                    System.out.println("Error saving file.");   //shows error, re-prints options
                    System.out.println("Press 'Q' to quit the game.");
                    System.out.println("Press 'S' to save current progress.");
                    System.out.println("Press 'ENTER' to keep going.\n");
                }
            }

            if (sqExit.equalsIgnoreCase("Q"))  //quits the maze
            {
                System.out.println("\nSee you next time! Thanks for playing!\n");
                //System.exit(0);       //closes application
            }

            //now maze will cycle through each direction every time you hit enter
            //  to see which direction is legal
            if (aMaze.isOpen(Maze.Direction.DOWN) && mazeArray [arrayRows + 1][arrayCols] == false)
            {
                aMaze.move(Maze.Direction.DOWN);
                mazeStack.push(Maze.Direction.DOWN);
                mazeArray[arrayRows][arrayCols] = true;
            }

            else if (aMaze.isOpen(Maze.Direction.UP) && mazeArray [arrayRows - 1][arrayCols] == false)
            {
                aMaze.move(Maze.Direction.UP);
                mazeStack.push(Maze.Direction.UP);
                mazeArray[arrayRows][arrayCols] = true;
            }

            else if (aMaze.isOpen(Maze.Direction.RIGHT) && mazeArray [arrayRows][arrayCols + 1] == false)
            {
                aMaze.move(Maze.Direction.RIGHT);
                mazeStack.push(Maze.Direction.RIGHT);
                mazeArray[arrayRows][arrayCols] = true;
            }

            else if (aMaze.isOpen(Maze.Direction.LEFT) && mazeArray [arrayRows][arrayCols - 1] == false)
            {
                aMaze.move(Maze.Direction.LEFT);
                mazeStack.push(Maze.Direction.LEFT);
                mazeArray[arrayRows][arrayCols] = true;
            }
            
            //if it hits a wall or moves have been visited, it will pop the
            //  stack and move backwards
            else  
            {
                Maze.Direction otherDir = mazeStack.pop();  //pops the stack

                //moves in direction opposite of what was popped
                switch(otherDir)  
                {
                    case UP:        aMaze.move(Maze.Direction.DOWN);
                                    mazeArray[arrayRows][arrayCols] = true;
                                    break;
                    case DOWN:      aMaze.move(Maze.Direction.UP);
                                    mazeArray[arrayRows][arrayCols] = true;
                                    break;
                    case RIGHT:     aMaze.move(Maze.Direction.LEFT);
                                    mazeArray[arrayRows][arrayCols] = true;
                                    break;
                    case LEFT:      aMaze.move(Maze.Direction.RIGHT);
                                    mazeArray[arrayRows][arrayCols] = true;
                                    break;                                                
                    default:        break;     
                }
            }
        } 
        while (!aMaze.goalReached() && !sqExit.equalsIgnoreCase("Q"));
        
        if (aMaze.goalReached())
            System.out.println("Maze is solved!");    //prints only when maze is solved
    }
}