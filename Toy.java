public class Toy {

    private int id; // Идентификатор игрушки.
    private String name; // Наименование игрушки.
    private double frequencyOfDropout; // Частота выпадения игрушки.

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getFrequencyOfDropout() {
        return frequencyOfDropout;
    }
    public void setFrequencyOfDropout(double frequencyOfDropout) {
        this.frequencyOfDropout = frequencyOfDropout;
    }

    public Toy(int id, String name, double frequencyOfDropout) {
        this.id = id;
        this.name = name;
        this.frequencyOfDropout = frequencyOfDropout;
    }

    @Override
    public String toString() {
        return String.format("номер: %d, название: %s, частота выпадения : %f", this.id, this.name, this.frequencyOfDropout);
    }

}
