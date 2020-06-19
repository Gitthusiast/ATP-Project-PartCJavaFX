package ViewModel;

public enum MovementCode {

    BOTTOM_LEFT(1),
    DOWN(2),
    BOTTOM_RIGHT(3),
    LEFT(4),
    DEAFULT(5),
    RIGHT(6),
    TOP_LEFT(7),
    UP(8),
    TOP_RIGHT(9),
    ARROW_UP(10),
    ARROW_DOWN(11),
    ARROW_LEFT(12),
    ARROW_RIGHT(13);


    private final int movementCode;

    MovementCode(int movementCode){
        this.movementCode = movementCode;
    }

    public int getMovementCode() {
        return movementCode;
    }
}
