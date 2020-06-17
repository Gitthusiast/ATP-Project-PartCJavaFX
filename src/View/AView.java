package View;

import ViewModel.MyViewModel;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
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
    protected static Stage pirmaryStage;

    public static Stage getPirmaryStage() {
        return pirmaryStage;
    }

    public static void setPirmaryStage(Stage pirmaryStage) {
        AView.pirmaryStage = pirmaryStage;
    }

    @FXML
    protected MenuBar menuBar;

    public AView() {}

    public static MyViewModel getViewModel() {
        return viewModel;
    }

    public static void setViewModel(MyViewModel viewModel) {
        AView.viewModel = viewModel;
    }

    @FXML
    protected void callUponExit(Event actionEvent){
        //close primary stage
        pirmaryStage.fireEvent(
                new WindowEvent(pirmaryStage, WindowEvent.WINDOW_CLOSE_REQUEST)
        );
    }

    @FXML
    protected void goToAbout(ActionEvent actionEvent){
        try {

            Scene lastScene = pirmaryStage.getScene();

            if(aboutScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
                aboutController = fxmlLoader.getController();
                aboutScene = new Scene(root,lastScene.getWidth(), lastScene.getHeight());
            }

            aboutController.setLastScene(lastScene);

            pirmaryStage.setScene(aboutScene);
            pirmaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void goToInstructions(ActionEvent actionEvent){
        try {

            Scene lastScene = pirmaryStage.getScene();

            if(instructionsScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("Instructions.fxml").openStream());
                instructionsController = fxmlLoader.getController();
                instructionsScene = new Scene(root,lastScene.getWidth(), lastScene.getHeight());
            }

            instructionsController.setLastScene(lastScene);

            pirmaryStage.setScene(instructionsScene);
            pirmaryStage.show();
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
        String message = "Number of threads is: ";
        message += properties.getProperty("threadsNum") + "\n";
        message += "The searching algorithm is: ";
        message += properties.getProperty("searchAlgorithm") + "\n";
        message += "The generating method is: ";
        message += properties.getProperty("generateMazeAlgorithm") + "\n";

        alert.setContentText(message);

        alert.showAndWait();
    }



}
