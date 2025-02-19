package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionDepartmentLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionStationListView {

    private final UtilsView utilsView;
    private final MissionStationView missionStationView;

    public MissionStationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionStationView = new MissionStationView(utilsView);
    }

    public MissionStationView getMissionStationView() {
        return missionStationView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionDepartmentLink missionDepartmentLink) {
        utilsView.addHeadingLabel(pane1, "Stations");
        for (MissionStationLink missionStationLink : missionDepartmentLink.getStationLinks()) {
            Button button = new Button(missionStationLink.getStation().toString());
            button.setOnAction(_ -> missionStationView.show(breadCrumbBar, pane1, pane2, missionStationLink));
            pane1.getChildren().add(button);
        }
    }

}
