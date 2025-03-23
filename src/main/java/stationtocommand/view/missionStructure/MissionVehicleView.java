package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleView {

    private final UtilsView utilsView;

    public MissionVehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionVehicleLink missionVehicleLink) {
        View.viewRunnable = () -> show(breadCrumbBar, view, missionVehicleLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionVehicleLink);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showSidebar(view, missionVehicleLink);
    }

    private void showSidebar(View view, MissionVehicleLink missionVehicleLink) {
        showMissionVehicleDetails(view, missionVehicleLink);
    }

    private void showMissionVehicleDetails(View view, MissionVehicleLink missionVehicleLink) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionVehicleLink.getMission().getMissionType().getResourcePath(), missionVehicleLink.getMission().getMissionType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionVehicleLink.getMission().toString());
    }

}