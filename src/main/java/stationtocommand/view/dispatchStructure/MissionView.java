package stationtocommand.view.dispatchStructure;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.organizationStructure.StationView;

import java.util.*;
import java.util.stream.Collectors;

public class MissionView {

    private final Mission mission;
    private final UtilsView utilsView;
    private final SortedMap<MissionDepartmentLink, MissionDepartmentView> missionDepartmentViews;

    public MissionView(Mission mission, UtilsView utilsView) {
        System.out.println("Created MissionView for " + mission);
        this.mission = mission;
        this.utilsView = utilsView;

        this.missionDepartmentViews = mission.getDepartmentLinks().stream()
                .map(departmentLink -> {
                    MissionDepartmentView missionDepartmentView = new MissionDepartmentView(departmentLink, utilsView);
                    return Map.entry(departmentLink, missionDepartmentView);
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
    }

    public MissionDepartmentView getMissionDepartmentView(MissionDepartmentLink missionDepartmentLink) {
        return missionDepartmentViews.get(missionDepartmentLink);
    }

    public void addMissionDepartmentView(MissionDepartmentLink missionDepartmentLink) {
        missionDepartmentViews.put(missionDepartmentLink, new MissionDepartmentView(missionDepartmentLink, utilsView));
    }

    public void addDispatchDetailsMission(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addMissionIcon(horizontalDetailsPane);
        addMissionButton(view, horizontalDetailsPane);
        showMissionDepartments(horizontalDetailsPane);
    }

    private void addMissionTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
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
        view.getNavigationPanel().clearAll();
        view.getWorldMap().setMapElementsNotVisible();
        addMissionTitle(view);
        for (MissionDepartmentView missionDepartmentView : missionDepartmentViews.values()) {
            missionDepartmentView.addMissionDetailsMissionDepartment(view);
        }
        // TODO: replace showMap
        //showMap(view, mission);
    }

    // TODO: replace showMap
    /*
    public void showMap(View view, Mission mission) {
        Point2D point = utilsView.locationToPoint(mission.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(mission.getMissionType().getResourcePath(), mission.getMissionType().toString());
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }
    */

}