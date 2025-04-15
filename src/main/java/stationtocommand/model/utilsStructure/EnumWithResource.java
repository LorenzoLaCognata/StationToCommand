package stationtocommand.model.utilsStructure;

public interface EnumWithResource {

    String getResourcePath();
    EnumWithResource[] getValues();
    EnumWithResource getPrimaryValue();

}