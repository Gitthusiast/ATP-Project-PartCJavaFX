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

        double canvasWeidth = this.getWidth();
        double canvasHeight = this.getHeight();

        double cellWiedth = canvasWeidth/colNum;
        double cellHeight = canvasHeight/rowNum;

        GraphicsContext graphicsContext = getGraphicsContext2D(); //get the relevant graphic context so that we can draw on owr canvas
        for(int i=0; i<rowNum; i++){
            for(int j=0; j<colNum; j++){
                if(maze[i][j] == 1) {
                    graphicsContext.setFill(Color.BROWN);
                    graphicsContext.fillRect(i, j,i*cellWiedth,j*cellHeight);
                }
                else{
                    graphicsContext.setFill(Color.RED);
                    graphicsContext.fillRect(i, j,i*cellWiedth,j*cellHeight);
                }
            }
        }

        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(charcterPosition[0],charcterPosition[1],charcterPosition[0] * cellWiedth,charcterPosition[1] * cellHeight);
    }
}
