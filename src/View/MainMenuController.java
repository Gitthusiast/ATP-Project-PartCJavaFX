package View;

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

    private PlayViewController playViewController;

    public MainMenuController() { }

    @FXML
    private Button playButton;


    public void goToPlayView(ActionEvent actionEvent) {
        try {

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene mainMenuScene = playButton.getScene();

            if (playViewScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("PlayView.fxml").openStream());
                playViewController = fxmlLoader.getController();
                playViewScene = new Scene(root, mainMenuScene.getWidth(), mainMenuScene.getHeight());

            }



            playViewController.setMainMenuScene(mainMenuScene);
            playViewController.setMainMenuController(this);
            viewModel.addObserver(playViewController);

            stage.setScene(playViewScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
