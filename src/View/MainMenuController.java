package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController extends AView {

    private Scene playViewScene;
    private Scene chooseCharacterScene;

    private PlayViewController playViewController;
    private chooseCharacterController chooseCharacterController;

    public MainMenuController() { }

    @FXML
    private Button playButton;
    @FXML
    private Button chooseCharacterButton;


    public void goToPlayView(ActionEvent actionEvent) {
        try {

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene mainMenuScene = playButton.getScene();

            if (playViewScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("PlayView.fxml").openStream());
                playViewController = fxmlLoader.getController();
                playViewScene = new Scene(root, mainMenuScene.getWidth(), mainMenuScene.getHeight());

                playViewScene.widthProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                        playViewController.displayMaze();
                    }
                });

                playViewScene.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        playViewController.displayMaze();
                    }
                });

            }



            playViewController.setMainMenuScene(mainMenuScene);
            viewModel.addObserver(playViewController);

            stage.setScene(playViewScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToChooseView(ActionEvent actionEvent) {
        try {

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene mainMenuScene = chooseCharacterButton.getScene();

            if (chooseCharacterScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("chooseCharacterView.fxml").openStream());
                chooseCharacterController = fxmlLoader.getController();
                chooseCharacterScene = new Scene(root, mainMenuScene.getWidth(), mainMenuScene.getHeight());

            }



            chooseCharacterController.setMainMenuScene(mainMenuScene);

            stage.setScene(chooseCharacterScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
