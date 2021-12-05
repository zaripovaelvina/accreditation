package project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMediaException extends RuntimeException {
    public UnsupportedMediaException() {
    }

    public UnsupportedMediaException(String message) {
        super(message);
    }

    public UnsupportedMediaException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMediaException(Throwable cause) {
        super(cause);
    }

    public UnsupportedMediaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
