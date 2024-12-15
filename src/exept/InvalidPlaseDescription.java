package exept;

public class InvalidPlaseDescription extends IllegalArgumentException {
    private final String customMessage;

    public InvalidPlaseDescription(String message) {
        super(message);
        this.customMessage = message;
    }

    @Override
    public String getMessage() {
        return "Ошибкочка: " + customMessage;
    }
}
