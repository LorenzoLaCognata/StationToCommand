package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
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
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, missionResponderLink.getMission().getMissionType().getResourcePath());
        utilsView.addMainTitleLabel(labelPane, missionResponderLink.getMission().toString());
    }

    public void showMap(Pane worldMap, MissionResponderLink missionResponderLink) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(missionResponderLink.getResponder().getAppearanceType().getResourcePath());
        utilsView.addNodeToPane(worldMap, imageView, point);
    }

}
