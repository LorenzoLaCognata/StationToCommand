package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MissionDepartmentView {

    private final MissionDepartmentLink missionDepartmentLink;
    private final UtilsView utilsView;
    private final Map<MissionStationLink, MissionStationView> missionStationViews;

    // Constructor

    public MissionDepartmentView(MissionDepartmentLink missionDepartmentLink, View view, UtilsView utilsView) {
        this.missionDepartmentLink = missionDepartmentLink;
        this.utilsView = utilsView;

        this.missionStationViews = new LinkedHashMap<>();

        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            addMissionStationView(missionStationLink, view, utilsView);
        }
    }

    public void addMissionStationView(MissionStationLink missionStationLink, View view, UtilsView utilsView) {
        if (!missionStationViews.containsKey(missionStationLink)) {
            MissionStationView missionStationView = new MissionStationView(missionStationLink, view, utilsView);
            missionStationViews.put(missionStationLink, missionStationView);
            view.addToMap(missionStationView.getNode());
        }
    }


    // Getter

    public Map<MissionStationLink, MissionStationView> getMissionStationViews() {
        return missionStationViews;
    }

    public MissionStationView getMissionStationView(MissionStationLink missionStationLink) {
        return missionStationViews.get(missionStationLink);
    }

    public Map<MissionUnitLink, MissionUnitView> getMissionUnitViews() {
        return missionStationViews.values().stream()
                .flatMap(missionStationView -> missionStationView.getMissionUnitViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<MissionVehicleLink, MissionVehicleView> getMissionVehicleViews() {
        return getMissionUnitViews().values().stream()
                .flatMap(missionUnitView -> missionUnitView.getMissionVehicleViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<MissionResponderLink, MissionResponderView> getMissionResponderViews() {
        return getMissionUnitViews().values().stream()
                .flatMap(missionResponderView -> missionResponderView.getMissionResponderViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));
    }


    // Methods

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), missionDepartmentLink.getDepartment().getDepartmentType(), missionDepartmentLink.getDepartment().toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionDepartmentLink);
        view.clearNavigationPanel();
        utilsView.addIconAndTitleWithSubtitle(
                view.getTitlePane(),
                missionDepartmentLink.getMission().getMissionType(), missionDepartmentLink.getMission().toString(),
                missionDepartmentLink.getDepartment().getDepartmentType(), missionDepartmentLink.getDepartment().toString()
        );
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), missionDepartmentLink.getDepartment().defaultStationType(), "Stations", () -> showStations(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionDepartmentLink.getDepartment().defaultUnitType(), "Units", () -> showUnits(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionDepartmentLink.getDepartment().defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionDepartmentLink.getDepartment().defaultAppearanceType(), "Responders", () -> showResponders(view));
    }


    // Mission Stations
    public Map<Location, List<Node>> missionStationNodesByLocation() {
        return utilsView.nodesByLocation(missionStationViews, v -> v.getMissionStationLink().getStation().getLocation());
    }

    private void showStations(View view) {
        View.viewRunnable = () -> showStations(view);
        showNavigationPanelStations(view);
        showMapStations(view);
    }

    private void showNavigationPanelStations(View view) {
        view.clearDetailsPane();
        utilsView.addSeparatorToPane(view.getDetailsPane());
        for (MissionStationView missionStationView : missionStationViews.values()) {
            missionStationView.addListDetails(view);
        }
    }

    private void showMapStations(View view) {
        view.hideMap();
        missionStationNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionStationView missionStationView : getMissionStationViews().values()) {
            missionStationView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.showNode();
    }


    // Mission Units
    public Map<Location, List<Node>> missionUnitNodesByLocation() {
        return utilsView.nodesByLocation(getMissionUnitViews(), v -> v.getMissionUnitLink().getUnit().getStation().getLocation());
    }

    private void showUnits(View view) {
        View.viewRunnable = () -> showUnits(view);
        showNavigationPanelUnits(view);
        showMapUnits(view);
    }

    private void showNavigationPanelUnits(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionDepartmentLink.missionUnitsByStatus(), missionDepartmentLink.missionUnitsByTypeAndStatus());
        for (MissionUnitView missionUnitView : getMissionUnitViews().values()) {
            missionUnitView.addListDetails(view);
        }
    }

    private void showMapUnits(View view) {
        view.hideMap();
        missionUnitNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionUnitView missionUnitView : getMissionUnitViews().values()) {
            missionUnitView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.showNode();
    }


    // Mission Vehicles

    public Map<Location, List<Node>> missionVehicleNodesByLocation() {
        return utilsView.nodesByLocation(getMissionVehicleViews(), v -> v.getMissionVehicleLink().getVehicle().getLocation());
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionDepartmentLink.missionVehiclesByStatus(), missionDepartmentLink.missionVehiclesByTypeAndStatus());
        for (MissionVehicleView missionVehicleView : getMissionVehicleViews().values()) {
            missionVehicleView.addListDetails(view);
        }
    }

    public void showMapVehicles(View view) {
        view.hideMap();
        missionVehicleNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionVehicleView missionVehicleView : getMissionVehicleViews().values()) {
            missionVehicleView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.showNode();
    }


    // Mission Responders

    public Map<Location, List<Node>> missionResponderNodesByLocation() {
        return utilsView.nodesByLocation(getMissionResponderViews(), v -> v.getMissionResponderLink().getResponder().getLocation());
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionDepartmentLink.missionRespondersByStatus(), missionDepartmentLink.missionRespondersByRankAndStatus());
        for (MissionResponderView missionResponderView : getMissionResponderViews().values()) {
            missionResponderView.addListDetails(view);
        }
    }

    private void showMapResponders(View view) {
        view.hideMap();
        missionResponderNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionResponderView missionResponderView : getMissionResponderViews().values()) {
            missionResponderView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionDepartmentLink.getMission());
        missionView.showNode();
    }

}