package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.Solution;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;


public class MyModel extends Observable implements IModel{

    private IMazeGenerator generator;
    private ISearchingAlgorithm solver;
    private Maze maze;
    private Position currentPosition;
    private Server MazeGeneratorServer;
    private Server MazeSolverServer;
    private int[][] solutionArray;

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

    private void stopServers(){

        MazeGeneratorServer.stop();
        MazeSolverServer.stop();
    }

    /**
     * Generates a maze according to given dimensions.
     *
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
        return maze.getMaze();
    }

    /**
     * Solves current open maze.
     *
     * @return Returns 2-d int array: 0 - part of the maze, 1 - part of the solution.
     */
    @Override
    public int[][] solveMaze() {


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
                        solutionArray = new int[maze.getRowNumber()][maze.getColumnNumber()];
                        for (AState mazeSolutionStep : mazeSolutionSteps) {

                            String[] dimensions = mazeSolutionStep.toString().split("\\{},");
                            int row = Integer.parseInt(dimensions[0]);
                            int column = Integer.parseInt(dimensions[1]);

                            solutionArray[row][column] = 1;
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
        return new int[0];
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
        position[1] = maze.getGoalPosition().getColumnIndex();
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
        position[0] = maze.getStartPosition().getRowIndex();
        position[1] = maze.getGoalPosition().getColumnIndex();
        return position;
    }
}
