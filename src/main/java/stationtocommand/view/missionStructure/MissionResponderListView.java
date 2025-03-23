package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionStationLink missionStationLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Responders");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                showSidebar(breadCrumbBar, view, missionResponderLink);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionUnitLink missionUnitLink) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Responders");
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            showSidebar(breadCrumbBar, view, missionResponderLink);
            showMap(view, missionResponderLink);
        }
    }

    public void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, MissionResponderLink missionResponderLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionResponderIcon(horizontalDetailsPane, missionResponderLink);
        showMissionResponderButton(breadCrumbBar, view, horizontalDetailsPane, missionResponderLink);
    }

    private void showMissionResponderIcon(Pane pane, MissionResponderLink missionResponderLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType().getResourcePath(), "");
    }

    private void showMissionResponderButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, MissionResponderLink missionResponderLink) {
        utilsView.addButtonToPane(pane, missionResponderLink.getResponder().toString(), (_ -> missionResponderView.show(breadCrumbBar, view, missionResponderLink)));
    }

    public void showMap(View view, MissionResponderLink missionResponderLink) {
        missionResponderView.showMap(view, missionResponderLink);
    }

}
