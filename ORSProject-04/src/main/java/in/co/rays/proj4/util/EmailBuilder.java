package in.co.rays.proj4.util;

import java.util.HashMap;

/**
 * EmailBuilder is a utility class responsible for generating HTML-based email
 * message bodies for different user-related actions such as registration,
 * password recovery, and password updates.
 *
 * <p>
 * All methods return a fully formatted HTML String that can be sent using the
 * EmailUtility class.
 * </p>
 *
 * <p>
 * This class uses a {@link HashMap} of String key-value pairs to dynamically
 * insert user-specific data such as login ID, password, first name, and last
 * name.
 * </p>
 *
 * <p>
 * Supported email templates:
 * </p>
 * <ul>
 *     <li>User Registration Confirmation</li>
 *     <li>Forget Password Email</li>
 *     <li>Password Change Notification</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
public class EmailBuilder {

    /**
     * Builds the HTML email content for user registration confirmation.
     *
     * <p>This message includes:</p>
     * <ul>
     *     <li>Login ID</li>
     *     <li>Password</li>
     *     <li>Support contact details</li>
     * </ul>
     *
     * @param map A map containing user attributes:
     *            <ul>
     *                <li>login — email/username</li>
     *                <li>password — user password</li>
     *            </ul>
     * @return HTML formatted registration success message
     */
    public static String getUserRegistrationMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Welcome to ORS, ").append(map.get("login")).append("!</H1>");
        msg.append("<P>Your registration is successful. You can now log in and manage your account.</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login"))
           .append("<BR>Password: ").append(map.get("password")).append("</B></P>");
        msg.append("<P>Change your password after logging in for security reasons.</P>");
        msg.append("<P>For support, contact +91 98273 60504 or hrd@sunrays.co.in.</P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

    /**
     * Builds the HTML email content for password recovery (Forget Password).
     *
     * <p>This message includes:</p>
     * <ul>
     *     <li>First Name</li>
     *     <li>Last Name</li>
     *     <li>Login ID</li>
     *     <li>Password</li>
     * </ul>
     *
     * @param map A map containing user attributes:
     *            <ul>
     *                <li>firstName</li>
     *                <li>lastName</li>
     *                <li>login — email/username</li>
     *                <li>password — current password</li>
     *            </ul>
     * @return HTML formatted forget password email
     */
    public static String getForgetPasswordMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Password Recovery</H1>");
        msg.append("<P>Hello, ").append(map.get("firstName")).append(" ").append(map.get("lastName")).append(".</P>");
        msg.append("<P>Your login details are:</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login"))
           .append("<BR>Password: ").append(map.get("password")).append("</B></P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }

    /**
     * Builds the HTML email content for password update notification.
     *
     * <p>This message includes:</p>
     * <ul>
     *     <li>First Name</li>
     *     <li>Last Name</li>
     *     <li>Login ID</li>
     *     <li>New Password</li>
     * </ul>
     *
     * @param map A map containing user attributes:
     *            <ul>
     *                <li>firstName</li>
     *                <li>lastName</li>
     *                <li>login</li>
     *                <li>password — updated password</li>
     *            </ul>
     * @return HTML formatted change password notification email
     */
    public static String getChangePasswordMessage(HashMap<String, String> map) {
        StringBuilder msg = new StringBuilder();
        msg.append("<HTML><BODY>");
        msg.append("<H1>Password Changed Successfully</H1>");
        msg.append("<P>Dear ").append(map.get("firstName")).append(" ").append(map.get("lastName"))
           .append(", your password has been updated.</P>");
        msg.append("<P>Your updated login details are:</P>");
        msg.append("<P><B>Login Id: ").append(map.get("login"))
           .append("<BR>New Password: ").append(map.get("password")).append("</B></P>");
        msg.append("</BODY></HTML>");
        return msg.toString();
    }
}
