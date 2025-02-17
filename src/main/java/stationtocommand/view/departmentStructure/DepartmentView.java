package stationtocommand.view.departmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, Department department) {
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, department);
        utilsView.addBreadCrumb(breadCrumbBar, department);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        stationListView.show(breadCrumbBar, pane1, pane2, department.getStations());
    }

}
