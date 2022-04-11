import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    static int playerX = 0;
    static int playerY = 0;

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
        System.out.printf("Maze start: (%d, %d)\n", 0, 0);
        System.out.printf("Maze end: (%d, %d)\n", mazeEndX + 1, mazeEndY + 1);
    }

    /**
     * This method will plot a point at the supplied coordinates and then print grid.
     * @param pointX x-coordinate
     * @param pointY y-coordinate
     */
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

    /**
     * This provides reusable neighbor checks
     * @param pathX
     * @param pathY
     * @return the cell instruction to place point depending on if the cell is not
     */
    public static int neighborCheck(int pathX, int pathY) {
        System.out.println("(" + pathX + ", " + pathY + ")");
        // boolean neighborUp = false;
        if (!(pathY == 0)) {
            if (maze[pathY - 1][pathX].equals("1") || maze[pathY][pathX - 1].equals("-")) {
                return 1;
            }
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

        return 5;
    }

    /**
     * This function checks if there is a dash in the points vicinity
     * @param pointX pointX
     * @param pointY pointY
     * @return t/f if point is "tethered"
     */
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

    /**
     * This function implements recursion to solve a provided maze.
     * Base case: When the maze "path" reaches the end of maze
     * Recursion case:
     */
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

            /**
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
             **/
        } else {
            playerX++;
            playerY++;
            return mazePath();
        }
        return true;
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
            if (mazePath()) {
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
