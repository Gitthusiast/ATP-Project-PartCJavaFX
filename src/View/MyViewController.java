package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXML;

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
}
