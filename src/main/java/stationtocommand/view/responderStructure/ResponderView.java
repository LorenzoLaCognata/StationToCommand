package stationtocommand.view.responderStructure;

import javafx.scene.layout.Pane;
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

    public void show(View view, Responder responder) {
        View.viewRunnable = () -> show(view, responder);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), responder);
        view.getNavigationPanel().clear();
        showSidebar(view, responder);
    }

    private void showSidebar(View view, Responder responder) {
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