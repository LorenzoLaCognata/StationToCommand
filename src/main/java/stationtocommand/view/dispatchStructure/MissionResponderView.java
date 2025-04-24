package stationtocommand.view.dispatchStructure;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderView {

    private final MissionResponderLink missionResponderLink;
    private final Node node;
    private final UtilsView utilsView;

    public MissionResponderView(MissionResponderLink missionResponderLink, View view, UtilsView utilsView) {
        this.missionResponderLink = missionResponderLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType(), missionResponderLink.getResponder().getLocation());
        this.utilsView = utilsView;
    }

    public MissionResponderLink getMissionResponderLink() {
        return missionResponderLink;
    }

    public Node getNode() {
        return node;
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionResponderLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();
        showSidebar(view);
        showMap(view);
    }

    private void showSidebar(View view) {
        showMissionResponderDetails(view);
    }

    private void showMissionResponderDetails(View view) {
        Pane titleAndSubtitlePane = utilsView.createVBox(view.getNavigationPanel().getTitlePane());
        Pane horizontalTitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionResponderLink.getMission().getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionResponderLink.getMission().toString());
        Pane horizontalSubtitlePane = utilsView.createHBox(titleAndSubtitlePane);
        utilsView.addIconToPane(horizontalSubtitlePane, IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType());
        utilsView.addMainSubtitleLabel(horizontalSubtitlePane, missionResponderLink.getResponder().toString());
    }

    public void addMissionStationDetailsResponder(View view) {
        addMissionUnitDetailsResponder(view);
    }

    public void addMissionUnitDetailsResponder(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addMissionResponderIcon(horizontalDetailsPane);
        addMissionResponderButton(view, horizontalDetailsPane);
    }

    private void addMissionResponderIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType());
    }

    private void addMissionResponderButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, missionResponderLink.getResponder().toString(), (_ -> show(view)));
    }

    public void showMap(View view) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(missionResponderLink.getResponder().getAppearanceType().getResourcePath(), "");
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }

}
