package stationtocommand.view.departmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.stationStructure.StationListView;

public class DepartmentView {

    private final UtilsView utilsView;
    private final StationListView stationListView;

    public DepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationListView = new StationListView(utilsView);
    }

    public StationListView getStationListView() {
        return stationListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Department department) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, department);
        utilsView.addBreadCrumb(breadCrumbBar, department);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showDepartmentDetails(navigationPanel, department);
        stationListView.show(breadCrumbBar, navigationPanel, worldMap, department.getStations());
    }

    private void showDepartmentDetails(Pane navigationPanel, Department department) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.departmentIconPath(department.getDepartmentType()));
        utilsView.addMainTitleLabel(labelPane, department.toString());
    }

}
