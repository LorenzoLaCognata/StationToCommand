package stationtocommand.model.vehicleStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum FireVehicleType implements VehicleType {
  PUMPER("Pumper Fire Truck", "/images/vehicle/pumperFireTruck.png"),
  TANKER("Tanker Fire Truck", "/images/vehicle/tankerFireTruck.png"),
  LADDER("Aerial Ladder Truck", "/images/vehicle/aerialLadderTruck.png"),
  TOWER("Tower Ladder Truck", "/images/vehicle/towerLadderTruck.png"),
  RESCUE("Rescue Truck", "/images/vehicle/rescueTruck.png"),
  HEAVY_RESCUE("Heavy Rescue Truck", "/images/vehicle/heavyRescueTruck.png");

  private final String name;
  private final Image image;

  FireVehicleType(String name, String resourcePath) {
    this.name = name;
    this.image = new Image(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
  }

  @Override
  public String toString() {
    return this.name;
  }

  public Image getImage() {
    return Objects.requireNonNullElse(image, new Image("/images/blank.png"));
  }

  @Override
  public FireVehicleType[] getValues() {
    return values();
  }

  @Override
  public FireVehicleType getPrimaryValue() {
    return values()[0];
  }

}