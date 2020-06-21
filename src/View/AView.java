package View;

import ViewModel.MyViewModel;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.Properties;


public abstract class AView implements IView {

    private Scene instructionsScene;
    private Scene aboutScene;

    private InstructionsController instructionsController;
    private AboutController aboutController;

    protected static MyViewModel viewModel;
    protected static Stage primaryStage;

    protected static ImageView characterImageHolder = new ImageView();;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    protected static MediaPlayer mediaPlayer_Dead;
    protected static MediaPlayer mediaPlayer_Scary;
    protected static boolean isScaryMusicPlaying;

    public static void setPrimaryStage(Stage primaryStage) {
        AView.primaryStage = primaryStage;
    }

    @FXML
    protected MenuBar menuBar;

    public AView() {  }

    public static MyViewModel getViewModel() {
        return viewModel;
    }

    public static void setViewModel(MyViewModel viewModel) {
        AView.viewModel = viewModel;
    }

    @FXML
    protected void callUponExit(Event actionEvent){
        //close primary stage
        primaryStage.fireEvent(
                new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)
        );
    }

    @FXML
    protected void goToAbout(ActionEvent actionEvent){
        try {

            Scene lastScene = primaryStage.getScene();

            if(aboutScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
                aboutController = fxmlLoader.getController();
                aboutScene = new Scene(root,lastScene.getWidth(), lastScene.getHeight());
            }

            aboutController.setLastScene(lastScene);

            primaryStage.setScene(aboutScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void goToInstructions(ActionEvent actionEvent){
        try {

            Scene lastScene = primaryStage.getScene();

            if(instructionsScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("Instructions.fxml").openStream());
                instructionsController = fxmlLoader.getController();
                instructionsScene = new Scene(root,lastScene.getWidth(), lastScene.getHeight());
            }

            instructionsController.setLastScene(lastScene);

            primaryStage.setScene(instructionsScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void ViewConfigurations(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Properties");
        alert.setHeaderText("Configuration");

        Properties properties = new Properties();
        String fileName = "src/resources/config.properties";
        try{
            InputStream inputStream = new FileInputStream(fileName);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String message = "Number of threads is: " + properties.getProperty("threadsNum") + "\n" +"The searching algorithm is: " +
                properties.getProperty("searchAlgorithm") + "\n" +  "The generating method is: "
                + properties.getProperty("generateMazeAlgorithm") + "\n";

        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Covid19Style.css").toExternalForm());
        dialogPane.getStyleClass().add("Alert");

        alert.showAndWait();
    }



}
