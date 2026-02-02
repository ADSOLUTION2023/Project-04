package in.co.rays.proj4.bean;

/**
 * RoleBean represents different user roles within the application such as
 * Admin, Student, Faculty, and Kiosk. Each role determines the set of
 * permissions and actions available to the user. This class extends 
 * {@link BaseBean} to include common audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class RoleBean extends BaseBean {

    /** Constant representing Admin role. */
    public static final int ADMIN = 1;

    /** Constant representing Student role. */
    public static final int STUDENT = 2;

    /** Constant representing Faculty role. */
    public static final int FACULTY = 3;

    /** Constant representing Kiosk role. */
    public static final int KIOSK = 4;

    /** Name of the role (e.g., "Admin", "Student"). */
    private String name;

    /** Description of the role and its purpose. */
    private String description;

    /**
     * Gets the name of the role.
     *
     * @return role name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     *
     * @param name the role name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the role.
     *
     * @return role description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the role.
     *
     * @param description the role description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the unique key (ID) as a string.
     *
     * @return key representing the role ID
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the role, typically its name.
     *
     * @return role name
     */
    @Override
    public String getValue() {
        return name;
    }
}
