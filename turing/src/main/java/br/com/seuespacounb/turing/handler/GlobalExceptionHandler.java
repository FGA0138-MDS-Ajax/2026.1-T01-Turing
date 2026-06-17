package br.com.seuespacounb.turing.handler;

import br.com.seuespacounb.turing.exception.*;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorResponse>  handlerNotFoundException(NotFoundException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ErrorResponse> handlerConflictException(ConflictException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        ErrorResponse response = ErrorResponse.builder()
                .message(mensagem)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handlerUnauthorizedException(UnauthorizedException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ErrorResponse> handlerForbiddenException(ForbiddenException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler({MethodNotAllowedException.class})
    public ResponseEntity<ErrorResponse> handlerMethodNotAllowedException(MethodNotAllowedException exception){
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponse> handlerDataIntegrityViolation(DataIntegrityViolationException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handlerBadRequest(BadRequestException exception) {
        ErrorResponse response = ErrorResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}




