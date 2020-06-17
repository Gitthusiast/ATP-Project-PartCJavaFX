package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;


public class MyModel extends Observable implements IModel{

    private Maze maze;
    private Server MazeGeneratorServer;
    private Server MazeSolverServer;
    private ArrayList<int[]> solutionArray;

    private int characterRow;
    private int characterColumn;

    public MyModel() {

        maze = null;
        startServers();
    }

    private void startServers(){

        MazeGeneratorServer = new Server(5400, 2000, new ServerStrategyGenerateMaze());
        MazeSolverServer = new Server(5401, 2000, new ServerStrategySolveSearchProblem());

        MazeGeneratorServer.start();
        MazeSolverServer.start();
    }

    public void stopServers(){

        MazeGeneratorServer.stop();
        MazeSolverServer.stop();
    }

    /**
     * Generates a maze according to given dimensions.
     * @param row    Number of row.
     * @param column Number of columns.
     * @return Returns a 2-d array maze.
     */
    @Override
    public int[][] generateMaze(int row, int column) {

        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, column};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[row*column + 50 /* assuming max maze size 1000x1000 */]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);

                        characterRow = maze.getStartPosition().getRowIndex();
                        characterColumn = maze.getStartPosition().getColumnIndex();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (maze == null)
            return null;

        setChanged();
        notifyObservers("generatedMaze");

        return maze.getMaze();
    }

    /**
     * Solves current open maze.
     *
     * @return Returns 2-d int array: 0 - part of the maze, 1 - part of the solution.
     */
    @Override
    public ArrayList<int[]> solveMaze() {


        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        solutionArray = new ArrayList<>();
                        for (AState mazeSolutionStep : mazeSolutionSteps) {

                            String[] dimensions = mazeSolutionStep.toString().split("[{},]");
                            int[] position = new int[2];
                            position[0] = Integer.parseInt(dimensions[1]);
                            position[1] = Integer.parseInt(dimensions[2]);

                            solutionArray.add(position);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        return solutionArray;
    }

    /**
     * Returns the current position in the maze as an int array of length 2.
     * Row number at index 0, Column number at index 1.
     *
     * @return int[]
     */
    @Override
    public int[] getCurrentPosition() {
        int[] res = new int[2];
        res[0] = characterRow;
        res[1] = characterColumn;

        return res;
    }

    /**
     * Returns the start position of the maze as an int array of length 2.
     * Row number at index 0, Column number at index 1.
     *
     * @return int[]
     */
    @Override
    public int[] getStartPosition() {

        int[] position = new int[2];
        position[0] = maze.getStartPosition().getRowIndex();
        position[1] = maze.getStartPosition().getColumnIndex();
        return position;
    }


    /**
     * Returns the goal position of the maze as an int array of length 2.
     * Row number at index 0, Column number at index 1.
     *
     * @return int[]
     */
    @Override
    public int[] getGoalPosition() {

        int[] position = new int[2];
        position[0] = maze.getGoalPosition().getRowIndex();
        position[1] = maze.getGoalPosition().getColumnIndex();
        return position;
    }

    /**
     * Move the character according to KeyCode input.
     * @param step movement direction
     */
    @Override
    public void moveCharacter(KeyCode step) {

        int[][] aMaze = maze.getMaze();
        boolean steppedOnAWall = false;

        switch (step){

            case NUMPAD8: //UP
                characterRow --;
                if(characterRow <= -1 || aMaze[characterRow][characterColumn] == 1){
                    characterRow++; //we don't want to allow illegal moves
                }
                break;
            case NUMPAD6: //RIGHT
                characterColumn++;
                if(characterColumn >= maze.getColumnNumber() || aMaze[characterRow][characterColumn] == 1){
                    characterColumn--;
                }
                break;
            case NUMPAD2: //DOWN
                characterRow++;
                if(characterRow >= maze.getRowNumber() || aMaze[characterRow][characterColumn] == 1){
                    characterRow--;
                }
                break;
            case NUMPAD4: //LEFT
                characterColumn--;
                if(characterColumn <= -1 || aMaze[characterRow][characterColumn] == 1){
                    characterColumn++;
                }
                break;
            case NUMPAD7: //UP LEFT
                characterColumn--;
                characterRow--;

                if(characterColumn <= -1 || characterRow <= -1 || aMaze[characterRow][characterColumn] == 1 || (aMaze[characterRow][characterColumn+1]==1 && aMaze[characterRow+1][characterColumn]==1)){

                    characterColumn++;
                    characterRow++;
                }
                break;
            case NUMPAD9: //UP RIGHT
                characterRow--;
                characterColumn++;

                if(characterRow <= -1 || characterColumn >= maze.getColumnNumber() || aMaze[characterRow][characterColumn] == 1 || (aMaze[characterRow][characterColumn-1]==1 && aMaze[characterRow+1][characterColumn]==1)){

                    characterRow++;
                    characterColumn--;
                }
                break;
            case NUMPAD3: //DOWN RIGHT
                characterColumn++;
                characterRow++;

                if(characterRow >= maze.getRowNumber() || characterColumn >= maze.getColumnNumber() || aMaze[characterRow][characterColumn] == 1 || (aMaze[characterRow-1][characterColumn]==1 && aMaze[characterRow][characterColumn-1]==1)){

                    characterColumn--;
                    characterRow--;
                }
                break;
            case NUMPAD1: //DOWN LEFT
                characterRow++;
                characterColumn--;

                if(characterRow >= maze.getRowNumber() || characterColumn <= -1 || aMaze[characterRow][characterColumn] == 1 || (aMaze[characterRow-1][characterColumn]==1 && aMaze[characterRow][characterColumn+1]==1)){

                    characterRow--;
                    characterColumn++;
                }
                break;
        }

        setChanged();
        notifyObservers("movedCharacter");
    }

    /**
     * @return Return 2-d array containing maze content as 1 and 0 values (1 - wall, 0 - path).
     */
    public int[][] getMaze(){ return maze.getMaze(); }

    /**
     * @return Returns the maze as a byte array containing all relevant details.
     */
    public byte[] getMazeByteArray() { return maze.toByteArray(); }

    /**
     * Updates the model with a given maze byte array and current character position.
     * @param decompressedMaze Maze in a byte array form
     * @param characterRow
     * @param characterColumn
     */
    public void updateMaze(byte[] decompressedMaze, int characterRow, int characterColumn) {

        maze = new Maze(decompressedMaze);
        this.characterRow = characterRow;
        this.characterColumn = characterColumn;

        setChanged();
        notifyObservers("generatedMaze");
    }
}
