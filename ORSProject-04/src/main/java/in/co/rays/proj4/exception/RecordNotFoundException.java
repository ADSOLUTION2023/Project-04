package in.co.rays.proj4.exception;

/**
 * RecordNotFoundException is thrown when a requested record 
 * does not exist in the database.
 *
 * Typical cases:
 *  - User tries to retrieve login credentials but no such email exists
 *  - Searching for a student/marksheet/role using an invalid primary key
 *  - Trying to fetch a timetable, subject, or course that is not present
 *
 * This exception is used to indicate genuine "no data found" conditions.
 */
public class RecordNotFoundException extends Exception {

	 /**
     * Constructs a RecordNotFoundException with a detailed message.
     *
     * @param msg the message explaining which record was not found
     */
	public RecordNotFoundException(String msg) {
		super(msg);
}
}