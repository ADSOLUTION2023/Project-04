package in.co.rays.proj4.util;

/**
 * EmailMessage is a simple JavaBean used for encapsulating the details
 * of an email message such as recipient address, subject, message body,
 * and message type (HTML or plain text).
 *
 * <p>
 * This class works closely with {@link EmailUtility} which uses an
 * EmailMessage object to send email notifications from the application.
 * </p>
 *
 * <p>
 * Supported message types:
 * </p>
 * <ul>
 *     <li>{@link #HTML_MSG} — HTML formatted email</li>
 *     <li>{@link #TEXT_MSG} — Plain text email</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
public class EmailMessage {

    /** Recipient email address */
    private String to;

    /** Subject of the email */
    private String subject;

    /** Message body content (HTML or text) */
    private String message;

    /**
     * Type of the message:
     * <ul>
     *     <li>HTML_MSG</li>
     *     <li>TEXT_MSG</li>
     * </ul>
     * Default type is TEXT_MSG.
     */
    private int messageType = TEXT_MSG;

    /** HTML email message type constant. */
    public static final int HTML_MSG = 1;

    /** Plain text email message type constant. */
    public static final int TEXT_MSG = 2;

    /**
     * Default constructor.
     * Creates an empty EmailMessage object.
     */
    public EmailMessage() {
    }

    /**
     * Parameterized constructor.
     *
     * @param to      Recipient's email address
     * @param subject Subject of the email
     * @param message Body content of the email
     */
    public EmailMessage(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    /**
     * Sets the recipient email address.
     *
     * @param to Email address of the recipient
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Returns the recipient email address.
     *
     * @return recipient email address
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the subject of the email.
     *
     * @param subject Email subject line
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the email subject.
     *
     * @return subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the email message content.
     *
     * @param message Body text or HTML content of the email
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the email message content.
     *
     * @return email body content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the type of message (HTML or text).
     * Use {@link #HTML_MSG} or {@link #TEXT_MSG}.
     *
     * @param messageType the type of the message
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * Returns the type of message.
     *
     * @return message type constant
     */
    public int getMessageType() {
        return messageType;
    }
}
