package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionStationListView {

    private final UtilsView utilsView;
    private final MissionStationView missionStationView;

    public MissionStationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionStationView = new MissionStationView(utilsView);
    }

    public MissionStationView getMissionStationView() {
        return missionStationView;
    }

    public void show(View view, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            showSidebar(view, missionStationLink);
        }
    }

    private void showSidebar(View view, MissionStationLink missionStationLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionStationIcon(horizontalDetailsPane, missionStationLink);
        showMissionStationButton(view, horizontalDetailsPane, missionStationLink);
    }

    private void showMissionStationIcon(Pane pane, MissionStationLink missionStationLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType());
    }

    private void showMissionStationButton(View view, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addButtonToPane(pane, missionStationLink.getStation().toString(), (_ -> missionStationView.show(view, missionStationLink)));
    }

}
