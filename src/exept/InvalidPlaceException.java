package exept;

public class InvalidPlaceException extends IllegalArgumentException {
    private final String customMessage;

    public InvalidPlaceException(String message) {
        super(message);
        this.customMessage = message;
    }

    @Override
    public String getMessage() {
        return "Ошибкочка: " + customMessage;
    }
}
