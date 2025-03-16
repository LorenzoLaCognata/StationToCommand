package stationtocommand.view.responderStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class ResponderView {

    private final UtilsView utilsView;

    public ResponderView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Responder responder) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, responder);
        utilsView.addBreadCrumb(breadCrumbBar, responder);
        utilsView.clearPane(navigationPanel);
        showSidebar(breadCrumbBar, navigationPanel, responder);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Responder responder) {
        showResponderDetails(navigationPanel, responder);
    }

    private void showResponderDetails(Pane navigationPanel, Responder responder) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, responder.getAppearanceType().getResourcePath());
        utilsView.addMainTitleLabel(labelPane, responder.toString());
        Pane detailsPane = utilsView.createHBox(navigationPanel);
        utilsView.addBodyLabel(detailsPane, responder.getRank().toString());
    }

}