package view.utilsStructure.utilsModule;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class UtilsView {

    public UtilsView() {
    }

    public void addPaneEntry(Pane pane, Button button, String text1, String text2) {
        button.setText(text1);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMinWidth(200);
        Label label = new Label(text2);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(button, label);
        pane.getChildren().addAll(hBox);
    }

    public List<Control> setBreadcrumbs(Pane pane, List<Control> controls, Control newControl) {
        resetBreadcrumbs(pane, controls);
        pane.getChildren().add(newControl);
        List<Control> newControls = new ArrayList<>(controls);
        newControls.add(newControl);
        return newControls;
    }

    public List<Control> setBreadcrumbs(Pane pane) {
        resetBreadcrumbs(pane);
        return new ArrayList<>();
    }

    public void resetBreadcrumbs(Pane pane, List<Control> controls) {
        pane.getChildren().clear();
        pane.getChildren().addAll(controls);
    }

    public void resetBreadcrumbs(Pane pane) {
        pane.getChildren().clear();
    }

}
