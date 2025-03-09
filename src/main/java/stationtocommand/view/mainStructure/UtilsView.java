package stationtocommand.view.mainStructure;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.equipmentStructure.EquipmentType;
import stationtocommand.model.equipmentStructure.FireEquipmentType;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.missionStructure.MissionType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.FireVehicleType;
import stationtocommand.model.vehicleStructure.MedicVehicleType;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.VehicleType;
import stationtocommand.view.View;

import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UtilsView {

    public static final float SMALL_ICON_SIZE = 32;
    public static final float MEDIUM_ICON_SIZE = 48;

    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("E dd-MM-yyyy HH:mm");

    public UtilsView() {
    }

    public List<Node> resetAndAddToPane(Pane pane, List<Node> nodes, Node newNode) {
        pane.getChildren().clear();
        pane.getChildren().addAll(nodes);
        pane.getChildren().add(newNode);
        List<Node> newNodeList = new ArrayList<>(nodes);
        newNodeList.add(newNode);
        return newNodeList;
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    public void clearPane(Pane pane) {
        pane.getChildren().clear();
    }

    public void addToSidebar(Pane pane, Button button, String text1, String text2) {
        button.setText(text1);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMinWidth(200);
        Label label = new Label(text2);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(button, label);
        pane.getChildren().addAll(hBox);
    }

    public void addMainTitleLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
        -fx-text-fill: white;
        -fx-font-size: 18px;
        -fx-font-weight: bold;
        -fx-padding: 10px 15px;
        -fx-background-color: rgba(50, 50, 50, 0.9);
        -fx-background-radius: 6px;
        -fx-border-color: #777;
        -fx-border-width: 1px;
        -fx-border-radius: 6px;
        -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.3, 0, 0);
    """);
        pane.getChildren().add(label);
    }

    public void addSectionTitleLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-font-weight: bold;
        -fx-padding: 7px 12px;
        -fx-background-color: rgba(60, 60, 60, 0.9);
        -fx-background-radius: 6px;
        -fx-border-color: #777;
        -fx-border-width: 1px;
        -fx-border-radius: 6px;
        -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.3, 0, 0);
    """);
        pane.getChildren().add(label);
    }

    public void addBodyLabel(Pane pane, String text) {
        Label label = new Label(text);
        label.setStyle("""
        -fx-text-fill: white;
        -fx-font-size: 13px;
        -fx-padding: 5px 10px;
        -fx-background-color: rgba(60, 60, 60, 0.9);
        -fx-background-radius: 4px;
    """);
        pane.getChildren().add(label);
    }

    public void resetBreadCrumbBar(BreadCrumbBar<Object> breadCrumbBar) {
        breadCrumbBar.setSelectedCrumb(null);
    }

    public void addBreadCrumb(BreadCrumbBar<Object> breadCrumbBar, Object object) {
        TreeItem<Object> treeItem = new TreeItem<>(object);
        if (breadCrumbBar.getSelectedCrumb() == null) {
            breadCrumbBar.setSelectedCrumb(treeItem);
        }
        else if (breadCrumbBar.getSelectedCrumb().getValue() != object) {
            breadCrumbBar.getSelectedCrumb().getChildren().add(treeItem);
            breadCrumbBar.setSelectedCrumb(treeItem);
        }
    }

    public void addNodeToPane(Pane pane, Node node, Point2D point) {
        node.setLayoutX(point.getX());
        node.setLayoutY(point.getY());
        addNodeToPane(pane, node);
    }

    public void addNodeToPane(Pane pane, Node node) {
        pane.getChildren().addAll(node);
    }

    double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public Point2D locationToPoint(Location location, IconType iconType) {
        double iconSize = 0;
        switch (iconType) {
            case IconType.SMALL, IconType.SMALL_SHADOW -> iconSize = SMALL_ICON_SIZE;
            case IconType.MEDIUM, IconType.MEDIUM_SHADOW -> iconSize = MEDIUM_ICON_SIZE;
        }
        double x = normalize(location.longitude(), LocationManager.MIN_LONGITUDE, LocationManager.MAX_LONGITUDE) * View.MAP_WIDTH;
        double y = (1 - normalize(location.latitude(), LocationManager.MIN_LATITUDE, LocationManager.MAX_LATITUDE)) * View.MAP_HEIGHT;
        return new Point2D(x - (iconSize/2.0) , y  - (iconSize/2.0));
    }

    public ImageView basicIcon(String iconPath) {
        return new ImageView(new Image(Objects.requireNonNull(getClass().getResource(iconPath)).toExternalForm()));
    }

    public ImageView mediumIcon(String iconPath) {
        ImageView imageView = basicIcon(iconPath);
        imageView.setFitWidth(MEDIUM_ICON_SIZE);
        imageView.setFitHeight(MEDIUM_ICON_SIZE);
        return imageView;
    }

    public ImageView mediumShadowIcon(String iconPath, IconColor iconColor) {
        ImageView imageView = mediumIcon(iconPath);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, " + iconColor + ", 20, 0.3, 0, 0);");
        return imageView;
    }

    public ImageView smallIcon(String iconPath) {
        ImageView imageView = basicIcon(iconPath);
        imageView.setFitWidth(SMALL_ICON_SIZE);
        imageView.setFitHeight(SMALL_ICON_SIZE);
        return imageView;
    }

    public ImageView smallShadowIcon(String iconPath, IconColor iconColor) {
        ImageView imageView = smallIcon(iconPath);
        imageView.setStyle("-fx-effect: dropshadow(gaussian, " + iconColor + ", 20, 0.3, 0, 0);");
        return imageView;
    }

    public FadeTransition iconTransition(ImageView stationIcon) {
        FadeTransition flash = new FadeTransition(Duration.seconds(0.5), stationIcon);
        flash.setFromValue(1.0);
        flash.setToValue(0.5);
        flash.setCycleCount(Animation.INDEFINITE);
        flash.setAutoReverse(true);

        return flash;
    }

    public String vehicleIconPath(VehicleType vehicleType) {
        String iconPath;
        switch (vehicleType) {
            case FireVehicleType.PUMPER -> iconPath = "/images/vehicle/pumperFireTruck.png";
            case FireVehicleType.TANKER -> iconPath = "/images/vehicle/tankerFireTruck.png";
            case FireVehicleType.LADDER -> iconPath = "/images/vehicle/aerialLadderTruck.png";
            case FireVehicleType.TOWER -> iconPath = "/images/vehicle/towerLadderTruck.png";
            case FireVehicleType.RESCUE -> iconPath = "/images/vehicle/rescueTruck.png";
            case FireVehicleType.HEAVY_RESCUE -> iconPath = "/images/vehicle/heavyRescueTruck.png";
            case PoliceVehicleType.SUV -> iconPath = "/images/vehicle/suv.png";
            case PoliceVehicleType.SEDAN -> iconPath = "/images/vehicle/sedan.png";
            case PoliceVehicleType.MOTORCYCLE -> iconPath = "/images/vehicle/motorcycle.png";
            case MedicVehicleType.BLS_AMBULANCE -> iconPath = "/images/vehicle/blsAmbulance.png";
            case MedicVehicleType.ALS_AMBULANCE -> iconPath = "/images/vehicle/alsAmbulance.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String missionIconPath(MissionType missionType) {
        String iconPath;
        switch (missionType) {
            case STRUCTURE_FIRE -> iconPath = "/images/mission/structureFire.png";
            // TODO: replace with final icons
            case VEHICLE_FIRE -> iconPath = "/images/fireMission.png";
            case WATER_RESCUE, COLLAPSE_RESCUE -> iconPath = "/images/rescueMission.png";
            case TRAFFIC_INCIDENT -> iconPath = "/images/rescueMission.png";
            case BURGLARY, ASSAULT, DISTURBANCE -> iconPath = "/images/policeMission.png";
            case HOMICIDE, DRUG_CRIME, VICE_CRIME -> iconPath = "/images/policeMission.png";
            case CROWD_CONTROL -> iconPath = "/images/policeMission.png";
            case MEDICAL_EMERGENCY, TRAUMA_RESPONSE -> iconPath = "/images/medicMission.png";
            case CARDIAC_ARREST, POISONING_OVERDOSE -> iconPath = "/images/medicMission.png";

            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String equipmentIconPath(EquipmentType equipmentType) {
        String iconPath;
        switch (equipmentType) {
            case FireEquipmentType.HOSE -> iconPath = "/images/equipment/fireHose.png";
            case FireEquipmentType.NOZZLE -> iconPath = "/images/equipment/fogNozzle.png";
            case FireEquipmentType.AXE -> iconPath = "/images/equipment/fireAxe.png";
            default -> iconPath = "/images/blank.png";
        }
        System.out.println(equipmentType + "..." + iconPath);
        return iconPath;
    }

    public String responderIconPath(Responder responder) {
        String iconPath;
        int hash = Math.abs((responder.getFirstName() + responder.getLastName()).hashCode() % 10) + 1;

        switch (responder.getGender()) {
            case MALE -> {
                switch (hash) {
                    case 1 -> iconPath = "/images/responder/male01.png";
                    case 2 -> iconPath = "/images/responder/male02.png";
                    case 3 -> iconPath = "/images/responder/male03.png";
                    case 4 -> iconPath = "/images/responder/male04.png";
                    case 5 -> iconPath = "/images/responder/male05.png";
                    case 6 -> iconPath = "/images/responder/male06.png";
                    case 7 -> iconPath = "/images/responder/male07.png";
                    case 8 -> iconPath = "/images/responder/male08.png";
                    case 9 -> iconPath = "/images/responder/male09.png";
                    case 10 -> iconPath = "/images/responder/male10.png";
                    default -> iconPath = "/images/blank.png";
                }
            }
            case FEMALE -> {
                switch (hash) {
                    case 1 -> iconPath = "/images/responder/female01.png";
                    case 2 -> iconPath = "/images/responder/female02.png";
                    case 3 -> iconPath = "/images/responder/female03.png";
                    case 4 -> iconPath = "/images/responder/female04.png";
                    case 5 -> iconPath = "/images/responder/female05.png";
                    case 6 -> iconPath = "/images/responder/female06.png";
                    case 7 -> iconPath = "/images/responder/female07.png";
                    case 8 -> iconPath = "/images/responder/female08.png";
                    case 9 -> iconPath = "/images/responder/female09.png";
                    case 10 -> iconPath = "/images/responder/female10.png";
                    default -> iconPath = "/images/blank.png";
                }
            }
            default -> iconPath = "/images/blank.png";
        }

        return iconPath;
    }

    public String unitIconPath(UnitType unitType) {
        String iconPath;
        switch (unitType) {
            case FireUnitType.FIRE_ENGINE -> iconPath = "/images/unit/fireEngine.png";
            case FireUnitType.FIRE_TRUCK -> iconPath = "/images/unit/fireTruck.png";
            case FireUnitType.RESCUE_SQUAD -> iconPath = "/images/unit/rescueSquad.png";
            case PoliceUnitType.PATROL_UNIT -> iconPath = "/images/unit/patrolUnit.png";
            case PoliceUnitType.DETECTIVE_UNIT -> iconPath = "/images/unit/detectiveUnit.png";
            case PoliceUnitType.HOMICIDE_UNIT -> iconPath = "/images/unit/homicideUnit.png";
            case PoliceUnitType.NARCOTICS_UNIT -> iconPath = "/images/unit/narcoticsUnit.png";
            case PoliceUnitType.VICE_UNIT -> iconPath = "/images/unit/viceUnit.png";
            case MedicUnitType.PRIMARY_CARE_UNIT -> iconPath = "/images/unit/primaryCare.png";
            case MedicUnitType.CRITICAL_CARE_UNIT -> iconPath = "/images/unit/criticalCare.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String unitStatusIconPath(UnitStatus unitStatus) {
        String iconPath;
        switch (unitStatus) {
            case UnitStatus.AVAILABLE -> iconPath = "/images/status/availableStatus.png";
            case UnitStatus.DISPATCHED -> iconPath = "/images/status/dispatchedStatus.png";
            case UnitStatus.ON_SCENE -> iconPath = "/images/status/onSceneStatus.png";
            case UnitStatus.RETURNING -> iconPath = "/images/status/returningStatus.png";
            case UnitStatus.UNAVAILABLE -> iconPath = "/images/status/unavailableStatus.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String stationIconPath(DepartmentType departmentType) {
        String iconPath;
        switch (departmentType) {
            case FIRE_DEPARTMENT -> iconPath = "/images/station/fireStation.png";
            case POLICE_DEPARTMENT -> iconPath = "/images/station/policeStation.png";
            case MEDIC_DEPARTMENT -> iconPath = "/images/station/medicStation.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public String departmentIconPath(DepartmentType departmentType) {
        String iconPath;
        switch (departmentType) {
            case FIRE_DEPARTMENT -> iconPath = "/images/department/fireDepartment.png";
            case POLICE_DEPARTMENT -> iconPath = "/images/department/policeDepartment.png";
            case MEDIC_DEPARTMENT -> iconPath = "/images/department/medicDepartment.png";
            default -> iconPath = "/images/blank.png";
        }
        return iconPath;
    }

    public IconColor departmentIconColor(Department department) {
        IconColor iconColor;
        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> iconColor = IconColor.PERSIAN_RED;
            case POLICE_DEPARTMENT -> iconColor = IconColor.DARK_BLUE;
            case MEDIC_DEPARTMENT -> iconColor = IconColor.CRIMSON_RED;
            default -> iconColor = IconColor.WHITE;
        }
        return iconColor;
    }

    public HBox createHBox(Pane pane) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);
        return hBox;
    }

    public ImageView addIconToPane(Pane pane, IconType iconType, IconColor iconColor, String imagePath) {
        ImageView imageView;

        switch (iconType) {
            case IconType.SMALL -> imageView = smallIcon(imagePath);
            case IconType.SMALL_SHADOW -> imageView = smallShadowIcon(imagePath, iconColor);
            case IconType.MEDIUM -> imageView = mediumIcon(imagePath);
            case IconType.MEDIUM_SHADOW -> imageView = mediumShadowIcon(imagePath, iconColor);
            default -> imageView = smallIcon(imagePath);
        }
        pane.getChildren().add(imageView);
        return imageView;
    }

    public void addButtonToPane(Pane pane, String string, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(string);
        button.setOnAction(eventHandler);
        pane.getChildren().add(button);
    }

}