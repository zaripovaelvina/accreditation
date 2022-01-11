package project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberCountMoreThanQuotaException extends RuntimeException {
    public MemberCountMoreThanQuotaException() {
    }

    public MemberCountMoreThanQuotaException(String message) {
        super(message);
    }

    public MemberCountMoreThanQuotaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberCountMoreThanQuotaException(Throwable cause) {
        super(cause);
    }

    public MemberCountMoreThanQuotaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
