package in.co.rays.proj4.exception;

/**
 * DatabaseException is a custom checked exception used to indicate 
 * errors that occur while interacting with the database layer.
 *
 * It is thrown when:
 *  - A SQL operation fails
 *  - Database connectivity issues occur
 *  - Constraints or query execution problems arise
 *  - Any unexpected DB-related error happens
 *
 * This exception helps isolate persistence-layer failures from 
 * general application exceptions, making debugging and handling easier.
 * 
 */
public class DatabaseException extends Exception {

    /**
     * Creates a DatabaseException with a detailed error message.
     *
     * @param msg the database-related error message
     */
    public DatabaseException(String msg) {
        super(msg);
    }
}
