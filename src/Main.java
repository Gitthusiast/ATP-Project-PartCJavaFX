import Model.MyModel;
import View.AView;
import View.MainMenuController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MyModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/MyView.fxml").openStream());

        AView.setViewModel(viewModel);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 700));
        //--------------
        SetStageCloseEvent(primaryStage, model);
        primaryStage.show();
    }

    protected void SetStageCloseEvent(Stage stage, MyModel model){
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Covid, Are you sure you want to exit the game?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    try {
                        model.stopServers();

                        /* Causes the JavaFX application to terminate.
                         * If this method is called after the Application start method is called, then the JavaFX launcher will call
                         * the Application stop method and terminate the JavaFX application thread.
                         * The launcher thread will then shutdown.
                         * If there are no other non-daemon threads that are running, the Java VM will exit. */
                        Platform.exit();
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    event.consume();
                }
            }
        });
    }


    public static void main(String[] args) { launch(args); }
}
