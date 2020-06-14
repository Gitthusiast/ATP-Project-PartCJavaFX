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
    private PlayViewController playViewController;

    public MainMenuController() { }

    @FXML
    private Button playButton;

    public void setViewModel(MyViewModel viewModel) { this.viewModel = viewModel; }

    public void goToInstructionsMenu(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Instructions.fxml").openStream());
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {

        }
    }

    public void goToPlayView(ActionEvent actionEvent) {
        try {

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

            if (playViewScene == null){

                FXMLLoader fxmlLoader = new FXMLLoader();
                Parent root = fxmlLoader.load(getClass().getResource("PlayView.fxml").openStream());
                playViewController = fxmlLoader.getController();
                playViewScene = new Scene(root, stage.getWidth(), stage.getHeight());

            }


            playViewController.setViewModel(viewModel);
            playViewController.setMainMenuScene(playButton.getScene());
            viewModel.addObserver(playViewController);

            stage.setScene(playViewScene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
