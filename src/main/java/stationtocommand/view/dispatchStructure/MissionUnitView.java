package stationtocommand.view.dispatchStructure;

import javafx.scene.Node;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MissionUnitView {

    private final MissionUnitLink missionUnitLink;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<MissionVehicleLink, MissionVehicleView> missionVehicleViews;
    private final SortedMap<MissionResponderLink, MissionResponderView> missionResponderViews;

    public MissionUnitView(MissionUnitLink missionUnitLink, View view, UtilsView utilsView) {
        this.missionUnitLink = missionUnitLink;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().getStation().getLocation());
        this.utilsView = utilsView;

        this.missionVehicleViews = new TreeMap<>();
        addMissionVehicleViews(view, utilsView);

        this.missionResponderViews = new TreeMap<>();
        addMissionResponderViews(view, utilsView);
    }

    public void addMissionVehicleViews(View view, UtilsView utilsView) {
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            MissionVehicleView missionVehicleView = new MissionVehicleView(missionVehicleLink, view, utilsView);
            missionVehicleViews.put(missionVehicleLink, missionVehicleView);
            // TODO: restore after solving app freeze issue
            view.addToMapDISABLED(missionVehicleView.getNode());
        }
    }

    public void addMissionResponderViews(View view, UtilsView utilsView) {
        for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
            MissionResponderView missionResponderView = new MissionResponderView(missionResponderLink, view, utilsView);
            missionResponderViews.put(missionResponderLink, missionResponderView);
            // TODO: restore after solving app freeze issue
            view.addToMapDISABLED(missionResponderView.getNode());
        }
    }

    public MissionUnitLink getMissionUnitLink() {
        return missionUnitLink;
    }

    public Node getNode() {
        return node;
    }

    public void showNode() {
        node.setVisible(true);
    }

    public SortedMap<MissionVehicleLink, MissionVehicleView> getMissionVehicleViews() {
        return missionVehicleViews;
    }

    public MissionVehicleView getMissionVehicleView(MissionVehicleLink missionVehicleLink) {
        return missionVehicleViews.get(missionVehicleLink);
    }

    public Map<Location, List<Node>> missionVehicleNodesByLocation() {
        return missionVehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionVehicleLink().getVehicle().getLocation(),
                        Collectors.mapping(
                                MissionVehicleView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public SortedMap<MissionResponderLink, MissionResponderView> getMissionResponderViews() {
        return missionResponderViews;
    }

    public MissionResponderView getMissionResponderView(MissionResponderLink missionResponderLink) {
        return missionResponderViews.get(missionResponderLink);
    }

    public Map<Location, List<Node>> missionResponderNodesByLocation() {
        return missionResponderViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getMissionResponderLink().getResponder().getLocation(),
                        Collectors.mapping(
                                MissionResponderView::getNode,
                                Collectors.toList()
                        )
                ));
    }

    public void addListDetails(View view) {
        utilsView.addIconAndButton(view.getDetailsPane(), missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().toString(), (_ -> show(view)));
    }

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionUnitLink);
        view.clearNavigationPanel();
        utilsView.addIconAndTitleWithSubtitle(view.getTitlePane(),
                missionUnitLink.getMission().getMissionType(), missionUnitLink.getMission().toString(),
                missionUnitLink.getUnit().getUnitType(), missionUnitLink.getUnit().toString()
        );
        utilsView.addSelectedButtonWithGraphic(view.getButtonsPane(), missionUnitLink.getUnit().getStation().getDepartment().defaultVehicleType(), "Vehicles", () -> showVehicles(view));
        utilsView.addButtonWithGraphic(view.getButtonsPane(), missionUnitLink.getUnit().getStation().getDepartment().defaultAppearanceType(), "Responders", () -> showResponders(view));
    }

    private void showVehicles(View view) {
        View.viewRunnable = () -> showVehicles(view);
        showNavigationPanelVehicles(view);
        showMapVehicles(view);
    }

    private void showNavigationPanelVehicles(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionUnitLink.getUnit().vehiclesByStatus(), missionUnitLink.getUnit().vehiclesByTypeAndStatus());
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.addListDetails(view);
        }
    }

    public void showMapVehicles(View view) {
        view.hideMap();
        missionVehicleNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionVehicleView missionVehicleView : missionVehicleViews.values()) {
            missionVehicleView.showNode();
        }
        MissionView missionView = view.getDispatchView().getMissionView(missionUnitLink.getMission());
        missionView.setNodeVisible();
    }

    private void showResponders(View view) {
        View.viewRunnable = () -> showResponders(view);
        showNavigationPanelResponders(view);
        showMapResponders(view);
    }

    private void showNavigationPanelResponders(View view) {
        view.clearDetailsPane();
        utilsView.addTotalResources(view.getDetailsPane(), missionUnitLink.getUnit().respondersByStatus(), missionUnitLink.getUnit().respondersByRankAndStatus());
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.addListDetails(view);
        }
    }

    private void showMapResponders(View view) {
        view.hideMap();
        missionResponderNodesByLocation().forEach(utilsView::distributeResourceIconsByLocation);
        for (MissionResponderView missionResponderView : missionResponderViews.values()) {
            missionResponderView.showNode();
        }
    }

}