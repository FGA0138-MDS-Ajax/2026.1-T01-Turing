package br.com.seuespacounb.turing.exception;

public class MethodArgumentTypeMismatchException extends RuntimeException {
    public MethodArgumentTypeMismatchException(String message) {
        super(message);
    }
}
