package actionStructure.actionModule;

import java.util.ArrayList;
import java.util.List;

public class ActionManager {

    private List<Action> actions = new ArrayList<>();

    public ActionManager() {
			System.out.println("ActionManager initializing");
			System.out.println("ActionManager initialized successfully");
    }
	
	public Action getAction() {
		// TODO
		return actions.get(0);
	}

	public void addAction(Action action) {
		this.actions.add(action);
	}

}