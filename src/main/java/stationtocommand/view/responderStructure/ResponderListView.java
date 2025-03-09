package stationtocommand.view.responderStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, List<Responder> responders) {
        utilsView.addSectionTitleLabel(navigationPanel, "Responders");

        // TODO: review different approach to avoid showing too many responders
        if (responders.size()<20) {
            for (Responder responder : responders) {
                showSidebar(breadCrumbBar, navigationPanel, responder);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Responder responder) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showResponderIcon(labelPane, responder);
        showResponderButton(breadCrumbBar, navigationPanel, labelPane, responder);
    }

    private void showResponderIcon(Pane labelPane, Responder responder) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, responder.getAppearanceType().getResourcePath());
    }

    private void showResponderButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, Responder responder) {
        utilsView.addButtonToPane(labelPane, responder.toString(), (_ -> responderView.show(breadCrumbBar, navigationPanel, responder)));
    }

}
