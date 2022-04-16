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

    /**
     * ########## NEXT REVISIONS ##########
     * Input validation for filename
     * ########## NEXT REVISIONS ##########
     */

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
     */
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

     * This method will plot a point at the supplied coordinates and then print grid.
     * @param pointX x-coordinate
     * @param pointY y-coordinate

    public static void plotPoint(int pointX, int pointY) {
        int tempPlayerX = playerX;
        int tempPlayerY = playerY;
        System.out.println(pointX + " " + pointY);
        maze[pointY][pointX] = "-";
        maze[tempPlayerY][tempPlayerX] = "-";
        playerX = pointX;
        playerY = pointY;
        printMaze();
    }


     * This provides reusable neighbor checks
     * @param pathX
     * @param pathY
     * @return the cell instruction to place point depending on if the cell is not

    public static int neighborCheck(int pathX, int pathY) {
        System.out.println("(" + pathX + ", " + pathY + ")");
        // boolean neighborUp = false;
        if (!(pathY == 0)) {
            if (maze[pathY - 1][pathX].equals("1") || maze[pathY - 1][pathX].equals("-")) {
                return 1;
            }
        } else {
            if (maze[pathY][pathX].equals("1") || maze[pathY][pathX].equals("-")) {
                return 1;
            }

            // boolean neighborDown = false;
            if (!(pathY == mazeY)) {
                if (!(maze[pathY + 1][pathX].equals("1")) || maze[pathY][pathX + 1].equals("-")) {
                    return 2;
                }
            }

            // boolean neighborLeft = false;
            if (!(pathX == 0)) {
                if (maze[pathY][pathX - 1].equals("1") || maze[pathY][pathX - 1].equals("-")) {
                    return 3;
                }
            }

            // boolean neighborRight = false;
            if (!(pathX == mazeX)) {
                if (maze[pathY][pathX + 1].equals("1") || maze[pathY][pathX + 1].equals("-")) {
                    return 4;
                }
            }
        }
        return 5;
    }


     * This function checks if there is a dash in the points vicinity
     * @param pointX pointX
     * @param pointY pointY
     * @return t/f if point is "tethered"

    public static boolean dashTetherCheck(int pointX, int pointY) {
        // NEED TO ADD CORNER CASE IN ADDITION TO LEFT RIGHT TOP BOTTOM SIDE
        if (pointX == 0) { // Left
            System.out.println(pointX + ": " + pointY);
            if (maze[pointY - 1][pointX].equals("-") || maze[pointY + 1][pointX].equals("-")
                    || maze[pointY][pointX].equals("-") || maze[pointY][pointX + 1].equals("-")) {
                return true;
            } else {
                return false;
            }
        } else if (pointX == mazeX) { // Right
            if (maze[pointY - 1][pointX].equals("-") || maze[pointY + 1][pointX].equals("-")
                    || maze[pointY][pointX - 1].equals("-") || maze[pointY][pointX].equals("-")) {
                return true;
            } else {
                return false;
            }
        } else if (pointY == 0) { // Bottom
            if (maze[pointY][pointX].equals("-") || maze[pointY + 1][pointX].equals("-")
                    || maze[pointY][pointX - 1].equals("-") || maze[pointY][pointX + 1].equals("-")) {
                return true;
            } else {
                return false;
            }
        } else if (pointY == mazeY) { // Top
            if (maze[pointY - 1][pointX].equals("-") || maze[pointY][pointX].equals("-")
                    || maze[pointY][pointX - 1].equals("-") || maze[pointY][pointX + 1].equals("-")) {
                return true;
            } else {
                return false;
            }
        } else { // Non border check
            if (maze[pointY - 1][pointX].equals("-") || maze[pointY + 1][pointX].equals("-")
                    || maze[pointY][pointX - 1].equals("-") || maze[pointY][pointX + 1].equals("-")) {
                return true;
            } else {
                return false;
            }
        }
    }


     * This function implements recursion to solve a provided maze.
     * Base case: When the maze "path" reaches the end of maze
     * Recursion case:

    public static boolean mazePath() {
        int pathX = playerX;
        int pathY = playerY;

        if (!maze[pathY][pathX].equals("1")) {
            // Base case
            if (maze[pathX][pathY].equals("F")) {
                return true;
            } else {
                if (dashTetherCheck(pathX, pathY)) {
                    switch (neighborCheck(pathX, pathY)) {
                        case 1:
                            playerX++;
                            playerY++;
                            plotPoint(pathX, pathY - 1);
                            break;
                        case 2:
                            playerX++;
                            playerY++;
                            plotPoint(pathX, pathY + 1);
                            break;
                        case 3:
                            playerX++;
                            playerY++;
                            plotPoint(pathX - 1, pathY);
                            break;
                        case 4:
                            playerX++;
                            playerY++;
                            plotPoint(pathX + 1, pathY);
                            break;
                        default:
                            playerX++;
                            playerY++;
                            return mazePath();
                    }
                } else {
                    playerX++; // Even if no piece is placed move player
                    playerY++;
                }
            }

    //////
             // Recursive Case
             for (pathY = 0; pathY < mazeY; pathY++) {
             for (pathX = 0; pathX < mazeX; pathX++) {
             if (maze[pathY][pathX].equals("-") && dashEdge(pathX, pathY)) {
             switch (neighborCheck(pathX, pathY)) {
             case 1:
             System.out.println("1");
             plotPoint(pathX, pathY - 1);
             playerX = pathX;
             playerY = pathY;
             break;
             case 2:
             System.out.println("2");
             plotPoint(pathX, pathY + 1);
             playerX = pathX;
             playerY = pathY;
             break;
             case 3:
             System.out.println("3");
             plotPoint(pathX - 1, pathY);
             playerX = pathX;
             playerY = pathY;
             break;
             case 4:
             System.out.println("4");
             plotPoint(pathX + 1, pathY);
             playerX = pathX;
             playerY = pathY;
             break;
             }
             return mazePath();
             }
             }
             }
             //////
        } else {
            playerX++;
            playerY++;
            return mazePath();
        }
        return true;
    }
    **/

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
        /**
        if (pathX == 0 || pathY == 0) {
            return false;
        } else if (pathX == 1 && pathY == 1) { // Prevents starting point loop
            return false;
        }
        int openingCount = 0;
        System.out.println(pathX + ", " + pathY);
        for (int i = -1; i < 2; i++) {
            for (int k = -1; k < 2; k++) {
                // if (!(i == 0 && k == 0)) { // This is so it doesn't count itself - OBSOLETE becasue itself will be a dash
                    if (!(maze[pathY + i][pathX + k].equals("1") || maze[pathY + i][pathX + k].equals("-"))) {
                        openingCount += 1;
                    }
                //}
                //System.out.println(maze[pathY + i][pathX + k]);
            }
        }
        if (openingCount > 0) {
            return true;
        }
        return false;
         **/
        return true;
    }

    /**
     * Not sure what exactly I was thinking with the plotPoint function so here is a new one.
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
                /**
                if (!(i == 0 && k == 0)) {
                    if (validPath(playerX + k, playerY + i)) {
                        System.out.println((playerX) + " : " + (playerY));
                        setPoint(playerX + k, playerY + i, 1);
                        newMazePath(playerX + k, playerY + i);
                        //return true;
                    } else if (maze[playerY + i][playerX + k].equals("-")) {
                        setPoint(playerX + k, playerY + i, 0);
                    }
                }
                 **/
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
                System.out.println("Solved : () !!!!");
            } else {
                System.out.println("You got some work to do");
            }
        } catch (Exception e) {
            System.out.println("Error opening file to build maze");
            e.printStackTrace();
        }
    }
}
