package model.utilsStructure.utilsModule;

import java.util.Random;

public class Utils {

    public static final Random randomGenerator = new Random();

    // TODO: add a weighted version where enums have different probability to be chosen
    public static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        int randomIndex = randomGenerator.nextInt(values.length);
        return values[randomIndex];
    }
}
