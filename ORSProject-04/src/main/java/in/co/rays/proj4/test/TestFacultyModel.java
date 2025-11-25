package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FacultyModel;

public class TestFacultyModel {

	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) {
		testAdd();
		//testUpdate();
		//testDelete(); 
		//testfindByPk();
		//testFindByName();
		//testSearch(); 
	}

	public static void testAdd() {
		
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	try {
		FacultyBean bean = new FacultyBean();
		bean.setFirstName("Raj");
		bean.setLastName("Sharma");
		try {
			bean.setDob(sdf.parse("2020-12-23"));
		} catch (ParseException e) {
		 
			e.printStackTrace();
		}
		bean.setGender("Male");
		bean.setMobileNo("9630381097");
		bean.setEmail("RajSharma@gmail.com");
		bean.setCollegeId(5);
		bean.setCourseId(3);
		bean.setSubjectId(2);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(bean);
		System.out.println("Test add Successfullty");
	} catch (ApplicationException | DuplicateRecordException e) {
		e.printStackTrace();
	}
}
}