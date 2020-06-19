package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MazeDisplayControl extends Canvas {

    private int rowNum;
    private int colNum;
    private int[][] maze;
    private boolean showSolution = false;
    private ArrayList<int[]> solutionList;
    private final int[] characterPosition = new int[2];
    private final int[] goalPosition = new int[2];

    private Image wallImage;
    private Image pathImage;
    private Image characterImage;
    private Image goalImage;
    private Image solutionPathImage;

    private Color backgroundWallColor;
    private Color backgroundPathColor;

    public void setMaze(int[][] maze) {
        this.maze = maze;
        rowNum = maze.length;
        colNum = maze[0].length;
    }

    public void setCharcterPosition(int rowIndex, int columnIndex){

        characterPosition[0] = rowIndex;
        characterPosition[1] = columnIndex;

    }

    public void setGoalPosition(int rowIndex, int columnIndex){

        goalPosition[0] = rowIndex;
        goalPosition[1] = columnIndex;

    }

    public void setSolutionList(ArrayList<int[]> solutionList) { this.solutionList = solutionList; }

    public void setWallImage(Image wallImage) { this.wallImage = wallImage; }

    public void setPathImage(Image pathImage) { this.pathImage = pathImage; }

    public void setCharacterImage(Image characterImage) { this.characterImage = characterImage; }

    public void setGoalImage(Image goalImage) { this.goalImage = goalImage; }

    public void setSolutionPathImage(Image solutionPathImage) { this.solutionPathImage = solutionPathImage; }

    public void setBackgroundWallColor(Color backgroundWallColor) { this.backgroundWallColor = backgroundWallColor; }

    public void setBackgroundPathColor(Color backgroundPathColor) { this.backgroundPathColor = backgroundPathColor; }

    public void drawMaze(){

        double canvasWidth = this.getWidth();
        double canvasHeight = this.getHeight();

        double cellWidth = canvasWidth/colNum;
        double cellHeight = canvasHeight/rowNum;

        GraphicsContext graphicsContext = getGraphicsContext2D(); //get the relevant graphic context so that we can draw on owr canvas
        graphicsContext.clearRect(0,0, getWidth(), getHeight());

        for(int i=0; i<rowNum; i++){
            for(int j=0; j<colNum; j++){
                if(maze[i][j] == 1) {
                    graphicsContext.setFill(backgroundWallColor);
                    graphicsContext.fillRect(j * cellWidth, i * cellHeight,cellWidth,cellHeight);
                    graphicsContext.drawImage(wallImage, j * cellWidth, i * cellHeight,cellWidth,cellHeight);
                }
                else{
                    graphicsContext.setFill(backgroundPathColor);
                    graphicsContext.fillRect(j * cellWidth, i * cellHeight,cellWidth,cellHeight);
                    graphicsContext.drawImage(pathImage, j * cellWidth, i * cellHeight,cellWidth,cellHeight);
                }
            }
        }


        if (showSolution){

            for (int[] position : solutionList) {

                graphicsContext.drawImage(solutionPathImage, position[1]* cellWidth, position[0] * cellHeight, cellWidth, cellHeight);
            }

        }

        graphicsContext.drawImage(goalImage, goalPosition[1] * cellWidth, goalPosition[0] * cellHeight, cellWidth, cellHeight);
        
        graphicsContext.drawImage(characterImage, characterPosition[1] * cellWidth, characterPosition[0] * cellHeight, cellWidth, cellHeight);



    }

    public boolean isShowSolution() {
        return showSolution;
    }

    public void setShowSolution(boolean showSolution) {
        this.showSolution = showSolution;
    }
}
