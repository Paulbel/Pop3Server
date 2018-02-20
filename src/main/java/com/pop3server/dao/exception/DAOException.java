package com.pop3server.dao.exception;

public class DAOException extends Exception {
    private static final long serialVersionUID = -6456501015920578270L;

    public DAOException() {
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
