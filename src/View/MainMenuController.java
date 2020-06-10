package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.INACTIVE;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class MainMenuController {

    private MyViewModel myViewModel;

    public MainMenuController() { }

    public MainMenuController(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

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
            PlayViewController playViewController = new PlayViewController(this.myViewModel);
            myViewModel.addObserver(playViewController);
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("PlayView.fxml").openStream());
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
