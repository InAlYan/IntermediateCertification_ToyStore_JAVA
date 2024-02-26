
import java.util.Random;

public class FabricOfToys {

    private static int totalId = 1; // генерируемый по порядку id игрушки
    private static int upperBoundOfFrequencyOfDropout = 10; // верхняя граница случайного шанса выпадения
    private static int lowerBoundOfFrequencyOfDropout = 1; // нижняя граница случайного шанса выпадения

    public static int getTotalId() {
        return totalId;
    }
    public static void setTotalId(int totalId) {
        FabricOfToys.totalId = totalId;
    }
    public static int getUpperBoundOfFrequencyOfDropout() {
        return upperBoundOfFrequencyOfDropout;
    }
    public static void setUpperBoundOfFrequencyOfDropout(int upperBoundOfFrequencyOfDropout) {
        FabricOfToys.upperBoundOfFrequencyOfDropout = upperBoundOfFrequencyOfDropout;
    }
    public static int getLowerBoundOfFrequencyOfDropout() {
        return lowerBoundOfFrequencyOfDropout;
    }
    public static void setLowerBoundOfFrequencyOfDropout(int lowerBoundOfFrequencyOfDropout) {
        FabricOfToys.lowerBoundOfFrequencyOfDropout = lowerBoundOfFrequencyOfDropout;
    }

    /**
     * Создаем игрушку вручную
     * @param id - int идентификатор игрушки
     * @param name - String наименование игрущки
     * @param FrequencyOfDropout - double частота выпадения игрушки
     * @return Toy - экземпляр класса игрушки
     */
    public static Toy createToy(int id, String name, double FrequencyOfDropout) {
        return new Toy(id, name, FrequencyOfDropout);
    }

    /**
     * Создаем игрушку вручную
     * @param name - String наименование игрущки
     * @param FrequencyOfDropout - double частота выпадения игрушки
     * @return Toy - экземпляр класса игрушки
     */
    public static Toy createToy(String name, double FrequencyOfDropout) {
        return new Toy(totalId++, name, FrequencyOfDropout);
    }

    /**
     * Создаем игрушку автоматически
     * @return Toy - экземпляр класса игрушки
     */
    public static Toy generateToy() {
        Random rnd = new Random();
        return new Toy(totalId, "игрушка_" + totalId++,
                rnd.nextInt(lowerBoundOfFrequencyOfDropout, upperBoundOfFrequencyOfDropout) * 1.0);
    }
}
