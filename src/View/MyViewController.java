package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.Console;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements IView, Observer {

    public MyViewController() { }

    private MyViewModel myViewModel;

    public void setMyViewModel(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        if(o == myViewModel){

        }
    }

    public void Instruction(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Instructions");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Instructions.fxml").openStream());
            Scene scene = new Scene(root, 400, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }

    public void Play(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("AboutController");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("Play.fxml").openStream());
            Scene scene = new Scene(root, stage.getWidth(), stage.getMinHeight());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {

        }
    }


}
