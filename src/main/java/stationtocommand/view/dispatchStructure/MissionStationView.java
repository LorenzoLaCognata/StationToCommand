package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionStationView extends ViewWithNode {

    private final MissionStationLink missionStationLink;
    private final UtilsView utilsView;
    private final Map<MissionUnitLink, MissionUnitView> missionUnitViews;

    // Constructor

    public MissionStationView(MissionStationLink missionStationLink, View view, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionStationLink.getStation().getStationType(), missionStationLink.getStation().getLocation()));
        this.missionStationLink = missionStationLink;
        this.utilsView = utilsView;

        this.missionUnitViews = new LinkedHashMap<>();
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            addMissionUnitView(missionUnitLink, view, utilsView);
        }
    }

    public void addMissionUnitView(MissionUnitLink missionUnitLink, View view, UtilsView utilsView) {
        if (!missionUnitViews.containsKey(missionUnitLink)) {
            MissionUnitView missionUnitView = new MissionUnitView(missionUnitLink, view, utilsView);
            missionUnitViews.put(missionUnitLink, missionUnitView);
            view.addToMap(missionUnitView.getNode());
        }
    }

    // Methods

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), missionStationLink.getStation().getStationType(), missionStationLink.getStation().toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionStationLink);
        view.clearNavigationPanel();
        utilsView.addIconAndTitleWithSubtitle(
                view.getTitlePane(),
                missionStationLink.getMission().getMissionType(), missionStationLink.getMission().toString(),
                missionStationLink.getStation().getStationType(), missionStationLink.getStation().toString()
        );
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), missionStationLink.getStation().getDepartment().defaultUnitType(), "Units", () -> showUnits(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionStationLink.getStation().getDepartment().defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionStationLink.getStation().getDepartment().defaultAppearanceType(), "Responders", () -> showResponders(view));
    }

    // Mission Station

    public MissionStationLink getMissionStationLink() {
        return missionStationLink;
    }

    // Mission Unit

    public Map<MissionUnitLink, MissionUnitView> getMissionUnitViews() {
        return missionUnitViews;
    }

    public MissionUnitView getMissionUnitView(MissionUnitLink missionUnitLink) {
        return missionUnitViews.get(missionUnitLink);
    }

    public Map<Location, List<Node>> missionUnitNodesByLocation() {
        return nodesByLocation(missionUnitViews, v -> v.getMissionUnitLink().getUnit().getStation().getLocation());
    }

    private void showUnits(View view) {
        View.viewRunnable = () -> showUnits(view);
        showNavigationPanelUnits(view);
        showMapUnits(view);
    }

    private void showNavigationPanelUnits(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionStationLink.missionUnitsByStatus(), missionStationLink.missionUnitsByTypeAndStatus());
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.addListDetails(view);
        }
    }

    private void showMapUnits(View view) {
        view.hideMap();
        missionUnitNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionUnitView missionUnitView : missionUnitViews.values()) {
            missionUnitView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.showNode();
    }

    // Mission Vehicle

    public Map<Location, List<Node>> missionVehicleNodesByLocation() {
        return nodesByLocation(getMissionVehicleViews(), v -> v.getMissionVehicleLink().getVehicle().getLocation());
        }

    public Map<MissionVehicleLink, MissionVehicleView> getMissionVehicleViews() {
        return missionUnitViews.values().stream()
                    .flatMap(missionUnitView -> missionUnitView.getMissionVehicleViews().entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (existing, _) -> existing,
                            LinkedHashMap::new
                    ));
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionStationLink.missionVehiclesByStatus(), missionStationLink.missionVehiclesByTypeAndStatus());
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
        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.showNode();
    }

    // Mission Responder

    public Map<MissionResponderLink, MissionResponderView> getMissionResponderViews() {
        return missionUnitViews.values().stream()
                .flatMap(missionUnitView -> missionUnitView.getMissionResponderViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, _) -> existing,
                        LinkedHashMap::new
                ));
    }

    public Map<Location, List<Node>> missionResponderNodesByLocation() {
        return nodesByLocation(getMissionResponderViews(), v -> v.getMissionResponderLink().getResponder().getLocation());
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionStationLink.missionRespondersByStatus(), missionStationLink.missionRespondersByRankAndStatus());
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
        MissionView missionView = view.getDispatchView().getMissionView(missionStationLink.getMission());
        missionView.showNode();
    }

}