package stationtocommand.view.mainStructure;

import javafx.scene.Node;
import stationtocommand.model.locationStructure.Location;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public <X, Y extends ViewWithNode> Map<Location, List<Node>> nodesByLocation(Map<X, Y> viewWithNode, Function<Y, Location> viewLocationFunction) {
        return viewWithNode.values().stream()
                .collect(Collectors.groupingBy(
                        viewLocationFunction,
                        Collectors.mapping(
                                Y::getNode,
                                Collectors.toList()
                        )
                ));
    }

}