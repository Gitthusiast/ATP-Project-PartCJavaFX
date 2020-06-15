package View;

import ViewModel.MyViewModel;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

public abstract class AView implements IView {

    protected static MyViewModel viewModel;

    @FXML
    protected MenuBar menuBar;

    public AView() {}

    public static MyViewModel getViewModel() {
        return viewModel;
    }

    public static void setViewModel(MyViewModel viewModel) {
        AView.viewModel = viewModel;
    }

}
