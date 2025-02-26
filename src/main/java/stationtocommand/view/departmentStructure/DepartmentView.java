package stationtocommand.view.departmentStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        showDepartmentDetails(pane1, department);
        stationListView.show(breadCrumbBar, pane1, pane2, department.getStations());
    }

    private void showDepartmentDetails(Pane pane1, Department department) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.departmentIconPath(department.getDepartmentType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, department.toString());
        utilsView.addNodeToPane(pane1, hBox);
    }

}
