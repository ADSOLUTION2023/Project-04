package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * FacultyBean represents faculty information within the system.
 * It includes personal details such as name, DOB, gender, and contact details,
 * as well as academic associations like college, course, and subject.
 * This class extends {@link BaseBean} to inherit common audit attributes.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class FacultyBean extends BaseBean {

    /** First name of the faculty member. */
    private String firstName;

    /** Last name of the faculty member. */
    private String lastName;

    /** Date of birth of the faculty member. */
    private Date dob;

    /** Gender of the faculty member. */
    private String gender;

    /** Mobile number of the faculty member. */
    private String mobileNo;

    /** Email address of the faculty member. */
    private String email;

    /** ID of the associated college. */
    private long collegeId;

    /** Name of the associated college. */
    private String collegeName;

    /** ID of the associated course. */
    private long courseId;

    /** Name of the associated course. */
    private String courseName;

    /** ID of the associated subject. */
    private long subjectId;

    /** Name of the associated subject. */
    private String subjectName;

    /**
     * Gets the first name of the faculty member.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the faculty member.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the faculty member.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the faculty member.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the date of birth of the faculty member.
     *
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the faculty member.
     *
     * @param dob the date of birth
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * Gets the gender of the faculty member.
     *
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the faculty member.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the mobile number of the faculty.
     *
     * @return mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * Sets the mobile number of the faculty.
     *
     * @param mobileNo the mobile number
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * Gets the email address of the faculty member.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the faculty member.
     *
     * @param email the email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the associated college ID.
     *
     * @return collegeId
     */
    public long getCollegeId() {
        return collegeId;
    }

    /**
     * Sets the associated college ID.
     *
     * @param collegeId the college ID
     */
    public void setCollegeId(long collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * Gets the associated college name.
     *
     * @return collegeName
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * Sets the associated college name.
     *
     * @param collegeName the college name
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * Gets the associated course ID.
     *
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the associated course ID.
     *
     * @param courseId the course ID
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the associated course name.
     *
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the associated course name.
     *
     * @param courseName the course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the associated subject ID.
     *
     * @return subjectId
     */
    public long getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the associated subject ID.
     *
     * @param subjectId the subject ID
     */
    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Gets the associated subject name.
     *
     * @return subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Sets the associated subject name.
     *
     * @param subjectName the subject name
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Returns the unique key (ID) as a string.
     *
     * @return the key
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the faculty, typically the full name.
     *
     * @return firstName + " " + lastName
     */
    @Override
    public String getValue() {
        return firstName + " " + lastName;
    }
}
