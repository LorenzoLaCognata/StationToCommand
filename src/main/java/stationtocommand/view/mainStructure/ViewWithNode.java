package stationtocommand.view.mainStructure;

import javafx.scene.Node;

public abstract class ViewWithNode {

    private final Node node;

    public ViewWithNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    };

    public void showNode() {
        node.setVisible(true);
    }

}