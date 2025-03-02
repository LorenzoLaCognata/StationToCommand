package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionResponderListView {

    private final UtilsView utilsView;
    private final MissionResponderView missionResponderView;

    public MissionResponderListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionResponderView = new MissionResponderView(utilsView);
    }

    public MissionResponderView getMissionResponderView() {
        return missionResponderView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(navigationPanel, "Responders");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                showSidebar(breadCrumbBar, navigationPanel, worldMap, missionResponderLink);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionUnitLink missionUnitLink) {
        utilsView.addSectionTitleLabel(navigationPanel, "Responders");
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, missionResponderLink);
            showMap(worldMap, missionResponderLink);
        }
    }

    public void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionResponderLink missionResponderLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showMissionResponderIcon(labelPane, missionResponderLink);
        showMissionResponderButton(breadCrumbBar, navigationPanel, worldMap, labelPane, missionResponderLink);
    }

    private void showMissionResponderIcon(Pane labelPane, MissionResponderLink missionResponderLink) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.responderIconPath(missionResponderLink.getResponder()));
    }

    private void showMissionResponderButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, MissionResponderLink missionResponderLink) {
        utilsView.addButtonToPane(labelPane, missionResponderLink.getResponder().toString(), (_ -> missionResponderView.show(breadCrumbBar, navigationPanel, worldMap, missionResponderLink)));
    }

    public void showMap(Pane worldMap, MissionResponderLink missionResponderLink) {
        missionResponderView.showMap(worldMap, missionResponderLink);
    }

}
