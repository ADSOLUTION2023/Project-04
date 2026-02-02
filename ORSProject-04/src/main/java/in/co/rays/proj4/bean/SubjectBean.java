package in.co.rays.proj4.bean;

/**
 * SubjectBean represents the subject details within the system.
 * It contains information such as subject name, associated course ID,
 * course name, and subject description. This class extends 
 * {@link BaseBean} to include standard audit information.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class SubjectBean extends BaseBean {

    /** Name of the subject. */
    private String name;

    /** ID of the course to which this subject belongs. */
    private long courseId;

    /** Name of the course to which this subject belongs. */
    private String courseName;

    /** Description or details about the subject. */
    private String description;

    /**
     * Gets the subject name.
     *
     * @return subject name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the subject name.
     *
     * @param name the subject name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the course ID associated with the subject.
     *
     * @return courseId
     */
    public long getCourseId() {
        return courseId;
    }

    /**
     * Sets the course ID associated with the subject.
     *
     * @param courseId the course ID
     */
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    /**
     * Gets the name of the associated course.
     *
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Sets the name of the associated course.
     *
     * @param courseName the course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Gets the subject description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the subject description.
     *
     * @param description the subject description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Returns the display value of the subject,
     * typically the subject name.
     *
     * @return subject name
     */
    @Override
    public String getValue() {
        return name;
    }
}
