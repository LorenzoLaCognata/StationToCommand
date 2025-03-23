package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleListView {

    private final UtilsView utilsView;
    private final MissionVehicleView missionVehicleView;

    public MissionVehicleListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionVehicleView = new MissionVehicleView(utilsView);
    }

    public MissionVehicleView getMissionVehicleView() {
        return missionVehicleView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicles");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
                showSidebar(breadCrumbBar, view, missionVehicleLink);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionUnitLink missionUnitLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicles");
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            showSidebar(breadCrumbBar, view, missionVehicleLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, MissionVehicleLink missionVehicleLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionVehicleIcon(horizontalDetailsPane, missionVehicleLink);
        showMissionVehicleButton(breadCrumbBar, view, horizontalDetailsPane, missionVehicleLink);
    }

    private void showMissionVehicleIcon(Pane pane, MissionVehicleLink missionVehicleLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType().getResourcePath(), missionVehicleLink.getVehicle().getVehicleType().toString());
    }

    private void showMissionVehicleButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, MissionVehicleLink missionVehicleLink) {
        utilsView.addButtonToPane(pane, missionVehicleLink.getVehicle().toString(), (_ -> missionVehicleView.show(breadCrumbBar, view, missionVehicleLink)));
    }

}