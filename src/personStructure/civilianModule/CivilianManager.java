package personStructure.civilianModule;

import java.util.ArrayList;
import java.util.List;

public class CivilianManager {

    private final List<Civilian> civilians = new ArrayList<>();

    public CivilianManager() {
    }

	public Civilian getCivilian(String firstName, String lastName) {
		return civilians.stream()
				.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
				.findFirst()
				.orElse(null);
	}

	public void addCivilian(Civilian civilian) {
		this.civilians.add(civilian);
	}

}