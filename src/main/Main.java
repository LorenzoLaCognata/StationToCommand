package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Model;
import view.View;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Model model = new Model();
    private final View view = new View();
    private final Controller controller = new Controller(model, view);

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(view.getParentScene(), 800, 600);

        primaryStage.setTitle("Station To Command");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}