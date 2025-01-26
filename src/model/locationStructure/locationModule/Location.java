package model.locationStructure.locationModule;

public record Location(float latitude, float longitude) {

    @Override
    public String toString() {
        String latDirection = this.latitude >= 0 ? "N" : "S";
        String lonDirection = this.longitude >= 0 ? "E" : "W";

        return String.format("%.6f° %s, %.6f° %s",
                Math.abs(this.latitude), latDirection,
                Math.abs(this.longitude), lonDirection);
    }

}