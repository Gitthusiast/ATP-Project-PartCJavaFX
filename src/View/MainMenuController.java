package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    private MyViewModel viewModel;
    private Scene playViewScene;
    private Scene instructionsScene;

    private PlayViewController playViewController;
    private InstructionsController instructionsController;

    public MainMenuController() { }

    @FXML
    private Button playButton;

    @FXML
    private Button instructionsButton;

    public void setViewModel(MyViewModel viewModel) { this.viewModel = viewModel; }

    public void goToInstructionsMenu(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene menuScene = instructionsButton.getScene();

            if(instructionsScene == null){
                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("Instructions.fxml").openStream());
                instructionsController = fxmlLoader.getController();
                instructionsScene = new Scene(root,menuScene.getWidth(), menuScene.getHeight());
            }

            instructionsController.setMainMenuScene(menuScene);

            stage.setScene(instructionsScene);
            stage.show();
        } catch (Exception e) {

        }
    }

    public void goToPlayView(ActionEvent actionEvent) {
        try {

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene meneScene = playButton.getScene();

            if (playViewScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("PlayView.fxml").openStream());
                playViewController = fxmlLoader.getController();
                playViewScene = new Scene(root, meneScene.getWidth(), meneScene.getHeight());

            }


            playViewController.setViewModel(viewModel);
            playViewController.setMainMenuScene(meneScene);
            viewModel.addObserver(playViewController);

            stage.setScene(playViewScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
