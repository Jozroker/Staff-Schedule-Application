package server.error;

public class NoneRecordsBeFoundException extends RuntimeException {

    public NoneRecordsBeFoundException() {
        super("Жодного запису не знайдено");
    }
}
