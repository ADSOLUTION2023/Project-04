package in.co.rays.proj4.util;

import java.util.ResourceBundle;

/**
 * <p>
 * PropertyReader is a utility class used to read values from the system
 * resource bundle file (<b>system.properties</b>). It is mainly used for
 * retrieving error messages, labels, and configurable application properties.
 * </p>
 *
 * <p>
 * This class supports:
 * </p>
 * <ul>
 *     <li>Fetching a value by key</li>
 *     <li>Fetching a value and replacing a single parameter (e.g. {0})</li>
 *     <li>Fetching a value and replacing multiple parameters ({0}, {1}, ...)</li>
 * </ul>
 *
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * String msg = PropertyReader.getValue("error.require", "Email");
 * // Output: "Email is required"
 * </pre>
 *
 * <p>
 * The resource bundle location:
 * <b>in.co.rays.proj4.bundle.system.properties</b>
 * </p>
 *
 * @author
 * @version 1.0
 */
public class PropertyReader {

    /** Loads the system.properties file */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Returns the value of a given key from the properties file.
     *
     * @param key the property key
     * @return the value associated with the key, or the key itself if not found
     */
    public static String getValue(String key) {

        String val = null;

        try {
            val = rb.getString(key); // Example: "{0} is required"
        } catch (Exception e) {
            val = key; // fallback to key if missing
        }
        return val;
    }

    /**
     * Returns the value of the given key and replaces the first placeholder {0}
     * with the given parameter.
     *
     * Example:
     * <pre>
     * getValue("error.require", "Name");
     * Output: "Name is required"
     * </pre>
     *
     * @param key the message key
     * @param param the value to replace {0}
     * @return formatted message
     */
    public static String getValue(String key, String param) {
        String msg = getValue(key); // e.g. "{0} is required"
        msg = msg.replace("{0}", param);
        return msg;
    }

    /**
     * Returns the value of the given key and replaces multiple placeholders like
     * {0}, {1}, {2}, ... with values from the provided params array.
     *
     * Example:
     * <pre>
     * params = { "Roll No", "Student Name" }
     * getValue("error.multipleFields", params);
     * Output: "Roll No and Student Name are required"
     * </pre>
     *
     * @param key the message key
     * @param params array of replacement values
     * @return formatted message
     */
    public static String getValue(String key, String[] params) {
        String msg = getValue(key); // e.g. "{0} and {1} are required"
        for (int i = 0; i < params.length; i++) {
            msg = msg.replace("{" + i + "}", params[i]);
        }
        return msg;
    }

    /**
     * Test method used for verifying key and value replacements.
     */
    public static void main(String[] args) {

        System.out.println("Single key example:");
        System.out.println(PropertyReader.getValue("error.require"));

        System.out.println("\nSingle parameter replacement example:");
        System.out.println(PropertyReader.getValue("error.require", "loginId"));

        System.out.println("\nMultiple parameter replacement example:");
        String[] params = { "Roll No", "Student Name" };
        System.out.println(PropertyReader.getValue("error.multipleFields", params));
    }
}
