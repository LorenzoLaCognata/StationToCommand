package stationtocommand.view.dispatchStructure;

import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

import java.util.LinkedHashMap;
import java.util.Map;

public class MissionView extends ViewWithNode {

    private final Mission mission;
    private final UtilsView utilsView;
    private final Map<MissionDepartmentLink, MissionDepartmentView> missionDepartmentViews;

    // Constructor

    public MissionView(Mission mission, View view, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, mission.getMissionType(), mission.getLocation()));
        this.mission = mission;
        this.utilsView = utilsView;

        this.missionDepartmentViews = new LinkedHashMap<>();
        for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
            addMissionDepartmentView(missionDepartmentLink, view, utilsView);
        }
    }

    public void addMissionDepartmentView(MissionDepartmentLink missionDepartmentLink, View view, UtilsView utilsView) {
        if (!missionDepartmentViews.containsKey(missionDepartmentLink)) {
            MissionDepartmentView missionDepartmentView = new MissionDepartmentView(missionDepartmentLink, view, utilsView);
            missionDepartmentViews.put(missionDepartmentLink, missionDepartmentView);
        }
    }


    // Getter

    public Mission getMission() {
        return mission;
    }

    public MissionDepartmentView getMissionDepartmentView(MissionDepartmentLink missionDepartmentLink) {
        return missionDepartmentViews.get(missionDepartmentLink);
    }


    // Methods

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), mission.getMissionType(), mission.toString(), (_ -> show(view)));
        if (!mission.getDepartmentLinks().isEmpty()) {
            Pane horizontalPane = utilsView.createHBox(view.getDetailsPane());
            for (MissionDepartmentLink missionDepartmentLink : mission.getDepartmentLinks()) {
                if (!missionDepartmentLink.getStationLinks().isEmpty()) {
                    for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
                        if (!missionStationLink.getUnitLinks().isEmpty()) {
                            for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
                                utilsView.addIconToPane(horizontalPane, IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType());
                                utilsView.addBodyLabel(horizontalPane, missionUnitLink.getUnit().toString());
                            }
                        }
                    }
                }
            }
        }
        Separator separator = UtilsView.createSeparator();
        view.getDetailsPane().getChildren().add(separator);
    }

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