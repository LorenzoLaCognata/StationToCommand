package stationtocommand.model.watchStructure;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WatchManager {

    private final List<Watch> watches = new ArrayList<>();

    public WatchManager() {
		initWatches();
    }

	@Override
	public String toString() {
		return watches.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Watch> getWatches() {
		return watches;
	}

	public Watch getWatch(LocalDateTime start, LocalDateTime end) {
		return watches.stream()
				.filter(item -> item.start().equals(start) && item.end().equals(end))
				.findAny()
				.orElse(null);
	}

	public void addWatch(Watch Watch) {
		this.watches.add(Watch);
	}

	public void initWatches() {

		LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 5, 30);
		LocalDateTime endTime = startTime.plusHours(12).plusMinutes(30);
		addWatch(new Watch(startTime, endTime));

		while (endTime.isBefore(LocalDateTime.of(2025, 2, 1, 0, 0))) {
			startTime = endTime.minusMinutes(30);
			endTime = startTime.plusHours(12).plusMinutes(30);
			addWatch(new Watch(startTime, endTime));
		}
	}

}