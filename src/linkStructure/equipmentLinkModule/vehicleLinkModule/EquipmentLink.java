package linkStructure.equipmentLinkModule.vehicleLinkModule;

import equipmentStructure.equipmentModule.Equipment;

public abstract class EquipmentLink {

    private final Equipment equipment;

    public EquipmentLink(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }

}