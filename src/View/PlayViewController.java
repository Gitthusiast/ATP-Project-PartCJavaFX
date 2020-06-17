package View;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class PlayViewController extends AView implements Observer, Initializable {

    private Scene mainMenuScene;
    private MainMenuController mainMenuController;
    private ArrayList<int[]> solutionList = null;
    private FileChooser fileChooser;

    @FXML
    private TextField textField_rowNumber;
    @FXML
    private TextField textField_columnNumber;
    @FXML
    private MazeDisplayControl mazeDisplayControl;
    @FXML
    private Button showSolutionButton;
    @FXML
    private Button generateMazeButton;
    @FXML
    private Button mainMenuReturnButton;
    @FXML
    private MenuItem saveMenuOption;
    @FXML
    private Label label_currentPosition;

    public PlayViewController() {

        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE file (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        label_currentPosition.textProperty().bind(Bindings.concat("Position: (", viewModel.currentPositionProperty(), ")") );
    }



    public void setMainMenuScene(Scene mainMenuScene) { this.mainMenuScene = mainMenuScene; }

    public void setMainMenuController(MainMenuController mainMenuController) { this.mainMenuController = mainMenuController; }

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

            if (arg instanceof String && arg.equals("generatedMaze")){

                displayMaze(viewModel.getMaze());
                generateMazeButton.setDisable(false);
                showSolutionButton.setDisable(false);
                saveMenuOption.setDisable(false);

                solutionList = null;
                mazeDisplayControl.setShowSolution(false);
                showSolutionButton.setText("Show Solution");

            }
            else if (arg instanceof String && arg.equals("notValidInputs")) {

                generateMazeButton.setDisable(false);

                if (solutionList != null)
                    showSolutionButton.setDisable(false);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid parameter");
                alert.setContentText("Wrong parameter received! Please enter an integer value between 2 and 100");
                alert.showAndWait();
            }
            else if (arg instanceof String && arg.equals("movedCharacter")){
                displayMaze(viewModel.getMaze());
            }
            else if (arg instanceof String && arg.equals("goalReached")){

                displayMaze(viewModel.getMaze());

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Congratulations");
                alert.setHeaderText("You Win!");
                alert.setContentText(
                        "You have managed to infect the whole body!\n" +
                        "You have managed to prove yourself as a severe, highly infective and extremely lethal virus. This unsuspecting human had no chance against you.\n" +
                        "But there are still other humans to eradicate. Now set a bigger maze and keep practicing.");
                alert.showAndWait();

            }
        }
        //disable All buttons
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
            e.printStackTrace();
        }
    }

    /**
     * Generates and display the maze instance in the view.
     */
    public void generateMaze(ActionEvent actionEvent){


        generateMazeButton.setDisable(true);
        showSolutionButton.setDisable(true);

        viewModel.generateMaze(textField_rowNumber.getText(), textField_columnNumber.getText());

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

    @FXML
    public void saveMaze(ActionEvent actionEvent){

        File saveDestination = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if (saveDestination != null){

            try (ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(saveDestination))) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(byteArrayOutputStream);
                compressorOutputStream.write(viewModel.getMazeByteArray());
                saveStream.writeObject(byteArrayOutputStream.toByteArray());

                saveStream.writeObject(viewModel.getCharacterRow());
                saveStream.writeObject(viewModel.getCharacterColumn());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void loadMaze(ActionEvent actionEvent){

        File loadDestination = fileChooser.showOpenDialog(menuBar.getScene().getWindow());

        if (loadDestination != null){

            try (ObjectInputStream loadStream = new ObjectInputStream(new FileInputStream(loadDestination))) {


                //Reading maze information from saved file
                byte[] compressedMaze = (byte[]) loadStream.readObject(); //read generated maze (compressed with MyCompressor) from server
                InputStream inputStream = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                byte[] decompressedMaze = new byte[viewModel.getMaze().length * viewModel.getMaze()[0].length + 50 /* assuming max maze size 1000x1000 */]; //allocating byte[] for the decompressed maze -
                inputStream.read(decompressedMaze); //Fill decompressedMaze with bytes


                int characterRow = (int) loadStream.readObject();
                int characterColumn = (int) loadStream.readObject();

                viewModel.updateMaze(decompressedMaze, characterRow, characterColumn);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
