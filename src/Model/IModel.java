package Model;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.search.ISearchingAlgorithm;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public interface IModel {

    /**
     * Generates a maze according to given dimensions.
     * @param row Number of row.
     * @param column Number of columns.
     * @return Returns a 2-d array maze.
     */
    public int[][] generateMaze(int row, int column);

    /**
     * Solves current open maze.
     * @return Returns 2-d int array: 0 - part of the maze, 1 - part of the solution.
     */
    public ArrayList<int[]> solveMaze();

    /**
     * Returns the current position in the maze as an int array of length 2.
     * Row number at index 0, Column number at index 1.
     * @return int[]
     */
    public int[] getCurrentPosition();

    /**
     * Returns the start position of the maze as an int array of length 2.
     * Row number at index 0, Column number at index 1.
     * @return int[]
     */
    public int[] getStartPosition();

    /**
     * Returns the goal position of the maze as an int array of length 2.
     * Row number at index 0, Column number at index 1.
     * @return int[]
     */
    public int[] getGoalPosition();

    /**
     * Move the character according to KeyCode input.
     * @param step movement direction
     */
    public void moveCharacter(KeyCode step);


    /**
     * @return Return 2-d array containing maze content as 1 and 0 values (1 - wall, 0 - path).
     */
    public int[][] getMaze();

    /**
     * @return Returns the maze as a byte array containing all relevant details.
     */
    public byte[] getMazeByteArray();
}
