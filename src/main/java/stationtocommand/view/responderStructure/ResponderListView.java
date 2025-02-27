package stationtocommand.view.responderStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.equipmentStructure.Equipment;
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
        utilsView.addSectionTitleLabel(pane, "Responders");

        // TODO: review different approach to avoid showing too many responders
        if (responders.size()<20) {
            for (Responder responder : responders) {
                showSidebar(breadCrumbBar, pane, responder);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Responder responder) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);

        showResponderIcon(responder, hBox);
        showResponderButton(breadCrumbBar, pane, responder, hBox);
    }

    private void showResponderIcon(Responder responder, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.responderIconPath(responder));
        hBox.getChildren().add(imageView);
    }

    private void showResponderButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Responder responder, HBox hBox) {
        Button button = new Button(responder.toString());
        button.setOnAction(_ -> responderView.show(breadCrumbBar, pane, responder));
        hBox.getChildren().add(button);
    }

}
