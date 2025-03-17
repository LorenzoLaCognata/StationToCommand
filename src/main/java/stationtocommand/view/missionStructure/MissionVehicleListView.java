package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(navigationPanel, "Vehicles");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
                showSidebar(breadCrumbBar, navigationPanel, missionVehicleLink);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, MissionUnitLink missionUnitLink) {
        utilsView.addSectionTitleLabel(navigationPanel, "Vehicles");
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            showSidebar(breadCrumbBar, navigationPanel, missionVehicleLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, MissionVehicleLink missionVehicleLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showMissionVehicleIcon(labelPane, missionVehicleLink);
        showMissionVehicleButton(breadCrumbBar, navigationPanel, labelPane, missionVehicleLink);
    }

    private void showMissionVehicleIcon(Pane labelPane, MissionVehicleLink missionVehicleLink) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType().getResourcePath(), missionVehicleLink.getVehicle().getVehicleType().toString());
    }

    private void showMissionVehicleButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, MissionVehicleLink missionVehicleLink) {
        utilsView.addButtonToPane(labelPane, missionVehicleLink.getVehicle().toString(), (_ -> missionVehicleView.show(breadCrumbBar, navigationPanel, missionVehicleLink)));
    }

}