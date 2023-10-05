package sesac.mockInvestment.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RecommandException.class)
    public ErrorResult recommandException(RecommandException e) {
        log.error("[recommandException] ex", e);
        return new ErrorResult("LoginError", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotLoginException.class)
    public ErrorResult notLoginException(NotLoginException e) {
        log.error("[notLoginException] ex", e);
        return new ErrorResult("RecommandError", e.getMessage());
    }
}
