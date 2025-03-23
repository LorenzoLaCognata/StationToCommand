package stationtocommand.view.departmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, List<Department> departments) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getTitlePane(), "Departments");
        for (Department department : departments) {
            showSidebar(breadCrumbBar, view, department);
            for (Station station : department.getStations()) {
                departmentView.getStationListView().getStationView().showMap(view, station);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Department department) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showDepartmentIcon(horizontalDetailsPane, department);
        showDepartmentButton(breadCrumbBar, view, horizontalDetailsPane, department);
    }

    private void showDepartmentIcon(Pane pane, Department department) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
    }

    private void showDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, Department department) {
        utilsView.addButtonToPane(pane, department.toString(), (_ -> departmentView.show(breadCrumbBar, view, department)));
    }



}
