package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeDisplayControl extends Canvas {

    private int rowNum;
    private int colNum;
    private int[][] maze;
    int[] charcterPosition;



    public void DrawMaze(){

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

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(charcterPosition[0] * cellWidth,charcterPosition[1] * cellHeight, cellWidth, cellHeight);
    }
}
