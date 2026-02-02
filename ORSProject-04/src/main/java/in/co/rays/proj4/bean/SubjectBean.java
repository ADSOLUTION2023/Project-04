package in.co.rays.proj4.bean;
 
/**
 * Subject JavaBean encapsulates Subject attributes.
 * 
 * @author Amit Chandsarkar
 *
 */
public class SubjectBean extends BaseBean{

	private String subjectName;
	private String description;
	private long courseId;
	private String courseName;
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Override
	public String getKey() {
		 
		return id+"";
	}
	public String getValue() {
		 
		return subjectName;
	}
}
