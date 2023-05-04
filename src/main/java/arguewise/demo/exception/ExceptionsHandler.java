package arguewise.demo.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceNotFoundException;

@ControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(InstanceNotFoundException e, HttpServletRequest request){
        ApiError apiError = new ApiError(e.getMessage(), 404);

        return  new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        ApiError apiError = new ApiError(e.getMessage(), 404);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictingRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(ConflictingRequestException e) {
        ApiError apiError = new ApiError(e.getMessage(), 409);

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
