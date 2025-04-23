package stationtocommand.view.dispatchStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

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

    public void show(View view, List<MissionResponderLink> missionResponderLinks) {
        for (MissionResponderLink missionResponderLink : missionResponderLinks) {
            showSidebar(view, missionResponderLink);
            showMap(view, missionResponderLink);
        }
    }

    public void showSidebar(View view, MissionResponderLink missionResponderLink) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showMissionResponderIcon(horizontalDetailsPane, missionResponderLink);
        showMissionResponderButton(view, horizontalDetailsPane, missionResponderLink);
    }

    private void showMissionResponderIcon(Pane pane, MissionResponderLink missionResponderLink) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionResponderLink.getResponder().getAppearanceType());
    }

    private void showMissionResponderButton(View view, Pane pane, MissionResponderLink missionResponderLink) {
        utilsView.addButtonToPane(pane, missionResponderLink.getResponder().toString(), (_ -> missionResponderView.show(view, missionResponderLink)));
    }

    public void showMap(View view, MissionResponderLink missionResponderLink) {
        missionResponderView.showMap(view, missionResponderLink);
    }

}
