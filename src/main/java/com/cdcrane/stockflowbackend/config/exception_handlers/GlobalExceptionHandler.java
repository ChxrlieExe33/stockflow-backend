package com.cdcrane.stockflowbackend.config.exception_handlers;

import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.config.responses.ExceptionErrorResponse;
import com.cdcrane.stockflowbackend.product_instances.exceptions.ItemAlreadyReservedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * This method catches any exceptions not explicitly caught by any other methods in this handler.
     * @param ex The exception thrown.
     * @return Returns a response entity to the user with error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionErrorResponse> handleException(Exception ex) {

        ExceptionErrorResponse error = ExceptionErrorResponse.builder()
                .message("Unknown error occurred. Please contact the developer for assistance.")
                .responseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(System.currentTimeMillis())
                .build();

        log.error("Generic error triggered: {}", ex.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }

    /**
     * Handle errors where a domain object was not found.
     * @param ex Exception thrown.
     * @return Response explaining problem.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        ExceptionErrorResponse error = ExceptionErrorResponse.builder()
                .message(ex.getMessage())
                .responseCode(HttpStatus.NOT_FOUND.value())
                .timestamp(System.currentTimeMillis())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ItemAlreadyReservedException.class)
    public ResponseEntity<ExceptionErrorResponse> handleItemAlreadyReservedException(ItemAlreadyReservedException ex) {

        ExceptionErrorResponse error = ExceptionErrorResponse.builder()
                .message(ex.getMessage())
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();

        log.warn("Save new order failed because items were already reserved: {}", ex.getMessage() + ".");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }
}
