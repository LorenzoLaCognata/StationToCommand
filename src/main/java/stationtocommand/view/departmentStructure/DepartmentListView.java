package stationtocommand.view.departmentStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        utilsView.addSectionTitleLabel(pane1, "Departments");
        for (Department department : departments) {
            showSidebar(breadCrumbBar, pane1, pane2, department);
            for (Station station : department.getStations()) {
                departmentView.getStationListView().getStationView().showMap(pane2, station);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Department department) {
        HBox hBox = new HBox(10);
        pane1.getChildren().add(hBox);

        showDepartmentIcon(department, hBox);
        showDepartmentButton(breadCrumbBar, pane1, pane2, department, hBox);
    }

    private void showDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Department department, HBox hBox) {
        Button button = new Button(department.getDepartmentType().toString() + " Department");
        button.setOnAction(_ -> departmentView.show(breadCrumbBar, pane1, pane2, department));
        hBox.getChildren().add(button);
    }

    private void showDepartmentIcon(Department department, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.departmentIconPath(department.getDepartmentType()));
        hBox.getChildren().add(imageView);
    }

}
