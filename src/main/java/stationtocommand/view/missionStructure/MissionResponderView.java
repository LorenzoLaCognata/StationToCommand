package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderView {

    private final UtilsView utilsView;

    public MissionResponderView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionResponderLink missionResponderLink) {
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, missionResponderLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionResponderLink);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showSidebar(breadCrumbBar, pane1, missionResponderLink);
        showMap(pane2, missionResponderLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionResponderLink missionResponderLink) {
        showMissionResponderDetails(pane, missionResponderLink);
    }

    private void showMissionResponderDetails(Pane pane, MissionResponderLink missionResponderLink) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.missionIconPath(missionResponderLink.getMission().getMissionType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, missionResponderLink.getMission().toString());
        utilsView.addNodeToPane(pane, hBox);
    }

    public void showMap(Pane pane, MissionResponderLink missionResponderLink) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation());
        String iconPath;
        switch (missionResponderLink.getResponder().getGender()) {
            case MALE -> iconPath = "/images/maleResponder.png";
            case FEMALE -> iconPath = "/images/femaleResponder.png";
            default -> iconPath = "/images/blank.png";
        }
        String color = "";
        switch (missionResponderLink.getResponder().getGender()) {
            case MALE -> color = "blue";
            case FEMALE -> color = "red";
        }
        ImageView imageView = utilsView.mediumShadowIcon(iconPath, color);
        utilsView.addNodeToPane(pane, imageView, point);
    }

}
