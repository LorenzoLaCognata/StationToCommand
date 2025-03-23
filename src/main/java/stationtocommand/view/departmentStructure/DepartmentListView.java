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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, Pane worldMap, List<Department> departments) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getTitlePane(), "Departments");
        for (Department department : departments) {
            showSidebar(breadCrumbBar, view, worldMap, department);
            for (Station station : department.getStations()) {
                departmentView.getStationListView().getStationView().showMap(worldMap, station);
            }
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Pane worldMap, Department department) {
        Pane horizontalEntryPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showDepartmentIcon(horizontalEntryPane, department);
        showDepartmentButton(breadCrumbBar, view, worldMap, horizontalEntryPane, department);
    }

    private void showDepartmentIcon(Pane horizontalEntryPane, Department department) {
        utilsView.addIconToPane(horizontalEntryPane, IconType.SMALL, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
    }

    private void showDepartmentButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane worldMap, Pane horizontalEntryPane, Department department) {
        utilsView.addButtonToPane(horizontalEntryPane, department.toString(), (_ -> departmentView.show(breadCrumbBar, view, worldMap, department)));
    }



}
