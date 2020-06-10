package ViewModel;

import Model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private IModel model;
    private int characterRow;
    private int characterColumn;
    public StringProperty characterRowInput;
    public StringProperty characterColumnInput;

    public MyViewModel(IModel model) {

        characterRowInput = new SimpleStringProperty();
        characterColumnInput = new SimpleStringProperty();
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
        if(o == model){

            int[] currentPosition = model.getCurrentPosition();
            characterRow = currentPosition[0];
            characterColumn = currentPosition[1];

            characterRowInput.set(characterRow + "");
            characterColumnInput.set(characterColumn + "");

            setChanged();
            notifyObservers();
        }

    }

    /**
     * Retrieves a maze from the model.
     * @param rowNumber number of rows.
     * @param columnNumber number of columns.
     * @return 2-d int array representation of a maze
     */
    public int[][] generateMaze(int rowNumber, int columnNumber){ return model.generateMaze(rowNumber, columnNumber); }

    /**
     * Retrieves the current maze's solution.
     * @return 2-d int array representation of a maze solution.
     */
    public ArrayList<int[]> solveMaze() { return model.solveMaze(); }

    public void moveCharacter(KeyCode movement){ model.moveCharacter(movement);}

    public int getCharacterRow(){ return characterRow; }
    public int getCharacterColumn(){ return characterColumn; }

    public int[][] getMaze() { return model.getMaze(); }

}
