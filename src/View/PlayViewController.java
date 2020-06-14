package View;

import ViewModel.MyViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.Observable;
import java.util.Observer;

public class PlayViewController implements IView, Observer {

    private MyViewModel viewModel;

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


    public PlayViewController() {}

    public void setViewModel(MyViewModel viewModel) { this.viewModel = viewModel; }


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

    public void goToMenu(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
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
            rowNumber = Integer.parseInt(textField_rowNumber.getText());
            columnNumber = Integer.parseInt(textField_columnNumber.getText());
            viewModel.generateMaze(rowNumber, columnNumber);
        }
        //else { alertInvalidMazeDimensions(); }

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
        mazeDisplayControl.drawMaze();
        showSolutionButton.setDisable(false);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        //mazeDisplayControl.requestFocus();
    }

    public void KeyPressed(KeyEvent keyEvent){
        viewModel.moveCharacter(keyEvent.getCode());
        //keyEvent.consume();
    }
}
