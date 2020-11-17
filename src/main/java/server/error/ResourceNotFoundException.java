package server.error;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Запис не знайдено");
    }
}
