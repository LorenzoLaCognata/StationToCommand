package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderView {

    private final UtilsView utilsView;

    public MissionResponderView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionResponderLink missionResponderLink) {
        utilsView.addBreadCrumb(breadCrumbBar, missionResponderLink);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showMap(pane2, missionResponderLink);
    }

    public void showMap(Pane pane, MissionResponderLink missionResponderLink) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation());
        String iconPath;
        switch (missionResponderLink.getResponder().getGender()) {
            case MALE -> iconPath = "/images/maleResponder.png";
            case FEMALE -> iconPath = "/images/femaleResponder.png";
            default -> iconPath = "/images/blank.png";
        }
        ImageView imageView = utilsView.generateIcon(iconPath);
        utilsView.addImageToMap(pane, imageView, point);
    }

}
