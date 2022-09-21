CSC205AB
Program 3


In this program, you will write two classes: 
•	a Stack class that works just like Java’s built-in Stack class
•	a class called MazeSolver, which will be able to create a Maze, set up a MazeDisplay, and then solve it. 
You are given 3 other classes to work with and possibly change slightly when it is time to implement Serialization.
•	Program3Driver - which starts the program
•	Maze – which is a maze
•	MazeDisplay – which graphically displays the maze it is given.
After everything is working, you will add Serialization in Part5 so you can save your MazeSolver at any time and read it back in later.

Please read all parts carefully: 

Part1 (30 pts):   You should write your own Stack class.   It should work exactly like Java’s built-in Stack class.  You can find the documentation for Java’s Stack class in the    java.sun.com    website that we have been looking at all semester.   You are given a tester, which simply creates tests and shows results from both your Stack and Java’s Stack.

Part2 (just background, but very important so you will know how to call the Maze’s methods):   Look at the Maze.java file; it contains the code for the Maze.  You do not have to know or understand all the code for the Maze class, but it uses a feature called enumerated data types that you will use extensively. 
 
•	At the very end of the Maze class is defined an enum called Direction.   When working with a Maze, the directions that you can use are UP, DOWN, LEFT, and RIGHT.   Since Direction is defined inside Maze, you have to refer to it as Maze.Direction and refer to the various directions as Maze.Direction.UP etc.  You  can use enumerated data types as a type in the same way that you would use other types or classes.  For example, you could have:

o	Maze.Direction  whichWay; 		//whichWay is a Maze.Direction; can have a value of 
//Maze.MazeDirection.LEFT for example.
o	Stack<Maze.Direction> myStack;	//Generics
o	whichWay = Maze.Direction.UP;      	//note

This will be extremely important, as the Maze methods that you have available are expecting Maze.Directions…

•	For background only (not needed to know or use) is a class that is defined at the very end of Maze.java.  It is a custom type of exception called IllegalMazeMoveException.  I am simply pointing it out because it is very easy to create your own exceptions.   IllegalMazeMoveException is a subclass of IllegalArgumentException and will inherit all the data and methods it needs to work.  A constructor had to be written, but by calling super(str), it just does whatever its parent would do if it received the str.  So we don’t have to know exactly how exceptions work in order to write our own because all of the functionality is inherited.  The Maze class will throw this exception if an illegal move is made.

Part3 (just background, familiarizing yourself with Maze and MazeDisplay).  You are given the Maze class and the MazeDisplay class.   You can create and work with a Maze by itself, but when you also create a MazeDisplay (passing in the Maze), then it becomes animated.   Your MazeSolver will do similar things.

Maze aMaze = new Maze(5, 8);				//creates a 5x8 Maze
aMaze.buildMaze(75);					//builds it, animation delay is set to 75 milliseconds
							//if you want it faster, make the delay smaller
MazeDisplay  display = new MazeDisplay(aMaze);	//creates a MazeDisplay with aMaze in it.  Since aMaze 
//has already been built, no animation will be shown yet. 

…Or you could try  this code, where the order is slightly different:

Maze aMaze = new Maze(8, 10);			//creates a 8x10 Maze
MazeDisplay  display = new MazeDisplay(aMaze);	//creates a MazeDisplay with aMaze in it.  Since build 
//comes after it is displayed, it will show it being built.
aMaze.buildMaze(140);					//builds it, animation delay is set to 140 milliseconds
							//if you want it faster, make the delay smaller


Part4 (solving the Maze, 40 pts)

You are to write a program called MazeSolver.java.   It should have:
•	Class data that stores a Maze and also store the data structures you will use to solve it.   To solve it, you will use your Stack and also a data structure to keep track of which cells in the Maze have been “visited” (see the algorithm below).   This class data will be eligible to be serialized in the last part of the program.   NOTE:  you will also create an instance of a MazeDisplay, but do not declare it in the class data.  It uses the Thread class, which is not Serializable.   So declare it locally in the solve() method.
•	A parameterized constructor that will receive a number of rows and a number of columns, both as ints.  The constructor will:
	Create the new instance of a Maze that you declared in the constructor, passing in the number of rows and the number of columns. 
	Tell your Maze instance to do a .buildMaze(n);  where n is the delay in milliseconds.   The larger n is the slower the Maze will be built.  You can choose any delay you want.
	Create the other data structures that are declared in the class data (the Stack and the data structure to keep track of whether or not a cell has been “visited”)
