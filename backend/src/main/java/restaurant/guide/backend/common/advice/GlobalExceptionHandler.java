package restaurant.guide.backend.common.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import restaurant.guide.backend.restaurant.exceptions.RestaurantNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<?> handleRestaurantNotFound(RestaurantNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of(
            "error", "BAD_REQUEST",
            "message", ex.getMessage()
        ));
    }
}
