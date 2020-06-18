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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class chooseCharacterController extends AView implements Initializable {

    private ArrayList<Image> images;
    private int currentIndex = 0;
    Scene mainMenuScene;

    @FXML
    private ImageView chosenImageView;
    @FXML
    Image chosenImage;
    @FXML
    private Button mainMenuReturnButton;
    @FXML
    private HBox imageBox;

    public chooseCharacterController() throws FileNotFoundException {

        images = new ArrayList<>();

        //images.add(new Image(new FileInputStream("//blueCovid.jpg")));
        //images.add(new Image("blueCovid.jpg"));
        images.add(new Image("Images/CovidAvatars/blueCovid.png"));
        images.add(new Image("Images/CovidAvatars/classicCovid.png"));
        images.add(new Image("Images/CovidAvatars/complexVirus.png"));
        images.add(new Image("Images/CovidAvatars/Influenza_Virus.png"));
        images.add(new Image("Images/CovidAvatars/purpleGang.png"));
        images.add(new Image("Images/CovidAvatars/simpleVirus.png"));
        images.add(new Image("Images/CovidAvatars/tealVirus.png"));
        images.add(new Image("Images/CovidAvatars/zikahVirus.png"));
        /*images.add(new Image("/resources/Images/classicCovid.jpg"));
        images.add(new Image("/resources/Images/complexCovid.jpg"));
        images.add(new Image("/resources/Images/Influenza_Virus.jpg"));
        images.add(new Image("/resources/Images/purpleGang.jpg"));
        images.add(new Image("/resources/Images/simpleVirus.jpg"));
        images.add(new Image("/resources/Images/tealVirus.jpg"));
        images.add(new Image("/resources/Images/zikahVirus.jpg"));*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AView.characterImageHolder.imageProperty().bind(chosenImageView.imageProperty());
        chosenImage = images.get(0);
        chosenImageView.setImage(chosenImage);
    }

    @FXML
    public void nextLeftImage(MouseEvent mouseEvent){

        if (currentIndex == 0)
            currentIndex = images.size() - 1;
        else
            currentIndex--;

        chosenImage = images.get(currentIndex);
        chosenImageView.setImage(chosenImage);
    }

    @FXML
    public void nextRightImage(MouseEvent mouseEvent){

        if (currentIndex == images.size() - 1)
            currentIndex = 0;
        else
            currentIndex++;

        chosenImage = images.get(currentIndex);
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
