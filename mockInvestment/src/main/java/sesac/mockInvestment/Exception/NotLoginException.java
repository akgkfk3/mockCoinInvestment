package sesac.mockInvestment.Exception;

public class NotLoginException extends RuntimeException{
    public NotLoginException() {
        super();
    }

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoginException(Throwable cause) {
        super(cause);
    }

    protected NotLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
