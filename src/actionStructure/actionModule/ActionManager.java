package actionStructure.actionModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActionManager {

    private final List<Action> actions = new ArrayList<>();

    public ActionManager() {
			System.out.println("ActionManager initializing");
			System.out.println("ActionManager initialized successfully");
    }

	@Override
	public String toString() {
		return actions.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public Action getAction() {
		// TODO
		return actions.get(0);
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

}