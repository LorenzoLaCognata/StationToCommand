package stationtocommand.view.mainStructure;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NavigationPanel {

    private final VBox container = new VBox(5);
    private final HBox titlePane = new HBox(5);
    private final HBox buttonsPane = new HBox(5);
    private final VBox detailsPane = new VBox(15);

    public NavigationPanel() {
        container.setSpacing(15);
        container.setPadding(new Insets(15));
        container.setStyle("""
            -fx-background-color: rgba(20, 20, 20, 0.9);
            -fx-border-radius: 10;
        """);
        Separator separator = UtilsView.createSeparator();
        container.getChildren().addAll(titlePane, separator, buttonsPane, detailsPane);
    }

    public VBox getContainer() {
        return container;
    }

    public HBox getTitlePane() {
        return titlePane;
    }

    public HBox getButtonsPane() {
        return buttonsPane;
    }

    public VBox getDetailsPane() {
        return detailsPane;
    }

}