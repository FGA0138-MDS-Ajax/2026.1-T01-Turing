package br.com.seuespacounb.turing.exception;

public class HttpMessageNotReadableException extends RuntimeException {
    public HttpMessageNotReadableException(String message) {
        super(message);
    }
}
