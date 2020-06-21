package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;


public class AboutController extends AView implements Initializable {

    Scene lastScene;

    @FXML
    Button goBackButton;
    @FXML
    Circle leftPicture;
    @FXML
    Circle rightPicture;

    public void setLastScene(Scene lastScene) {
        this.lastScene = lastScene;
    }

    public AboutController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leftPicture.setFill(new ImagePattern(new Image("/resources/Images/yair.jpeg")));
        rightPicture.setFill(new ImagePattern(new Image("/resources/Images/gila.jpg")));
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            double instructionsWidth = goBackButton.getScene().getWindow().getWidth();
            double instructionsHeight = goBackButton.getScene().getWindow().getHeight();

            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(lastScene);

            stage.show();

            stage.setWidth(instructionsWidth);
            stage.setHeight(instructionsHeight);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
