package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends AView implements Initializable {

    private Scene playViewScene;
    private Scene chooseCharacterScene;

    private PlayViewController playViewController;
    private chooseCharacterController chooseCharacterController;

    public MainMenuController() { }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(mediaPlayer_Scary == null){
            Media musicFile =  new Media(getClass().getResource("/Mp3/HeartBeatScary.mp3").toString());
            mediaPlayer_Scary = new MediaPlayer(musicFile);
        }
        mediaPlayer_Scary.setVolume(0.1);
        mediaPlayer_Scary.setAutoPlay(true);
        isScaryMusicPlaying = true;
        mediaPlayer_Scary.setOnEndOfMedia(() -> mediaPlayer_Scary.seek(Duration.ZERO));
        try {
            sound.setImage(new Image(new FileInputStream("./src/resources/Images/Unmute.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button playButton;
    @FXML
    private Button chooseCharacterButton;
    @FXML
    private ImageView sound;


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

            playViewController.displayMaze();

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

    public void setSound(MouseEvent mouseEvent) {
        if(isScaryMusicPlaying){
            mediaPlayer_Scary.pause();

            isScaryMusicPlaying = false;
            try{
                sound.setImage(new Image(new FileInputStream("./src/resources/Images/Mute.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        else{
            mediaPlayer_Scary.play();
            isScaryMusicPlaying = true;
            try{
                sound.setImage(new Image(new FileInputStream("./src/resources/Images/Unmute.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
