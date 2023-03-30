package kt.reactive.mywebflux.exception;

import org.springframework.http.HttpStatus;

public class CustomAPIException extends Exception{
    private HttpStatus httpStatus;

    public CustomAPIException(String message) {
        this(message, HttpStatus.EXPECTATION_FAILED); //417
    }

    public CustomAPIException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {

        return httpStatus;
    }
}