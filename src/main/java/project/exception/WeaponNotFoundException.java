package project.exception;

public class WeaponNotFoundException extends RuntimeException {
    public WeaponNotFoundException() {
    }

    public WeaponNotFoundException(String message) {
        super(message);
    }

    public WeaponNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WeaponNotFoundException(Throwable cause) {
        super(cause);
    }

    public WeaponNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
