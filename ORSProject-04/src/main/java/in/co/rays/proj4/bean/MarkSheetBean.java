package in.co.rays.proj4.bean;

/**
 * MarksheetBean represents the marksheet details of a student.
 * It includes subject-wise marks, roll number, and student identity details.
 * This class inherits audit fields from {@link BaseBean}.
 *
 * author Chaitanya Bhatt
 * @version 1.0
 */
public class MarksheetBean extends BaseBean {

    /** Unique roll number assigned to the student. */
    private String rollNo;

    /** ID of the student to whom this marksheet belongs. */
    private long studentId;

    /** Name of the student. */
    private String name;

    /** Marks obtained in Physics. */
    private Integer physics;

    /** Marks obtained in Chemistry. */
    private Integer chemistry;

    /** Marks obtained in Mathematics. */
    private Integer maths;

    /**
     * Gets the roll number of the student.
     *
     * @return the roll number
     */
    public String getRollNo() {
        return rollNo;
    }

    /**
     * Sets the roll number of the student.
     *
     * @param rollNo the roll number
     */
    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    /**
     * Gets the student ID.
     *
     * @return the studentId
     */
    public long getStudentId() {
        return studentId;
    }

    /**
     * Sets the student ID.
     *
     * @param studentId the ID of the student
     */
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets the name of the student.
     *
     * @return student name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student's name.
     *
     * @param name the student's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Physics marks.
     *
     * @return physics marks
     */
    public Integer getPhysics() {
        return physics;
    }

    /**
     * Sets the Physics marks.
     *
     * @param physics marks in physics
     */
    public void setPhysics(int physics) {
        this.physics = physics;
    }

    /**
     * Gets the Chemistry marks.
     *
     * @return chemistry marks
     */
    public Integer getChemistry() {
        return chemistry;
    }

    /**
     * Sets the Chemistry marks.
     *
     * @param chemistry marks in chemistry
     */
    public void setChemistry(int chemistry) {
        this.chemistry = chemistry;
    }

    /**
     * Gets the Mathematics marks.
     *
     * @return maths marks
     */
    public Integer getMaths() {
        return maths;
    }

    /**
     * Sets the Mathematics marks.
     *
     * @param maths marks in mathematics
     */
    public void setMaths(int maths) {
        this.maths = maths;
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
     * Returns the display value of the marksheet, 
     * typically the roll number.
     *
     * @return roll number
     */
    @Override
    public String getValue() {
        return rollNo;
    }

}
