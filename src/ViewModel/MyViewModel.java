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
    private int[] startPosition;
    private int[] goalPosition;
    private StringProperty currentPosition;

    public MyViewModel(IModel model) {

        currentPosition  = new SimpleStringProperty(this,"currentPosition", "0,0");
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
            this.currentPosition.set(characterRow + "," + characterColumn);

            if (arg instanceof String && arg.equals("generatedMaze")){

                startPosition = model.getStartPosition();
                goalPosition = model.getGoalPosition();

                setChanged();
                notifyObservers("generatedMaze");
            }
            else if (arg instanceof String && arg.equals("movedCharacter")){

                setChanged();

                if (characterRow == goalPosition[0] && characterColumn == goalPosition[1])
                    notifyObservers("goalReached");
                else
                    notifyObservers("movedCharacter");
            }



        }

    }


    public void setCurrentPosition(String currentPosition) {
        this.currentPosition.set(currentPosition);
    }

    public String getCurrentPosition() {
        return currentPosition.get();
    }

    /**
     * Current position's string is of the form - "{row},{column}".
     */
    public StringProperty currentPositionProperty() {
        return currentPosition;
    }

    /**
     * Retrieves a maze from the model if given parameters are valid or notifies if are not valid.
     * Parameters are valid is string representing integer between 2 to 100.
     * @param sRowNumber number of rows.
     * @param sColumnNumber number of columns.
     */
    public void generateMaze(String sRowNumber, String sColumnNumber){

        if (isValidInteger(sRowNumber) && isValidInteger(sColumnNumber)) {

            int rowNumber = Integer.parseInt(sRowNumber);
            int columnNumber = Integer.parseInt(sColumnNumber);
            model.generateMaze(rowNumber, columnNumber);
        }
        else {
            setChanged();
            notifyObservers("notValidInputs");
        }
}

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
            default:
                movementCode = DEAFULT;
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

    /**
     * Checks if a given string is representing a non-negative integer value in the radix of 10.
     * @param s String to be evaluated.
     * @return True if string is representing a non-negative integer value in the radix of 10, else false.
     */
    private static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),10) < 0) return false;
        }
        return true;
    }

    /**
     * Validates text field user input.
     * @param s input.
     * @return True if is an integer between 2 to 100.
     */
    public boolean isValidInteger(String s){

        if (!isInteger(s)) return false;

        int sConverted = Integer.parseInt(s);
        return sConverted >= 2 && sConverted <= 100;
    }

}
