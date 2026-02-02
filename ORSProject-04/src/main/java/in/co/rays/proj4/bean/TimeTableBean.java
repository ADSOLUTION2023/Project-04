package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimetableBean represents the timetable information for examinations.
 * It includes details such as semester, exam date, exam time, course,
 * subject, and a description. This class extends {@link BaseBean}
 * to inherit standard audit attributes.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class TimetableBean extends BaseBean {

    /** Semester for which the timetable is scheduled. */
    private String semester;

    /** Description or additional details about the exam. */
    private String description;

    /** Date on which the exam will be conducted. */
    private Date examDate;

    /** Time at which the exam will be conducted. */
    private String examTime;

    /** ID of the associated course. */
    private long courseId;

    /** Name of the associated course. */
    private String courseName;

    /** ID of the subject included in the timetable. */
    private long subjectId;

    /** Name of the subject included in the timetable. */
    private String subjectName;

    /**
     * Gets the semester.
     *
     * @return semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * Sets the semester.
     *
     * @param semester the semester
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * Gets the timetable description.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the timetable description.
     *
     * @param description details or notes
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the exam date.
     *
     * @return examDate
     */
    public Date getExamDate() {
        return examDate;
    }

    /**
     * Sets the exam date.
     *
     * @param examDate the exam date
     */
    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    /**
     * Gets the exam time.
     *
     * @return examTime
     */
    public String getExamTime() {
        return examTime;
    }

    /**
     * Sets the exam time.
     *
     * @param examTime the exam time
     */
    public void setExamTime(String examTime) {
        this.examTime = examTime;
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
     * Returns the unique key for this timetable entry.
     * Typically the ID inherited from BaseBean.
     *
     * @return key as String
     */
    @Override
    public String getKey() {
        return id + "";
    }

    /**
     * Returns the display value for this timetable entry.
     * Typically a combination of semester and subject name.
     *
     * @return readable timetable label
     */
    @Override
    public String getValue() {
        return semester + " - " + subjectName;
    }
}
