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
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
    private ArrayList<int[]> solutionList = null;
    private FileChooser fileChooser;
    private Image wallImage;
    private Image pathImage;
    private Image goalImage;
    private Image solutionPathImage;

    private double oldX;
    private double oldY;
    private double mazeDisplayCellWidth;
    private double mazeDisplayCellHeight;

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
    @FXML
    private Pane mazePane;

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

        wallImage = new Image("Images/vein-tissue.jpeg");
        pathImage = new Image("Images/bloodCells2.jpeg");
        goalImage = new Image("Images/lungs.png");
        solutionPathImage = new Image("Images/solution.png");

        mazeDisplayControl.setWallImage(wallImage);
        mazeDisplayControl.setPathImage(pathImage);
        mazeDisplayControl.setGoalImage(goalImage);
        mazeDisplayControl.setSolutionPathImage(solutionPathImage);

        mazeDisplayControl.setBackgroundPathColor(Color.web("#DE9291"));
        mazeDisplayControl.setBackgroundWallColor(Color.web("#DE9291"));

        mazeDisplayControl.widthProperty().bind(mazePane.widthProperty());
        mazeDisplayControl.heightProperty().bind(mazePane.heightProperty());
    }



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

            if (arg instanceof String && arg.equals("generatedMaze")){

                displayMaze();
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
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("Covid19Style.css").toExternalForm());
                dialogPane.getStyleClass().add("Alert");
                alert.showAndWait();
            }
            else if (arg instanceof String && arg.equals("movedCharacter")){
                displayMaze();
            }
            else if (arg instanceof String && arg.equals("goalReached")){

                displayMaze();
                handelInvalidParametersAlert();
            }
        }
        //disable All buttons
    }

    private void handelInvalidParametersAlert(){
        if(mediaPlayer_Dead == null){
            Media musicFile = new Media(getClass().getResource("/Mp3/HeartBeatDying.mp3").toString());
            mediaPlayer_Dead = new MediaPlayer(musicFile);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations");
        alert.setHeaderText("You Win!");
        alert.setContentText(
                "You have managed to infect the whole body!\n" +
                        "You have managed to prove yourself as a severe, highly infective and extremely lethal virus. This unsuspecting human had no chance against you.\n" +
                        "But there are still other humans to eradicate. Now set a bigger maze and keep practicing.");

        alert.setOnCloseRequest(e ->{
            mediaPlayer_Dead.stop();
            if(isScaryMusicPlaying)
                mediaPlayer_Scary.play();
        });

        alert.setOnShowing(e ->{
            mediaPlayer_Scary.stop();
            mediaPlayer_Dead.play();
            mediaPlayer_Dead.setVolume(0.1);

            mediaPlayer_Dead.setOnEndOfMedia(() -> {
                if(isScaryMusicPlaying)
                    mediaPlayer_Scary.play();
            });

        });

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Covid19Style.css").toExternalForm());
        dialogPane.getStyleClass().add("winAlert");
        alert.showAndWait();
    }

    public void displayMaze(){
        if(viewModel.getMaze() != null){
            mazeDisplayControl.setMaze(viewModel.getMaze());
            mazeDisplayControl.setCharcterPosition(viewModel.getCharacterRow(), viewModel.getCharacterColumn());
            mazeDisplayControl.setCharacterImage(AView.characterImageHolder.getImage());
            mazeDisplayControl.setGoalPosition(viewModel.getGoalRow(), viewModel.getGoalColumn());
            mazeDisplayControl.drawMaze();
        }
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

        mazeDisplayCellWidth = mazePane.getWidth() / viewModel.getMaze()[0].length;
        mazeDisplayCellHeight = mazePane.getHeight() / viewModel.getMaze().length;

        oldX = viewModel.getCharacterColumn() * mazeDisplayCellWidth;
        oldY = viewModel.getCharacterRow() * mazeDisplayCellHeight;

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
        keyEvent.consume();
    }

    @FXML
    public void saveMaze(ActionEvent actionEvent){

        File saveDestination = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if (saveDestination != null){

            try (ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(saveDestination))) {

                saveStream.writeObject(viewModel.getMaze().length);
                saveStream.writeObject(viewModel.getMaze()[0].length);

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


                int rowNumber = (int)loadStream.readObject();
                int colNumber = (int)loadStream.readObject();

                //Reading maze information from saved file
                byte[] compressedMaze = (byte[]) loadStream.readObject(); //read generated maze (compressed with MyCompressor) from server
                InputStream inputStream = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                byte[] decompressedMaze = new byte[rowNumber * colNumber + 50 /* assuming max maze size 1000x1000 */]; //allocating byte[] for the decompressed maze -
                inputStream.read(decompressedMaze); //Fill decompressedMaze with bytes


                int characterRow = (int) loadStream.readObject();
                int characterColumn = (int) loadStream.readObject();

                viewModel.updateMaze(decompressedMaze, characterRow, characterColumn);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * drag mouse fuction makes it possible to move the character by dragging the mouse of the display.
     * It converts the relative position of the mouse to an appropriate key code and calls move character function.
     * @param mouseEvent - the event that drags the character
     */
    public void dragMouse(MouseEvent mouseEvent) {

        if(viewModel.getMaze() != null){

            double quarterCellHeight = mazeDisplayCellHeight /4;
            double quarterCellWidth = mazeDisplayCellWidth/4;
            boolean hasMoved = false;


            if(mouseEvent.getY() >= oldY + quarterCellHeight  && mouseEvent.getY() < oldY+ 3*quarterCellHeight && mouseEvent.getX() > oldX + mazeDisplayCellWidth + quarterCellWidth && mouseEvent.getX() < oldX + mazeDisplayCellWidth*2 - quarterCellWidth){
                oldX += mazeDisplayCellWidth;
                hasMoved = viewModel.moveCharacter(KeyCode.RIGHT);
                if(!hasMoved){
                    oldX -= mazeDisplayCellWidth;
                }
            }
            else if(mouseEvent.getY() >= oldY + quarterCellHeight  && mouseEvent.getY() < oldY+ 3*quarterCellHeight && mouseEvent.getX() >= oldX - mazeDisplayCellWidth + quarterCellWidth && mouseEvent.getX() < oldX - quarterCellWidth){
                oldX -= mazeDisplayCellWidth;
                hasMoved = viewModel.moveCharacter(KeyCode.LEFT);
                if(!hasMoved){
                    oldX += mazeDisplayCellWidth;
                }
            }

            else if(mouseEvent.getX() >= oldX + quarterCellWidth && mouseEvent.getX() <oldX+ 3*quarterCellWidth && mouseEvent.getY() > oldY + mazeDisplayCellHeight + quarterCellHeight && mouseEvent.getY() < oldY + mazeDisplayCellHeight *2 - quarterCellHeight){
                oldY += mazeDisplayCellHeight;
                hasMoved = viewModel.moveCharacter(KeyCode.DOWN);
                if(!hasMoved){
                    oldY -= mazeDisplayCellHeight;
                }
            }

            else if(mouseEvent.getX() >= oldX + quarterCellWidth && mouseEvent.getX() <oldX+3*quarterCellWidth && mouseEvent.getY() > oldY - mazeDisplayCellHeight + quarterCellHeight && mouseEvent.getY() < oldY - quarterCellHeight){
                oldY -= mazeDisplayCellHeight;
                hasMoved = viewModel.moveCharacter(KeyCode.UP);
                if(!hasMoved){
                    oldY += mazeDisplayCellHeight;
                }
            }
            //UP LEFT
            else if(mouseEvent.getY() > oldY - 3*quarterCellHeight && mouseEvent.getY() < oldY -quarterCellHeight && mouseEvent.getX() >= oldX - 3*quarterCellWidth && mouseEvent.getX() < oldX - quarterCellWidth){
                oldX -= mazeDisplayCellWidth;
                oldY -= mazeDisplayCellHeight;
                hasMoved = viewModel.moveCharacter(KeyCode.NUMPAD7);
                if(!hasMoved){
                    oldX += mazeDisplayCellWidth;
                    oldY += mazeDisplayCellHeight;
                }
            }
            //UP RIGHT
            else if(mouseEvent.getY() > oldY - 3*quarterCellHeight && mouseEvent.getY() < oldY -quarterCellHeight  && mouseEvent.getX() > oldX + mazeDisplayCellWidth + quarterCellWidth && mouseEvent.getX() < oldX + mazeDisplayCellWidth*2 - quarterCellWidth){
                oldX += mazeDisplayCellWidth;
                oldY -= mazeDisplayCellHeight;
                hasMoved = viewModel.moveCharacter(KeyCode.NUMPAD9);
                if(!hasMoved){
                    oldX -= mazeDisplayCellWidth;
                    oldY += mazeDisplayCellHeight;
                }
            }
            //DOWN RIGHT
            else if(mouseEvent.getY() > oldY + mazeDisplayCellHeight + quarterCellHeight && mouseEvent.getY() < oldY + mazeDisplayCellHeight *2 - quarterCellHeight && mouseEvent.getX() > oldX + mazeDisplayCellWidth + quarterCellWidth && mouseEvent.getX() < oldX + mazeDisplayCellWidth*2 - quarterCellWidth){
                oldX += mazeDisplayCellWidth;
                oldY += mazeDisplayCellHeight;
                hasMoved = viewModel.moveCharacter(KeyCode.NUMPAD3);
                if(!hasMoved){
                    oldX -= mazeDisplayCellWidth;
                    oldY -= mazeDisplayCellHeight;
                }
            }
            //DOWN LEFT
            else if(mouseEvent.getY() > oldY + mazeDisplayCellHeight + quarterCellHeight && mouseEvent.getY() < oldY + mazeDisplayCellHeight *2 - quarterCellHeight && mouseEvent.getX() >= oldX - mazeDisplayCellWidth + quarterCellWidth && mouseEvent.getX() < oldX - quarterCellWidth){
                oldX -= mazeDisplayCellWidth;
                oldY += mazeDisplayCellHeight;
                hasMoved = viewModel.moveCharacter(KeyCode.NUMPAD1);
                if(!hasMoved){
                    oldX += mazeDisplayCellWidth;
                    oldY -= mazeDisplayCellHeight;
                }
            }

        }


    }

}
