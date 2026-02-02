package in.co.rays.proj4.bean;

/**
 * CollegeBean represents the College entity containing details such as
 * name, address, state, city, and phone number. It extends {@link BaseBean}
 * to include common audit fields.
 *
 * @author Chaitanya Bhatt
 * @version 1.0
 */
public class CollegeBean extends BaseBean {

    /** Name of the college. */
    private String name;

    /** Address of the college. */
    private String address;

    /** State where the college is located. */
    private String state;

    /** City where the college is located. */
    private String city;

    /** Contact phone number of the college. */
    private String phoneNo;

    /**
     * Gets the name of the college.
     *
     * @return the college name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the college.
     *
     * @param name the college name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the college.
     *
     * @return the college address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the college.
     *
     * @param address the college address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the state of the college.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the college.
     *
     * @param state the state name
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the city of the college.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the college.
     *
     * @param city the city name
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the contact phone number of the college.
     *
     * @return the phone number
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets the contact phone number of the college.
     *
     * @param phoneNo the phone number
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Returns the primary key of the college as a String.
     *
     * @return the key representing the ID
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the college, typically the name.
     *
     * @return the college name
     */
    @Override
    public String getValue() {
        return name;
    }
}
