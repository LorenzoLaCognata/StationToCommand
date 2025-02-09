package view.missionStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.Mission;
import model.responderStructure.Responder;
import view.mainStructure.UtilsView;

import java.util.List;

public class MissionResponderListView {

    private final UtilsView utilsView;
    private final MissionResponderView missionResponderView;

    public MissionResponderListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionResponderView = new MissionResponderView();
    }

    public void show(Pane pane, List<Node> nodes, Mission mission, List<Responder> responders) {
        Label respondersSeparator = new Label("----------------------\nResponders");
        pane.getChildren().addAll(respondersSeparator);

        for (Responder responder : responders) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes = utilsView.resetAndAddToPane(pane, nodes, button);
                missionResponderView.show(pane);
            });
            utilsView.addToSidebar(pane, button, responder.toString(), responder.getRank().toString());
        }
    }

}
