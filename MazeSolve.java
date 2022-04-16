import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is a program that reads in a maze text file and then solves the maze using backtracking.
 * @author sethrossman
 * @version 1
 */
public class MazeSolve {
    static String[][] maze;
    static int mazeX = 0;
    static int mazeY = 0;
    static int mazeEndX;
    static int mazeEndY;
    static int recursionCount = 0;

    /**
     * ########## NEXT REVISIONS ##########
     * Flood-fill algorithm
     * Work on main (buildMaze parameters specifically)
     * Input validation for filename
     * Make whole class non-static
     *      Constructor takes in filename to create board
     *      Call using Object(MazeSolve).solveFloodFill || Object(MazeSolve).solveRecursion
     * ########## NEXT REVISIONS ##########
     **/

    /**
     * Creates maze from supplied file.
     * @param filename filename
     * @throws FileNotFoundException e
     */
    public static void buildMaze(String filename) throws FileNotFoundException {
        try {
            File mazeFile = new File("src/maze1.txt");
            Scanner sc = new Scanner(mazeFile);
            mazeY = sc.nextInt();
            mazeX = sc.nextInt();
            maze = new String[mazeY][mazeX];

            // Consume the newline
            sc.nextLine();

            for (int i = 0; i < mazeY; i++) {
                for (int k = 0; k < mazeX; k++) {
                    maze[i][k] = sc.next();
                    if (maze[i][k].equals("F")) {
                        mazeEndX = k;
                        mazeEndY = i;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("FILE DOES NOT EXIST");
        }
    }

    /**
     * This will print the maze.
     **/
    public static void printMaze() {
        System.out.println("### MAZE SOLVE ###");
        for (int i = 0; i < mazeY; i++) {
            for (int k = 0; k < mazeX; k++) {
                System.out.print(" " + maze[i][k] + " ");
            }
            System.out.println();
        }
    }

    /**
     * If the path only has one available spot to move then it has reached a dead end.
     * This needs to be used in conjunction with a call that specifies the next cell to be tested.
     * @param pathX this is the next valid cells x-coordinate
     * @param pathY this is the next valid cells x-coordinate
     * @return t/f if it's a valid path
     */
    public static boolean validPath(int pathX, int pathY) {
        // This function will check if the supplied point is a "1" or "-"
        if (maze[pathY][pathX].equals("1") || maze[pathY][pathX].equals("-")) {
            return false;
        }
        return true;
    }

    /**
     * Not sure what exactly I was thinking with the plotPoint (deleted) function so here is a new one.
     * @param pointX to plot
     * @param pointY to plot
     * @param value what to replace it with 0/1
     */
    public static void setPoint(int pointX, int pointY, int value) {
        if (value == 0) {
            maze[pointY][pointX] = "-";
        } else {
            maze[pointY][pointX] = "-";
        }
    }

    /**
     * New version of above function. (!)
     * @param playerX x-coordinate
     * @param playerY y-coordinate
     * @return if solved or unsolvable
     */
    public static boolean newMazePath(int playerX, int playerY) {
        printMaze();
        recursionCount++;

        // This is not needed because the recursion will check each path regardless
        // !(maze[playerY - 1 + i][playerX - 1 + i].equals("-"))
        for (int i = -1; i < 2; i++) {
            for (int k = -1; k < 2; k++) {
                // Base case
                if (maze[playerY + i][playerX + k].equals("F")) {
                    return true;
                }
                // Recursion case if open path is found and chosen
                //  && newMazePath(playerX + k, playerY + i)
                if (validPath(playerX + k, playerY + i)) {
                    setPoint(playerX + k, playerY + i, 1);
                    return newMazePath(playerX + k, playerY + i);
                }
            }
        }
        return false;
    }

    /**
     * Main function.
     * Read with scanner
     * @param args args args args
     * @throws FileNotFoundException e
     */
    public static void main(String[] args) throws FileNotFoundException {
        // This will be for user input files - NOT WORKING YET - COME BACK TO
        //Scanner fileSc = new Scanner(System.in);

        System.out.println();

        try {
            //File fileFile = new File(String.valueOf(path.toAbsolutePath()));
            buildMaze("ahhaha");
            printMaze();
            System.out.printf("Maze start: (%d, %d)\n", 0, 0);
            System.out.printf("Maze end: (%d, %d)\n", mazeEndX + 1, mazeEndY + 1);
            // Border of ones to prevent ArrayOutOfBounds errors so origin has to be (1,1)
            setPoint(1, 1, 1);
            if (newMazePath(1, 1)) {
                System.out.println("Solved! : [Recursion Count] == " + recursionCount);
            } else {
                System.out.println("You got some work to do");
            }
        } catch (Exception e) {
            System.out.println("Error opening file to build maze");
            e.printStackTrace();
        }
    }
}
