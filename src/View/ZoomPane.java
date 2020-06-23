package View;

import javafx.geometry.Bounds;
        import javafx.geometry.Point2D;
        import javafx.geometry.Pos;
        import javafx.scene.Group;
        import javafx.scene.Node;
        import javafx.scene.control.ScrollPane;
        import javafx.scene.layout.VBox;

public class ZoomPane extends ScrollPane {

    private double scalingFactor = 0.99;
    private Node displayedContent;
    private Node groupedNode;

    public ZoomPane(){ super(); }

    public void setScalingTarget(Node displayedContent){

        this.displayedContent = displayedContent;

        groupedNode = new Group(displayedContent);
        Node centeredNode = createCenteredNode(groupedNode);
        Node scrollableNode = createScrollableNode(centeredNode);
        setContent(scrollableNode);

        // center content in scroll pane
        setFitToHeight(true);
        setFitToWidth(true);

        setScale();
    }

    private void setScale() {

        displayedContent.setScaleY(scalingFactor);
        displayedContent.setScaleX(scalingFactor);
    }

    private Node createCenteredNode(Node node) {

        VBox centeredNode = new VBox(node);
        centeredNode.setAlignment(Pos.CENTER);

        return centeredNode;
    }

    private Node createScrollableNode(Node node) {

        node.setOnScroll(e -> {

            if (e.isControlDown()){

                e.consume();
                handleZoom(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
            }
        });

        return node;
    }

    private void handleZoom(double scrolledValue, Point2D mousePoint) {

        double zoomingRate = 0.02;
        double zoomFactor = Math.pow(2, scrolledValue * zoomingRate);

        Bounds layoutBounds = groupedNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();


        double oldY = (layoutBounds.getHeight() - viewportBounds.getHeight()) * getVvalue();
        double oldX = (layoutBounds.getWidth() - viewportBounds.getWidth()) * getHvalue();


        scalingFactor = scalingFactor * zoomFactor;
        setScale();
        layout();


        Point2D mouseContentPosition = displayedContent.parentToLocal(groupedNode.parentToLocal(mousePoint));
        Point2D mouseTranslation = displayedContent.getLocalToParentTransform().deltaTransform(mouseContentPosition.multiply(zoomFactor - 1));


        Bounds localBounds = groupedNode.getBoundsInLocal();
        setVvalue((oldY + mouseTranslation.getY()) / (localBounds.getHeight() - viewportBounds.getHeight()));
        setHvalue((oldX + mouseTranslation.getX()) / (localBounds.getWidth() - viewportBounds.getWidth()));
    }
}