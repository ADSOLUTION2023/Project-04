package in.co.rays.proj4.exception;

/**
 * DuplicateRecordException is thrown when an attempt is made to add or update
 * a record that already exists in the database.
 *
 * Typical scenarios:
 *  - Trying to register a user with an email/login that already exists
 *  - Adding a role, subject, or course that has the same unique name
 *  - Attempting to insert duplicate roll numbers in marksheets
 *
 * This helps enforce unique constraints at the application level.
 *
 */
public class DuplicateRecordException extends Exception {

    /**
     * Creates a DuplicateRecordException with a specific error message.
     *
     * @param msg the detail message explaining the duplicate condition
     */
    public DuplicateRecordException(String msg) {
        super(msg);
    }
}
