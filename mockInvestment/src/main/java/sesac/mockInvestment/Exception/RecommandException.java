package sesac.mockInvestment.Exception;

public class RecommandException extends RuntimeException{
    public RecommandException() {
        super();
    }

    public RecommandException(String message) {
        super(message);
    }

    public RecommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecommandException(Throwable cause) {
        super(cause);
    }

    protected RecommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
