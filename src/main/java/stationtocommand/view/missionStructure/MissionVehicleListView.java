package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
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

    public void show(View view, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicles");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
                showSidebar(view, missionVehicleLink);
            }
        }
    }

    public void show(View view, MissionUnitLink missionUnitLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicles");
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            showSidebar(view, missionVehicleLink);
        }
    }

    private void showSidebar(View view, MissionVehicleLink missionVehicleLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionVehicleIcon(horizontalDetailsPane, missionVehicleLink);
        showMissionVehicleButton(view, horizontalDetailsPane, missionVehicleLink);
    }

    private void showMissionVehicleIcon(Pane pane, MissionVehicleLink missionVehicleLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType().getResourcePath(), missionVehicleLink.getVehicle().getVehicleType().toString());
    }

    private void showMissionVehicleButton(View view, Pane pane, MissionVehicleLink missionVehicleLink) {
        utilsView.addButtonToPane(pane, missionVehicleLink.getVehicle().toString(), (_ -> missionVehicleView.show(view, missionVehicleLink)));
    }

}