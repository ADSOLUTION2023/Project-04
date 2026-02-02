package in.co.rays.proj4.exception;

/**
 * ApplicationException is a custom checked exception used to represent 
 * general application-level errors in the project.
 * 
 * It is typically thrown when:
 *  - Something unexpected happens in the application flow
 *  - A lower-level exception needs to be wrapped and passed upward
 *  - A model/database/service layer encounters an internal failure
 *
 * This helps maintain a clean exception structure by separating 
 * user errors, database errors, and application errors.
 *
 * @author 
 */
public class ApplicationException extends Exception {

    /**
     * Creates an ApplicationException instance with a descriptive message.
     *
     * @param msg the detailed error message
     */
    public ApplicationException(String msg) {
        super(msg);
    }
}
