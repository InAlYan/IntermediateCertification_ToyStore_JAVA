
import java.io.FileWriter;
import java.io.IOException;

public class ToyStoreApp {

    public static void main(String[] args) {

        // Создаем магазин игрушек
        ToyStore ts = new ToyStore();

        // Заполняем магазин игрушек случайными игрушками
        for (int i = 0; i < 20; i++) {
            try {
                ts.appendToy(FabricOfToys.generateToy());
            } catch (IncorrectMatchFrequencyOfDropoutException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(ts);
        System.out.println("----------РОЗЫГРЫШ!!!----------");

        // Проводим розыгрыш игрушек и пишем их в файл
        Toy currentToy;
        try (FileWriter fw = new FileWriter("winning_toys.txt");) {
            for (int i = 0; i < 21; i++) {
                try {
                    currentToy = ts.getNextToyByFrequencyOfDropout();
                    System.out.printf("Выпала игрушка: %s%n", currentToy);
                    fw.write(currentToy + "\n");
                } catch (EmptyToyStoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n----------Состояние магазина игрушек после розыгрыша----------");
        System.out.println(ts);

    }

}
