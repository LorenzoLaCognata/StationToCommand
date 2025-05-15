package stationtocommand.model.utilsStructure;

import javafx.scene.image.Image;

public interface EnumWithResource {

    Image getImage();
    EnumWithResource[] getValues();
    EnumWithResource getPrimaryValue();
}