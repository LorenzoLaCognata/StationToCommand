package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
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
        System.out.println("Created MissionView for " + mission);
        this.mission = mission;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, mission.getMissionType(), mission.getLocation());
        this.utilsView = utilsView;

        this.missionDepartmentViews = new TreeMap<>();
        addMissionDepartmentViews(view, utilsView);
    }

    public Mission getMission() {
        return mission;
    }

    public Node getNode() {
        return node;
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    public SortedMap<MissionDepartmentLink, MissionDepartmentView> getMissionDepartmentViews() {
        return missionDepartmentViews;
    }

    public MissionDepartmentView getMissionDepartmentView(MissionDepartmentLink missionDepartmentLink) {
        return missionDepartmentViews.get(missionDepartmentLink);
    }

    public void addMissionDepartmentViews(View view, UtilsView utilsView) {
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            addMissionDepartmentView(view, utilsView, missionDepartmentLink);
        }
    }

    private void addMissionDepartmentView(View view, UtilsView utilsView, MissionDepartmentLink missionDepartmentLink) {
        if (!missionDepartmentViews.containsKey(missionDepartmentLink)) {
            MissionDepartmentView missionDepartmentView = new MissionDepartmentView(missionDepartmentLink, view, utilsView);
            missionDepartmentViews.put(missionDepartmentLink, missionDepartmentView);
        }
    }

    public void addDispatchDetailsMission(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getDetailsPane());
        addMissionIcon(horizontalDetailsPane);
        addMissionButton(view, horizontalDetailsPane);
        // TODO: testing removal of this part
        // showMissionDepartments(horizontalDetailsPane);
    }

    private void addMissionTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, mission.getMissionType());
        utilsView.addMainTitleLabel(horizontalTitlePane, mission.toString());
    }

    private void addMissionIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, mission.getMissionType());
    }

    private void addMissionButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, mission.toString(), (_ -> showMission(view)));
    }

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

    public void showMission(View view) {
        View.viewRunnable = () -> showMission(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), mission);
        view.clearNavigationPanel();
        view.hideMap();
        showMissionDetails(view);
        showMissionMap(view);
    }

    private void showMissionDetails(View view) {
        addMissionTitle(view);
        for (MissionDepartmentView missionDepartmentView : missionDepartmentViews.values()) {
            missionDepartmentView.addMissionDetailsMissionDepartment(view);
        }
    }

    public void showMissionMap(View view) {
        view.hideMap();
        setNodeVisible();
    }

}