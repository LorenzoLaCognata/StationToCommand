package stationtocommand.view.missionStructure;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

    public void show(View view, MissionResponderLink missionResponderLink) {
        View.viewRunnable = () -> show(view, missionResponderLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionResponderLink);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        showSidebar(view, missionResponderLink);
        showMap(view, missionResponderLink);
    }

    private void showSidebar(View view, MissionResponderLink missionResponderLink) {
        showMissionResponderDetails(view, missionResponderLink);
    }

    private void showMissionResponderDetails(View view, MissionResponderLink missionResponderLink) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        MissionType missionType = missionResponderLink.getMission().getMissionType();
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionType.getResourcePath(), missionType.toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionResponderLink.getMission().toString());
    }

    public void showMap(View view, MissionResponderLink missionResponderLink) {
        Point2D point = utilsView.locationToPoint(missionResponderLink.getResponder().getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(missionResponderLink.getResponder().getAppearanceType().getResourcePath(), "");
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }

}
