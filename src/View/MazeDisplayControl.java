package View;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MazeDisplayControl extends Canvas {

    private int rowNum;
    private int colNum;
    private int[][] maze;
    private boolean showSolution = false;
    private ArrayList<int[]> solutionList;
    int[] charcterPosition;


    public void setMaze(int[][] maze) { this.maze = maze; }
    public void setCharcterPosition(int rowIndex, int columnIndex){

        charcterPosition[0] = rowIndex;
        charcterPosition[1] = columnIndex;
    }

    public void drawMaze(){

        double canvasWidth = this.getWidth();
        double canvasHeight = this.getHeight();

        double cellWidth = canvasWidth/colNum;
        double cellHeight = canvasHeight/rowNum;

        GraphicsContext graphicsContext = getGraphicsContext2D(); //get the relevant graphic context so that we can draw on owr canvas
        for(int i=0; i<rowNum; i++){
            for(int j=0; j<colNum; j++){
                if(maze[i][j] == 1) {
                    graphicsContext.setFill(Color.BROWN);
                    graphicsContext.fillRect(i * cellWidth, j * cellHeight,cellWidth,cellHeight);
                }
                else{
                    graphicsContext.setFill(Color.RED);
                    graphicsContext.fillRect(i * cellWidth, j * cellHeight,cellWidth,cellHeight);
                }
            }
        }

        if (showSolution){
            graphicsContext.setFill(Color.AZURE);

            for (int[] position : solutionList) {

                graphicsContext.setFill(Color.AZURE);
                graphicsContext.fillRect(position[0] * cellWidth, position[1] * cellHeight, cellWidth, cellHeight);
            }

        }

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(charcterPosition[0] * cellWidth,charcterPosition[1] * cellHeight, cellWidth, cellHeight);


    }

    public boolean isShowSolution() {
        return showSolution;
    }

    public void setShowSolution(boolean showSolution) {
        this.showSolution = showSolution;
    }
}