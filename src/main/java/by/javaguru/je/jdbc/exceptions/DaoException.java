package by.javaguru.je.jdbc.exceptions;

import java.sql.SQLException;

public class DaoException extends RuntimeException {
    public DaoException(Throwable e) {
        super(e + "DAO ERROR");
    }
}
