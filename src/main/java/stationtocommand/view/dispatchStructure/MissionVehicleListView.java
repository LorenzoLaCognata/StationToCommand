package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

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

    public void show(View view, List<MissionVehicleLink> missionVehicleLinks) {
        for (MissionVehicleLink missionVehicleLink : missionVehicleLinks) {
            showSidebar(view, missionVehicleLink);
        }
    }

    private void showSidebar(View view, MissionVehicleLink missionVehicleLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionVehicleIcon(horizontalDetailsPane, missionVehicleLink);
        showMissionVehicleButton(view, horizontalDetailsPane, missionVehicleLink);
    }

    private void showMissionVehicleIcon(Pane pane, MissionVehicleLink missionVehicleLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionVehicleLink.getVehicle().getVehicleType());
    }

    private void showMissionVehicleButton(View view, Pane pane, MissionVehicleLink missionVehicleLink) {
        utilsView.addButtonToPane(pane, missionVehicleLink.getVehicle().toString(), (_ -> missionVehicleView.show(view, missionVehicleLink)));
    }

}