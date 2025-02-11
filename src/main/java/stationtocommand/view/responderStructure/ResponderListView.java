package stationtocommand.view.responderStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class ResponderListView {

    private final UtilsView utilsView;
    private final ResponderView responderView;

    public ResponderListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.responderView = new ResponderView(utilsView);
    }

    public ResponderView getResponderView() {
        return responderView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, List<Responder> responders) {
        utilsView.addLabel(pane, "Responders");
        for (Responder responder : responders) {
            Button button = new Button(responder.toString());
            button.setOnAction(_ -> responderView.show(breadCrumbBar, pane, responder));
            pane.getChildren().add(button);
        }
    }

}
