package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class InstructionsController extends AView {

    Scene mainMenuScene;

    @FXML
    Button returnToMainMenuButton;

    public void setMainMenuScene(Scene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
    }

    public InstructionsController() {}

    public void goToMainMenu(ActionEvent actionEvent) {
        try {
            double instructionsWidth = returnToMainMenuButton.getScene().getWindow().getWidth();
            double instructionsHeight = returnToMainMenuButton.getScene().getWindow().getHeight();

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(mainMenuScene);
            Window menuWindow = stage.getOwner();

            stage.show();

            menuWindow.setWidth(instructionsWidth);
            menuWindow.setHeight(instructionsHeight);

        } catch (Exception e) {

        }
    }


}
