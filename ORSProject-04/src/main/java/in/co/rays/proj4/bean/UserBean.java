package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * UserBean represents a user of the application. 
 * It stores user profile information such as name, login credentials, 
 * date of birth, mobile number, gender, and assigned role. 
 * This class extends {@link BaseBean} to include standard audit fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class UserBean extends BaseBean {

    /** First name of the user. */
    private String firstName;

    /** Last name of the user. */
    private String lastName;

    /** Login ID or username of the user. */
    private String login;

    /** Password for the user's account. */
    private String password;

    /** Confirm password field for validation. */
    private String confirmPassword;

    /** Date of birth of the user. */
    private Date dob;

    /** Mobile number of the user. */
    private String mobileNo;

    /** Role ID assigned to the user. */
    private long roleId;

    /** Gender of the user. */
    private String gender;

    /**
     * Gets the first name of the user.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the user's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the user's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the login ID or username of the user.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login ID or username of the user.
     *
     * @param login the user's login name
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets the user's password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the confirm password value.
     *
     * @return confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Sets the confirm password value.
     *
     * @param confirmPassword the confirm password
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Gets the user's date of birth.
     *
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the user's date of birth.
     *
     * @param dob the date of birth
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Gets the mobile number of the user.
     *
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number of the user.
     *
     * @param mobileNo the mobile number
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Gets the role ID assigned to the user.
     *
     * @return roleId
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * Sets the role ID assigned to the user.
     *
     * @param roleId the role ID
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * Gets the gender of the user.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the user.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the unique key for this user (ID as string).
     *
     * @return ID as string
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value for the user, 
     * typically their full name.
     *
     * @return firstName + " " + lastName
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
}
