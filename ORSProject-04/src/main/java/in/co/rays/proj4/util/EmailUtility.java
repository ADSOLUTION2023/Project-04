package in.co.rays.proj4.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import in.co.rays.proj4.exception.ApplicationException;

/**
 * <p>
 * Utility class used to send email messages using JavaMail API.
 * It reads SMTP configurations from the system.properties file and
 * sends email in TEXT or HTML format using the {@link EmailMessage} class.
 * </p>
 *
 * <p>
 * This class supports:
 * </p>
 * <ul>
 *     <li>SMTP authentication</li>
 *     <li>SSL/TLS configuration</li>
 *     <li>Sending email to one or multiple recipients</li>
 *     <li>Sending HTML or Plain Text messages</li>
 * </ul>
 *
 * <p>
 * Configuration is loaded from:
 * <br>
 * <code>in.co.rays.proj4.bundle.system.properties</code>
 * </p>
 *
 * <p>
 * Expected properties:
 * </p>
 * <pre>
 * smtp.server = smtp.gmail.com
 * smtp.port   = 465
 * email.login = your-email@gmail.com
 * email.pwd   = your-password
 * </pre>
 *
 * @author 
 * @version 1.0
 */
public class EmailUtility {

    /** Load SMTP configuration from properties file */
    static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /** SMTP Host (e.g., smtp.gmail.com) */
    private static final String SMTP_HOST_NAME = rb.getString("smtp.server");

    /** SMTP Port (e.g., 465 or 587) */
    private static final String SMTP_PORT = rb.getString("smtp.port");

    /** Email address used to authenticate with SMTP server */
    private static final String emailFromAddress = rb.getString("email.login");

    /** Password for SMTP authentication */
    private static final String emailPassword = rb.getString("email.pwd");

    /** Stores JavaMail SMTP properties */
    private static Properties props = new Properties();

    // Static initialization block for SMTP configuration
    static {
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    /**
     * Sends an email using the values provided in the {@link EmailMessage} object.
     *
     * @param emailMessageDTO object containing email details such as
     *                        To, Subject, Message, and Message Type
     * @throws ApplicationException if any SMTP or configuration error occurs
     */
    public static void sendMail(EmailMessage emailMessageDTO) throws ApplicationException {
        try {
            // Create authenticated mail session
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailFromAddress, emailPassword);
                }
            });

            // Build email message
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailFromAddress));
            msg.setRecipients(Message.RecipientType.TO, getInternetAddresses(emailMessageDTO.getTo()));
            msg.setSubject(emailMessageDTO.getSubject());

            // Determine message type: HTML or Plain Text
            String contentType = emailMessageDTO.getMessageType() == EmailMessage.HTML_MSG
                    ? "text/html"
                    : "text/plain";

            msg.setContent(emailMessageDTO.getMessage(), contentType);

            // Send email
            Transport.send(msg);

        } catch (Exception ex) {
            throw new ApplicationException("Email Error: " + ex.getMessage());
        }
    }

    /**
     * Converts a comma-separated list of email IDs into an array of InternetAddress.
     *
     * @param emails comma-separated email IDs
     * @return array of InternetAddress objects
     * @throws Exception if parsing fails
     */
    private static InternetAddress[] getInternetAddresses(String emails) throws Exception {
        if (emails == null || emails.isEmpty()) {
            return new InternetAddress[0];
        }

        String[] emailArray = emails.split(",");
        InternetAddress[] addresses = new InternetAddress[emailArray.length];

        for (int i = 0; i < emailArray.length; i++) {
            addresses[i] = new InternetAddress(emailArray[i].trim());
        }

        return addresses;
    }
}
