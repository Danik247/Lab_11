package exept;

public class PlaceEnterException extends Exception {
    private final String customMessage;

    public PlaceEnterException(String message) {
        super(message);
        this.customMessage = message;
    }

    @Override
    public String getMessage() {
        return "Ошибочка: " + customMessage;
    }
}
