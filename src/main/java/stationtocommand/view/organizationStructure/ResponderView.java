package stationtocommand.view.organizationStructure;

import javafx.scene.Node;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class ResponderView {

    private final Responder responder;
    private final Node node;
    private final UtilsView utilsView;

    public ResponderView(Responder responder, UtilsView utilsView) {
        this.responder = responder;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, responder.getAppearanceType(), responder.getLocation());
        this.utilsView = utilsView;
    }

    public Responder getResponder() {
        return responder;
    }

    public Node getNode() {
        return node;
    }

    public void showNode() {
        node.setVisible(true);
    }

    public void addListDetails(View view) {
        utilsView.addIconAndButtonAndIcon(view.getDetailsPane(), responder.getAppearanceType(), responder.toString(), (_ -> show(view)), responder.getResponderStatus());
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), responder);
        view.clearNavigationPanel();
        utilsView.addIconAndTitle(view.getTitlePane(), responder.getAppearanceType(), responder.toString());
        utilsView.addLabelAndText(view.getDetailsPane(), "Gender", responder.getGender().toString());
        utilsView.addLabelAndIcon(view.getDetailsPane(), "Rank", responder.getRank().getRankType());
    }

    private void showMap(View view) {
        view.hideMap();
        showNode();
    }

}