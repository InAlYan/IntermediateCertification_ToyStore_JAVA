public class IncorrectMatchFrequencyOfDropoutException extends Exception{

    private final double FrequencyOfDropout;
    public double getFrequencyOfDropout() {
        return this.FrequencyOfDropout;

    }
    public IncorrectMatchFrequencyOfDropoutException(String message, double FrequencyOfDropout) {
        super(message);
        this.FrequencyOfDropout = FrequencyOfDropout;
    }

}
