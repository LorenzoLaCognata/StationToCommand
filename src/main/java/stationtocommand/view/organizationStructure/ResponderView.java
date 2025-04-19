package stationtocommand.view.organizationStructure;

import javafx.scene.Group;
import javafx.scene.Node;
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

    public Responder getResponder() {
        return responder;
    }

    public void addStationDetailsResponder(View view, Group responderIcons, Node responderNode) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addResponderIcon(horizontalDetailsPane);
        addResponderButton(view, horizontalDetailsPane, responderIcons, responderNode);
        addResponderStatusIcon(horizontalDetailsPane);
    }

    private void addResponderTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, responder.getAppearanceType());
        utilsView.addMainTitleLabel(horizontalTitlePane, responder.toString());
    }
    private void addResponderIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responder.getAppearanceType());
    }

    private void addResponderButton(View view, Pane pane, Group responderIcons, Node responderNode) {
        utilsView.addButtonToPane(pane, responder.toString(), (_ -> showResponder(view, responderIcons, responderNode)));
    }

    private void addResponderStatusIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responder.getResponderStatus());
    }

    public void showResponder(View view, Group responderIcons, Node responderNode) {
        View.viewRunnable = () -> showResponder(view, responderIcons, responderNode);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), responder);
        view.getNavigationPanel().clearAll();
        showResponderDetails(view);
        showResponderMap(view, responderIcons, responderNode);
    }

    private void showResponderDetails(View view) {
        addResponderTitle(view);

        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addBodyLabel(horizontalDetailsPane, responder.getRank().toString());
    }

    private void showResponderMap(View view, Group responderIcons, Node responderNode) {
        for (Node responderIcon : responderIcons.getChildren()) {
            responderIcon.setVisible(responderIcon.equals(responderNode));
        }
    }

}