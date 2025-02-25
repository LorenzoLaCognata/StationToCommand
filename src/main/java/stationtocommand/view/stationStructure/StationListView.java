package stationtocommand.view.stationStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.ArrayList;
import java.util.List;

public class StationListView {

    private boolean isSelected = false;

    private final UtilsView utilsView;
    private final StationView stationView;

    public StationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationView = new StationView(utilsView);
    }

    public StationView getStationView() {
        return stationView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Department department, List<Station> stations) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.departmentIconPath(department.getDepartmentType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addHeading2LabelToPane(hBox, department.toString());
        utilsView.addNodeToPane(pane1, hBox);

        utilsView.addHeadingLabelToPane(pane1, "Stations");
        for (Station station : stations) {
            showSidebar(breadCrumbBar, pane1, pane2, station);
            stationView.showMap(pane2, station);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Station station) {
        HBox hBox = new HBox(10);

        ImageView imageView = utilsView.smallIcon(utilsView.stationIconPath(station.getDepartment().getDepartmentType()));
        hBox.getChildren().add(imageView);

        Button button = new Button(station.toString());
        button.setOnAction(_ -> stationView.show(breadCrumbBar, pane1, pane2, station));
        hBox.getChildren().add(button);

        List<UnitType> unitTypes;

        switch (station.getDepartment().getDepartmentType()) {
            case DepartmentType.FIRE_DEPARTMENT -> unitTypes = List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> unitTypes = List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> unitTypes = List.of(MedicUnitType.values());
            default -> unitTypes = new ArrayList<>();
        }

        for (UnitType unitType : unitTypes) {
            ImageView unitImageView = utilsView.smallIcon(utilsView.unitIconPath(unitType));
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                unitImageView.setOpacity(0.2);
            }
            hBox.getChildren().add(unitImageView);
        }

        pane1.getChildren().add(hBox);
    }

}
