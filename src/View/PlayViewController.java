package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class PlayViewController extends AView implements Observer {

    private Scene mainMenuScene;
    private ArrayList<int[]> solutionList = null;

    @FXML
    private TextField textField_rowNumber;
    @FXML
    private TextField textField_columnNumber;
    @FXML
    private MazeDisplayControl mazeDisplayControl;
    @FXML
    Button showSolutionButton;
    @FXML
    Button generateMazeButton;
    @FXML
    Button mainMenuReturnButton;

    public PlayViewController() {}

    public void setMainMenuScene(Scene mainMenuScene) { this.mainMenuScene = mainMenuScene; }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o == viewModel){

            displayMaze(viewModel.getMaze());
            generateMazeButton.setDisable(false);
        }
    }

    public void displayMaze(int[][] maze){
        mazeDisplayControl.setMaze(maze);
        mazeDisplayControl.setCharcterPosition(viewModel.getCharacterRow(), viewModel.getCharacterColumn());
        mazeDisplayControl.drawMaze();
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        try {
            double playWindowWidth = mainMenuReturnButton.getScene().getWindow().getWidth();
            double playWindowHeight = mainMenuReturnButton.getScene().getWindow().getHeight();

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(mainMenuScene);

            Window mainMenuWindow = mainMenuScene.getWindow();

            mainMenuWindow.setWidth(playWindowWidth);
            mainMenuWindow.setHeight(playWindowHeight);

            stage.show();
        } catch (Exception e) {

        }
    }

    /**
     * Generates and display the maze instance in the view.
     */
    public void generateMaze(ActionEvent actionEvent){

        int rowNumber, columnNumber;

        if(isValidInteger(textField_rowNumber.getText()) && isValidInteger(textField_columnNumber.getText())){

            generateMazeButton.setDisable(true);

            solutionList = null;
            mazeDisplayControl.setShowSolution(false);
            showSolutionButton.setText("Show Solution");

            rowNumber = Integer.parseInt(textField_rowNumber.getText());
            columnNumber = Integer.parseInt(textField_columnNumber.getText());
            viewModel.generateMaze(rowNumber, columnNumber);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid parameter");
            alert.setContentText("Wrong parameter received! Please enter an integer value between 2 and 100");
            alert.showAndWait();
        }

    }


    private static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),10) < 0) return false;
        }
        return true;
    }

    /**
     * Validates text field user input.
     * @param s input.
     * @return True if is an integer between 2 to 100.
     */
    public boolean isValidInteger(String s){

        if (!isInteger(s)) return false;

        int sConverted = Integer.parseInt(s);
        return sConverted >= 2 && sConverted <= 100;
    }

    public void showSolution(){

        if (mazeDisplayControl.isShowSolution()){

            mazeDisplayControl.setShowSolution(false);
            showSolutionButton.setText("Show Solution");
        }
        else{

            mazeDisplayControl.setShowSolution(true);
            showSolutionButton.setText("Hide Solution");
        }

        showSolutionButton.setDisable(true);
        if (solutionList == null){
            solutionList = viewModel.solveMaze();
            mazeDisplayControl.setSolutionList(solutionList);
        }
        mazeDisplayControl.drawMaze();
        showSolutionButton.setDisable(false);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayControl.requestFocus();
    }

    public void KeyPressed(KeyEvent keyEvent){
        viewModel.moveCharacter(keyEvent.getCode());
        //keyEvent.consume();
    }

}
