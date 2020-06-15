package View;

import Model.IModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;


import java.util.Observable;
import java.util.Observer;

public class InstructionsController implements IView, Observer {

    IModel model;
    Scene mainMenuScene;

    @FXML
    Button bGoToMenu;

    public void setMainMenuScene(Scene mainMenuScene) {
        this.mainMenuScene = mainMenuScene;
    }

    public InstructionsController() {
    }

    public InstructionsController(IModel model) {
        this.model = model;
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

    }


    public void goToMainMenu(ActionEvent actionEvent) {
        try {
            double instructionsWidth = bGoToMenu.getScene().getWindow().getWidth();
            double instructionsHeight = bGoToMenu.getScene().getWindow().getHeight();

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
