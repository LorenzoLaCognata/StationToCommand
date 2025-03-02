package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(navigationPanel, "Units");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, missionUnitLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionUnitLink missionUnitLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showMissionUnitIcon(labelPane, missionUnitLink);
        showMissionUnitButton(breadCrumbBar, navigationPanel, worldMap, labelPane, missionUnitLink);
    }

    private void showMissionUnitIcon(Pane labelPane, MissionUnitLink missionUnitLink) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.unitIconPath(missionUnitLink.getUnit().getUnitType()));
    }

    private void showMissionUnitButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, MissionUnitLink missionUnitLink) {
        utilsView.addButtonToPane(labelPane, missionUnitLink.getUnit().toString(), (_ -> missionUnitView.show(breadCrumbBar, navigationPanel, worldMap, missionUnitLink)));
    }

}
