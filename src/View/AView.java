package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class AView implements IView {

    protected static MyViewModel viewModel;
    protected  FileChooser fileChooser;

    @FXML
    protected MenuBar menuBar;

    public AView() {

        fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("MAZE file (*.maze)", "*.maze");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

    public static MyViewModel getViewModel() {
        return viewModel;
    }

    public static void setViewModel(MyViewModel viewModel) {
        AView.viewModel = viewModel;
    }

    @FXML
    public void saveMaze(ActionEvent actionEvent){

        File saveDestination = fileChooser.showSaveDialog(menuBar.getScene().getWindow());

        if (saveDestination != null){

            try (ObjectOutputStream saveStream = new ObjectOutputStream(new FileOutputStream(saveDestination))) {

                saveStream.writeObject(viewModel.getMazeByteArray());
                saveStream.writeObject(viewModel.getCharacterRow());
                saveStream.writeObject(viewModel.getCharacterColumn());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
