package stationtocommand.controller;

import stationtocommand.model.Model;
import stationtocommand.view.View;

public class Controller {

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.initialize(this);
        view.generateUI(model.departmentManager.getDepartments(), model.missionManager.getMissions());
    }

}
