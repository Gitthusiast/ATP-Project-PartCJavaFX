import Model.MyModel;
import View.MainMenuController;
import View.PlayViewController;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        MyModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View/MyView.fxml").openStream());
        //root.getStylesheets().add(getClass().getResource("View/Covid19Style.css").toString());

        MainMenuController mainMenuView = fxmlLoader.getController();
        mainMenuView.setViewModel(viewModel);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
