package com.kt.ciclient.exception;

/**
 * Created by Vega Zhou on 2015/10/12.
 */
public class CiApiException extends Exception {

    public CiApiException() {
        super();
    }

    public CiApiException(String message) {
        super(message);
    }

    public CiApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public CiApiException(Throwable cause) {
        super(cause);
    }

    protected CiApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
