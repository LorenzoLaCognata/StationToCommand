package stationtocommand.model.utilsStructure;

import java.util.Random;

public class Utils {

    public static final Random randomGenerator = new Random();

    // TODO: add a weighted version where enums have different probability to be chosen
    public static <T extends Enum<?>> T getRandomEnumValue(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        int randomIndex = randomGenerator.nextInt(values.length);
        return values[randomIndex];
    }

    public static int randomIntegerFromRatio(float ratio) {
        int count = 0;

        if (ratio > 1.0f) {
            count = (int) ratio;
        }
        else {
            float randomFloat = Utils.randomGenerator.nextFloat();
            if (randomFloat < ratio) {
                count = 1;
            }
        }
        return count;
    }

}
