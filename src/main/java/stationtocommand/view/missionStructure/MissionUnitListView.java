package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionUnitListView {

    private final UtilsView utilsView;
    private final MissionUnitView missionUnitView;

    public MissionUnitListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionUnitView = new MissionUnitView(utilsView);
    }

    public MissionUnitView getMissionUnitView() {
        return missionUnitView;
    }

    public void show(View view, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Units");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            showSidebar(view, missionUnitLink);
        }
    }

    private void showSidebar(View view, MissionUnitLink missionUnitLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionUnitIcon(horizontalDetailsPane, missionUnitLink);
        showMissionUnitButton(view, horizontalDetailsPane, missionUnitLink);
    }

    private void showMissionUnitIcon(Pane pane, MissionUnitLink missionUnitLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType().getResourcePath(), missionUnitLink.getUnit().getUnitType().toString());
    }

    private void showMissionUnitButton(View view, Pane pane, MissionUnitLink missionUnitLink) {
        utilsView.addButtonToPane(pane, missionUnitLink.getUnit().toString(), (_ -> missionUnitView.show(view, missionUnitLink)));
    }

}
