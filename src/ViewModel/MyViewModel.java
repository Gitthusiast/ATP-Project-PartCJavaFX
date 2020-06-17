package ViewModel;

import Model.IModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static ViewModel.MovementCode.*;

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

    private MovementCode convertKeyCode(KeyCode movement){
        MovementCode movementCode = null;
        switch (movement)
        {
            case NUMPAD1:
                movementCode = BOTTOM_LEFT;
                break;
            case NUMPAD2:
                movementCode = DOWN;
                break;
            case NUMPAD3:
                movementCode = BOTTOM_RIGHT;
                break;
            case NUMPAD4:
                movementCode = LEFT;
                break;
            case NUMPAD6:
                movementCode = RIGHT;
                break;
            case NUMPAD7:
                movementCode = TOP_LEFT;
                break;
            case NUMPAD8:
                movementCode = UP;
                break;
            case NUMPAD9:
                movementCode = TOP_RIGHT;
                break;
        }
        return movementCode;
    }

    public void moveCharacter(KeyCode movement){ model.moveCharacter(convertKeyCode(movement));}

    public int getCharacterRow(){ return characterRow; }
    public int getCharacterColumn(){ return characterColumn; }

    public int[][] getMaze() { return model.getMaze(); }

    public byte[] getMazeByteArray() { return model.getMazeByteArray(); }

    /**
     * Updates the model with a given maze byte array and current character position.
     * @param decompressedMaze Maze in a byte array form
     * @param characterRow
     * @param characterColumn
     */
    public void updateMaze(byte[] decompressedMaze, int characterRow, int characterColumn) { model.updateMaze(decompressedMaze , characterRow, characterColumn); }
}
