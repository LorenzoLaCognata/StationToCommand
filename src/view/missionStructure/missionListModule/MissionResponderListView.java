package view.missionStructure.missionListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.missionModule.Mission;
import model.responderStructure.responderModule.Responder;
import view.missionStructure.missionModule.MissionResponderView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionResponderListView {

    private final UtilsView utilsView;
    private final MissionResponderView missionResponderView;

    public MissionResponderListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionResponderView = new MissionResponderView();
    }

    public void show(Pane pane, List<Control> controls, Mission mission, List<Responder> responders) {
        Label respondersSeparator = new Label("----------------------\nResponders");
        pane.getChildren().addAll(respondersSeparator);

        for (Responder responder : responders) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.resetAndAddToPane(pane, controls, button);
                missionResponderView.show(pane);
            });
            utilsView.addToSidebar(pane, button, responder.toString(), responder.getRank().toString());
        }
    }

}
