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
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.missionIconPath(missionResponderLink.getMission().getMissionType()));
        utilsView.addMainTitleLabel(labelPane, missionResponderLink.getMission().toString());
    }

    public void showMap(Pane worldMap, MissionResponderLink missionResponderLink) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation(), IconType.SMALL);
        String iconPath;
        switch (missionResponderLink.getResponder().getGender()) {
            case MALE -> iconPath = "/images/maleResponder.png";
            case FEMALE -> iconPath = "/images/femaleResponder.png";
            default -> iconPath = "/images/blank.png";
        }
        IconColor iconColor;
        switch (missionResponderLink.getResponder().getGender()) {
            case MALE -> iconColor = IconColor.DARK_BLUE;
            case FEMALE -> iconColor = IconColor.PERSIAN_RED;
            default -> iconColor = IconColor.BLANK;
        }
        ImageView imageView = utilsView.mediumShadowIcon(iconPath, iconColor);
        utilsView.addNodeToPane(worldMap, imageView, point);
    }

}
