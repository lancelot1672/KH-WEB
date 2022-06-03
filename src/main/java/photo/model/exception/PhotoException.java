package photo.model.exception;

public class PhotoException extends RuntimeException {
    public PhotoException(String s) {
    }

    public PhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhotoException(Throwable cause) {
        super(cause);
    }

    public PhotoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
