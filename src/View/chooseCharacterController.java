package View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class chooseCharacterController extends AView {

    private ArrayList<Image> images;
    private int currentIndex = 0;

    @FXML
    private ImageView chosenImageView;
    @FXML
    Image chosenImage;

    public chooseCharacterController(){

        images = new ArrayList<>();

        images.add(new Image("@/resources/Images/blueCovid.jpg"));
        images.add(new Image("@/resources/Images/classicCovid.jpg"));
        images.add(new Image("@/resources/Images/complexCovid.jpg"));
        images.add(new Image("@/resources/Images/Influenza_Virus.jpg"));
        images.add(new Image("@/resources/Images/purpleGang.jpg"));
        images.add(new Image("@/resources/Images/simpleVirus.jpg"));
        images.add(new Image("@/resources/Images/tealVirus.jpg"));
        images.add(new Image("@/resources/Images/zikahVirus.jpg"));

        chosenImage = images.get(1);
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

}
