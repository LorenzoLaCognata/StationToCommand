package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionStructure.MissionType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderView {

    private final UtilsView utilsView;

    public MissionResponderView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionResponderLink missionResponderLink) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, missionResponderLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionResponderLink);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showSidebar(breadCrumbBar, navigationPanel, missionResponderLink);
        showMap(worldMap, missionResponderLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, MissionResponderLink missionResponderLink) {
        showMissionResponderDetails(navigationPanel, missionResponderLink);
    }

    private void showMissionResponderDetails(Pane navigationPanel, MissionResponderLink missionResponderLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        MissionType missionType = missionResponderLink.getMission().getMissionType();
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.EMPTY, missionType.getResourcePath(), missionType.toString());
        utilsView.addMainTitleLabel(labelPane, missionResponderLink.getMission().toString());
    }

    public void showMap(Pane worldMap, MissionResponderLink missionResponderLink) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(missionResponderLink.getResponder().getAppearanceType().getResourcePath(), "");
        utilsView.addNodeToPane(worldMap, imageView, point);
    }

}
