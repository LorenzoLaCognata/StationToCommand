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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, Responder responder) {
        View.viewRunnable = () -> show(breadCrumbBar, view, responder);
        utilsView.addBreadCrumb(breadCrumbBar, responder);
        view.getNavigationPanel().clear();
        showSidebar(breadCrumbBar, view, responder);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Responder responder) {
        showResponderDetails(view, responder);
    }

    private void showResponderDetails(View view, Responder responder) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, responder.getAppearanceType().getResourcePath(), "");
        utilsView.addMainTitleLabel(horizontalTitlePane, responder.toString());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addBodyLabel(horizontalDetailsPane, responder.getRank().toString());
    }

}