import java.util.ArrayList;

public class PriorityArrayList implements Comparable<PriorityArrayList> {

    private double frequencyOfDropout; // Частота выпадения, объединяющая игрушки в данном классе.
    private ArrayList<Toy> toys = new ArrayList<>(); // Место хранения игрушек, объединенных частотой выпадения.

    public double getFrequencyOfDropout() {
        return frequencyOfDropout;
    }
    public ArrayList<Toy> getToys() {
        return toys;
    }
    public void setToys(ArrayList<Toy> toys) {
        this.toys = toys;
    }

    public PriorityArrayList(double FrequencyOfDropout) {
        this.frequencyOfDropout = FrequencyOfDropout;
    }

    public void appendToy(Toy toy) throws IncorrectMatchFrequencyOfDropoutException {
        if (toy.getFrequencyOfDropout() == frequencyOfDropout) {
            toys.add(toy);
        } else {
            throw new IncorrectMatchFrequencyOfDropoutException(
                    String.format("Попытка записать в лист с игрушками с одинаковой частотой выпадения %f игрушку %s с другой частотой выпадения %f",
                                   toy.getFrequencyOfDropout(), toy.getName(),frequencyOfDropout), frequencyOfDropout);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.format("Игрушки с одинаковой частотой выпадения: %f:%n", frequencyOfDropout));
        for (Toy el : toys) {
            sb.append(el.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(PriorityArrayList o) {
        if (o.getFrequencyOfDropout() > frequencyOfDropout) {
            return 1;
        }
        if (o.getFrequencyOfDropout() < frequencyOfDropout) {
            return -1;
        }
        return 0;
    }
}
