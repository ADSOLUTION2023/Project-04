package in.co.rays.proj4.bean;

/**
 * CourseBean represents a course entity within the system. 
 * It contains details such as course name, duration, and description.
 * This class extends {@link BaseBean} to inherit common auditing fields.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class CourseBean extends BaseBean {

    /** Name of the course. */
    private String name;

    /** Duration of the course (e.g., "2 Years", "6 Months"). */
    private String duration;

    /** Detailed description of the course. */
    private String description;

    /**
     * Gets the name of the course.
     *
     * @return the course name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the course.
     *
     * @param name the course name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the duration of the course.
     *
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the course.
     *
     * @param duration the course duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets the description of the course.
     *
     * @return the course description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the course.
     *
     * @param description details about the course
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the primary key of the course as a String.
     *
     * @return the key representing the ID
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value of the course, typically the course name.
     *
     * @return the course name
     */
    @Override
    public String getValue() {
        return name;
    }

}
