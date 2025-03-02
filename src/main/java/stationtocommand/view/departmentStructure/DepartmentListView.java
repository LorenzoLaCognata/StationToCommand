package stationtocommand.view.departmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, List<Department> departments) {
        utilsView.addSectionTitleLabel(navigationPanel, "Departments");
        for (Department department : departments) {
            showSidebar(breadCrumbBar, navigationPanel, worldMap, department);
            for (Station station : department.getStations()) {
                departmentView.getStationListView().getStationView().showMap(worldMap, station);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Department department) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showDepartmentIcon(labelPane, department);
        showDepartmentButton(breadCrumbBar, navigationPanel, worldMap, labelPane, department);
    }

    private void showDepartmentIcon(Pane labelPane, Department department) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, utilsView.departmentIconPath(department.getDepartmentType()));
    }

    private void showDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Pane labelPane, Department department) {
        utilsView.addButtonToPane(labelPane, department.toString(), (_ -> departmentView.show(breadCrumbBar, navigationPanel, worldMap, department)));
    }



}
