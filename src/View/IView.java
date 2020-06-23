package View;

import javafx.event.ActionEvent;

public interface IView {


    /**
     * Generates the maze instance in the view.
     */
    public void generateMaze(ActionEvent actionEvent);

    /**
     * Allow showing solution of the maze to the player.
     */
    public void showSolution();

    /**
     * Display maze in the GUI.
     */
    public void displayMaze();
}
