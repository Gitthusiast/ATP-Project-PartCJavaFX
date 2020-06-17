package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class InstructionsController extends AView {

    Scene lastScene;

    @FXML
    Button goBackButton;

    public void setLastScene(Scene lastScene) {
        this.lastScene = lastScene;
    }

    public InstructionsController() {}

    public void goBack(ActionEvent actionEvent) {
        try {
            double instructionsWidth = goBackButton.getScene().getWindow().getWidth();
            double instructionsHeight = goBackButton.getScene().getWindow().getHeight();

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(lastScene);
            Window menuWindow = stage.getOwner();

            stage.show();

            menuWindow.setWidth(instructionsWidth);
            menuWindow.setHeight(instructionsHeight);

        } catch (Exception e) {

        }
    }

}
