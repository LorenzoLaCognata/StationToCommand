package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.SortedMap;
import java.util.TreeMap;

public class MissionView {

    private final Mission mission;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<MissionDepartmentLink, MissionDepartmentView> missionDepartmentViews;

    public MissionView(Mission mission, View view, UtilsView utilsView) {
        this.mission = mission;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, mission.getMissionType(), mission.getLocation());
        this.utilsView = utilsView;

        this.missionDepartmentViews = new TreeMap<>();
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            addMissionDepartmentView(missionDepartmentLink, view, utilsView);
        }
    }

    public Mission getMission() {
        return mission;
    }

    public Node getNode() {
        return node;
    }

    public void showNode() {
        node.setVisible(true);
    }

    public SortedMap<MissionDepartmentLink, MissionDepartmentView> getMissionDepartmentViews() {
        return missionDepartmentViews;
    }

    public MissionDepartmentView getMissionDepartmentView(MissionDepartmentLink missionDepartmentLink) {
        return missionDepartmentViews.get(missionDepartmentLink);
    }

    public void addMissionDepartmentView(MissionDepartmentLink missionDepartmentLink, View view, UtilsView utilsView) {
        if (!missionDepartmentViews.containsKey(missionDepartmentLink)) {
            MissionDepartmentView missionDepartmentView = new MissionDepartmentView(missionDepartmentLink, view, utilsView);
            missionDepartmentViews.put(missionDepartmentLink, missionDepartmentView);
        }
    }

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), mission.getMissionType(), mission.toString(), (_ -> show(view)));
        // TODO: testing removal of this part -- could be refactored in case!
        // showMissionDepartments(horizontalDetailsPane);
    }

    /*
    private void showMissionDepartments(Pane pane) {
        if (!mission.getDepartmentLinks().isEmpty()) {
            for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
                utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, missionDepartmentLink.getDepartment().getDepartmentType());
                for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                    if (!missionStationLink.getUnitLinks().isEmpty()) {
                        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
                            utilsView.addBodyLabel(pane, missionUnitLink.getUnit().toString());
                        }
                    }
                }
            }
        }
    }
    */

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), mission);
        view.clearNavigationPanel();
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addIconAndTitle(view.getTitlePane(), mission.getMissionType(), mission.toString());
        for (MissionDepartmentView missionDepartmentView : missionDepartmentViews.values()) {
            missionDepartmentView.addListDetails(view);
        }
    }

    public void showMap(View view) {
        view.hideMap();
        showNode();
    }

}