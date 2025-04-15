package stationtocommand.view.organizationStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class ResponderView {

    private final Responder responder;
    private final UtilsView utilsView;

    public ResponderView(Responder responder, UtilsView utilsView) {
        this.responder = responder;
        this.utilsView = utilsView;
    }

    public void addStationDetailsResponder(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addResponderIcon(horizontalDetailsPane);
        addResponderButton(view, horizontalDetailsPane);
        addResponderStatusIcon(horizontalDetailsPane);
    }

    private void addResponderIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responder.getAppearanceType());
    }

    private void addResponderButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, responder.toString(), (_ -> showResponder(view)));
    }

    private void addResponderStatusIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responder.getResponderStatus());
    }

    public void showResponder(View view) {
        View.viewRunnable = () -> showResponder(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), responder);
        view.getNavigationPanel().clearAll();
        showResponderDetails(view);
    }

    private void showResponderDetails(View view) {
        addResponderTitle(view);

        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addBodyLabel(horizontalDetailsPane, responder.getRank().toString());
    }

    private void addResponderTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, responder.getAppearanceType());
        utilsView.addMainTitleLabel(horizontalTitlePane, responder.toString());
    }


}