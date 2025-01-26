package model.actionStructure.actionModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionManager {

    private final List<Action> actions = new ArrayList<>();

    public ActionManager() {
    }

	@Override
	public String toString() {
		return actions.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public Action getAction(ActionType actionType) {
		return actions.stream()
				.filter(item -> item.getActionType().equals(actionType))
				.findAny()
				.orElse(null);
	}

	public List<Action> getActions() {
		return actions;
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

}