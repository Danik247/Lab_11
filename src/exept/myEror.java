package exept;

public class myEror extends IllegalArgumentException {
    private final String customMessage;

    public myEror(String message) {
        super(message);
        this.customMessage = message;
    }

    @Override
    public String getMessage() {
        return "Ошибкочка: " + customMessage;
    }
}
