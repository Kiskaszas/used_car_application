package com.test.used_car_application.Exception;

public class UsedCarAppException extends RuntimeException {

    public UsedCarAppException() {
        super();
    }

    public UsedCarAppException(String message) {
        super(message);
    }

    public UsedCarAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
