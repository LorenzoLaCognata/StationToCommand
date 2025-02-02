package view.responderStructure.responderListModule;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.responderStructure.responderModule.Responder;
import view.responderStructure.responderModule.ResponderView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class ResponderListView {

    private final UtilsView utilsView;
    private final ResponderView responderView;

    public ResponderListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.responderView = new ResponderView(utilsView);
    }

    public void show(Pane pane, List<Node> nodes, List<Responder> responders) {
        Label respondersSeparator = new Label("----------------------\nResponders");
        pane.getChildren().addAll(respondersSeparator);

        for (Responder responder : responders) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes = utilsView.resetAndAddToPane(pane, nodes, button);
                responderView.show(pane, nextNodes, responder);
            });
            utilsView.addToSidebar(pane, button, responder.toString(), responder.getRank().toString());
        }
    }

}
