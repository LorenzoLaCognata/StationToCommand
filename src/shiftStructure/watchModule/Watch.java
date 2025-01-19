package shiftStructure.watchModule;

import java.time.LocalDateTime;

public record Watch(LocalDateTime start, LocalDateTime end) {

    @Override
    public String toString() {
        return "[WATCH] " + this.start + " - " + this.end;
    }
}