•	A method called solve(), which will contain the logic to actually solve the Maze.  It should do the following:
	Create a new instance of a MazeDisplay, passing in the Maze that has already been created.  
	Tell the new Maze to setSolveAnimationDelay, passing in how long it should wait between moves.  This is so the animation does not happen so fast that you cannot watch it.  You can choose any delay you want.  
	Once the Maze is built and displayed, you are to write code to solve it.  The algorithm is described below.  When you implement it, you cannot change or add code to the Maze class, but only use Maze’s existing public methods.  They are:

//-------- getCurrentRow - returns the current (real) row
public int getCurrentRow()

//-------- getCurrentCol - returns the current (real) col
public int getCurrentCol()
 
//-------- isOpen - returns true if there is no wall in the direction that is passed in
public boolean isOpen(Direction direction)

// -------- move - receives a Direction and moves there if OK.  
public boolean move(Direction direction)

//-------- goalReached - returns true if the maze is solved (current location is the goal)
public boolean goalReached()

//------- setSolveAnimationDelay - sets the delay (milliseconds) for the maze being solved (in case its 
//          animated)
public void setSolveAnimationDelay(int theDelay)



The basic algorithm it to try going UP, DOWN, LEFT, or RIGHT to legally move through the Maze and taking care to go to a cell (location) that you have already been to.  If there is no new location to go to, you have to use the Stack to backtrack.  This can be done as follows:


Mark the current (starting) cell you are at as “visited.”

Repeat the following actions until the goal location is reached
{
If it is open (there is not a wall) in the UP direction (Maze.Direction.UP) and you have not visited the cell above you (the UP direction), then push Maze,Direction.UP onto your Stack and mark the cell you arrived at as “visited.”   Tell the maze instance to move UP.

Else if it is open (there is not a wall) in the DOWN direction (Maze.Direction.DOWN) and you have not visited the cell below you (the DOWN direction), then push Maze.Direction.DOWN onto your Stack and mark the cell you arrived at as “visited.”  Tell the maze instance to move DOWN.

Else ….(try the same thing for the LEFT direction.  Same logic)  

Else …(try the same thing for the RIGHT direction.  Same logic)

Else – you have come to a dead end.  Pop the Stack to see the direction you came from.  Tell the maze instance to move in the opposite direction, which will accomplish the backtracking.
}

After the loop ends successfully, print “Maze is solved.”


Part5 (Serialization, 10 pts):  Your Part4 should have been implemented using a loop, which has this basic logic:
do
{
     <your logic to move LEFT, RIGHT, UP, DOWN, or backtrack>
}  while ( <the goal is not reached >;

Once Part4 is working, change the code to add the ability to stop in the middle of solving the maze and either quit or save its state through Serialization.   You will have to do three things:

1)	Change the class definition in the Maze class so that it can be Serialized.  That is the only line you should change in Maze.java.

2)	Change the loop in your MazeSolver so that the user can enter input each time with a kb.nextLine() to control what happens.  Specifically:

	Before you start the loop to solve the maze, tell the user (once) that they will enter Q to quit, S to save, and just ENTER to keep going.  Be sure that you receive the input each time as a String (so that you can easily check for “Q” or “S” while ignoring case and also so the user can just hit ENTER for an empty String.  (Remember that the Scanner has already read in 2 ints so an ENTER is left on the input stream; be sure and flush it).

	The loop itself check whether the goal has been reached and also whether the user said to quit.  So the logic of:
Repeat while the goal location is not reached
	should change to: 
Repeat while the goal location is not reached and the user does not say Q


	So inside the loop, the code should stop and wait for user input.  Do not prompt the user each time; instead just wait for the input (Q, S, or ENTER).  

	If the user inputs S (ignore case), then your program should ask the user what file to save it to.  The code should then Serialize your instance of MazeSolver (this) into that file.  Please USE THE EXACT FILENAME THAT THE USER TYPES IN (do not add your own extension such as .dat to it)

	The input has to be in the black DOS window (when using Textpad).

3)	Change Program3Driver so that it will restore a MazeSolver if the user types the file name of where it has been Serialized.   See the Program3Driver.java code.


Comments and formatting:   Be sure to comment your classes, methods, and logic.   Your variable names should be meaningful and also follow Java conventions.  Also indent correctly and use appropriate white space and variable names.


Please submit via Canvas:   Your Stack.java, MazeSolver.java, and your updated Maze.java and Program3Driver.java  

