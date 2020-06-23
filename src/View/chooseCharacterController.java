package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class chooseCharacterController extends AView implements Initializable {

    private ArrayList<Image> images;
    private int currentImageIndex = 0;
    Scene mainMenuScene;

    @FXML
    private ImageView chosenImageView;
    @FXML
    Image chosenImage;
    @FXML
    private Button mainMenuReturnButton;

    public chooseCharacterController() throws FileNotFoundException {

        images = new ArrayList<>();

        images.add(new Image(new FileInputStream("./src/resources/Images/CovidAvatars/blueCovid.png")));
        images.add(new Image("Images/CovidAvatars/classicCovid.png"));
        images.add(new Image("Images/CovidAvatars/complexVirus.png"));
        images.add(new Image("Images/CovidAvatars/Influenza_Virus.png"));
        images.add(new Image("Images/CovidAvatars/purpleGang.png"));
        images.add(new Image("Images/CovidAvatars/simpleVirus.png"));
        images.add(new Image("Images/CovidAvatars/tealVirus.png"));
        images.add(new Image("Images/CovidAvatars/zikahVirus.png"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AView.characterImageHolder.imageProperty().bind(chosenImageView.imageProperty());
        chosenImage = images.get(1);
        chosenImageView.setImage(chosenImage);
    }

    @FXML
    public void nextLeftImage(MouseEvent mouseEvent){

        if (currentImageIndex == 0)
            currentImageIndex = images.size() - 1;
        else
            currentImageIndex--;

        chosenImage = images.get(currentImageIndex);
        chosenImageView.setImage(chosenImage);
    }

    @FXML
    public void nextRightImage(MouseEvent mouseEvent){

        if (currentImageIndex == images.size() - 1)
            currentImageIndex = 0;
        else
            currentImageIndex++;

        chosenImage = images.get(currentImageIndex);
        chosenImageView.setImage(chosenImage);


    }

    public void setMainMenuScene(Scene mainMenuScene) { this.mainMenuScene = mainMenuScene; }

    @FXML
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
}
