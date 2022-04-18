import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is a program that reads in a maze text file and then solves the maze using backtracking.
 * @author sethrossman
 * @version 1
 */
public class MazeSolve {
    String[][] maze;
    int mazeX = 0;
    int mazeY = 0;
    int mazeEndX;
    int mazeEndY;
    int recursionCount = 0;

    /**
     * ########## NEXT REVISIONS ##########
     * Take out diagonal moves < DONE
     * Make class win function
     * Create separate testing file using asserts to run through testing
     * Flood-fill algorithm (and my interpretation of it vs. algorithm)
     * Work on main (buildMaze parameters specifically) < DONE
     * Input validation for filename
     * Make whole class non-static < DONE
     *      Constructor takes in filename to create board
     *      Call using Object(MazeSolve).solveFloodFill || Object(MazeSolve).solveRecursion
     * ########## NEXT REVISIONS ##########
     **/

    /**
     * This is the MazeSolve constructor that initializes the board for the instance
     * @param filename the filename
     */
    public MazeSolve(String filename) throws FileNotFoundException {
        try {
            buildMaze("src/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error opening file to build maze");
        }
    }

    /**
     * Creates maze from supplied file.
     * @param filename filename
     * @throws FileNotFoundException e
     */
    public void buildMaze(String filename) throws FileNotFoundException {
        try {
            File mazeFile = new File(filename);
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
    public void printMaze() {
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
    public boolean validPath(int pathX, int pathY) {
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
    public void setPoint(int pointX, int pointY, int value) {
        if (value == 0) {
            maze[pointY][pointX] = "0";
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
    public boolean newMazePath(int playerX, int playerY) {
        printMaze();
        recursionCount++;

        if (validPath(playerX, playerY - 1)) {
            // Base case
            if (maze[playerY - 1][playerX].equals("F")) {
                return true;
            }

            setPoint(playerX, playerY - 1, 1);
            if (newMazePath(playerX, playerY - 1)) {
                return true;
            } else {
                if (maze[playerY - 1][playerX].equals("-")) {
                    setPoint(playerX, playerY - 1, 0);
                }
            }
        }

        if (validPath(playerX - 1, playerY)) {
            // Base case
            if (maze[playerY][playerX - 1].equals("F")) {
                return true;
            }

            setPoint(playerX - 1, playerY, 1);
            if (newMazePath(playerX - 1, playerY)) {
                return true;
            } else {
                if (maze[playerY][playerX - 1].equals("-")) {
                    setPoint(playerX - 1, playerY, 0);
                }
            }
        }

        if (validPath(playerX + 1, playerY)) {
            // Base case
            if (maze[playerY][playerX + 1].equals("F")) {
                return true;
            }
            setPoint(playerX + 1, playerY, 1);
            if (newMazePath(playerX + 1, playerY)) {
                return true;
            } else {
                if (maze[playerY][playerX + 1].equals("-")) {
                    setPoint(playerX + 1, playerY, 0);
                }
            }
        }

        if (validPath(playerX, playerY + 1)) {
            // Base case
            if (maze[playerY + 1][playerX].equals("F")) {
                return true;
            }

            setPoint(playerX, playerY + 1, 1);
            if (newMazePath(playerX, playerY + 1)) {
                return true;
            } else {
                if (maze[playerY + 1][playerX].equals("-")) {
                    setPoint(playerX, playerY + 1, 0);
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
        System.out.println("Enter filename");
        Scanner fileSc = new Scanner(System.in);
        System.out.println();

        MazeSolve maze = new MazeSolve(fileSc.next());
        System.out.printf("Maze start: (%d, %d)\n", 0, 0);
        System.out.printf("Maze end: (%d, %d)\n", maze.mazeEndX + 1, maze.mazeEndY + 1);

        // Border of ones to prevent ArrayOutOfBounds errors so origin has to be (1,1)
        // Easier than edge cases lol
        maze.setPoint(1, 1, 1);

        // I should probably abstract into a class win function
        if (maze.newMazePath(1, 1)) {
            System.out.println("Solved! : [Recursion Count] == " + maze.recursionCount);
        } else {
            System.out.println("You got some work to do");
        }
    }
}
