package civilianStructure.civilianModule;

import java.util.ArrayList;
import java.util.List;

public class CivilianManager {

    private List<Civilian> civilians = new ArrayList<>();

    public CivilianManager() {
        System.out.println("CivilianManager initializing");
        System.out.println("CivilianManager initialized successfully");
    }
	
	public Civilian getCivilian(String name) {
		// TODO
		return civilians.get(0);
	}

	public void addCivilian(Civilian civilian) {
		this.civilians.add(civilian);
	}

}