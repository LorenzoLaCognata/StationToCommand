package stationtocommand.view.departmentStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;


public class DepartmentListView {

    private final UtilsView utilsView;
    private final DepartmentView departmentView;

    public DepartmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.departmentView = new DepartmentView(utilsView);
    }

    public DepartmentView getDepartmentView() {
        return departmentView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Department> departments) {
        utilsView.addLabel(pane1, "Departments");
        for (Department department : departments) {
            showSidebar(breadCrumbBar, pane1, pane2, department);
            for (Station station : department.getStations()) {
                departmentView.getStationListView().getStationView().showMap(pane2, station);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Department department) {
        Button button = new Button(department.getDepartmentType().toString() + " Department");
        button.setOnAction(_ -> departmentView.show(breadCrumbBar, pane1, pane2, department));
        pane1.getChildren().add(button);
    }

}
