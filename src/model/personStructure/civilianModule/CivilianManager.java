package model.personStructure.civilianModule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CivilianManager {

    private final List<Civilian> civilians = new ArrayList<>();

    public CivilianManager() {
    }

	@Override
	public String toString() {
		return civilians.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Civilian> getCivilians(String firstName, String lastName) {
		return civilians.stream()
				.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
				.toList();
	}

	public void addCivilian(Civilian civilian) {
		this.civilians.add(civilian);
	}

}