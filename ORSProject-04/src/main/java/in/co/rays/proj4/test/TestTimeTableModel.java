package in.co.rays.proj4.test;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimeTableModel {

public static TimetableModel model = new TimetableModel();

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
			TimetableBean bean = new TimetableBean();
			bean.setSemester("First");
			try {
				bean.setExamDate(sdf.parse("2020-12-23"));
			} catch (ParseException e) {
			 
				e.printStackTrace();
			}
			bean.setExamTime("9 am to 12 pm");
			bean.setCourseId(001);
			bean.setSubjectId(101);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("Test add");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	public static void testUpdate() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			TimetableBean bean = new TimetableBean();
			bean.setSemester("First");
			try {
				bean.setExamDate(sdf.parse("2020-12-23"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			bean.setExamTime("9 am to 12 pm");
			bean.setCourseId(001);
			bean.setSubjectId(101);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("Test Update");
			
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
public static void testDelete() {
	try {
		TimetableBean bean = new TimetableBean();
		long pk = 1L;
		bean.setId(pk);
		model.delete(bean);
		TimetableBean deletedbean = model.findByPk(pk);
		if (deletedbean != null) {
			System.out.println("Test Delete fail");
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testfindByPk() {
	try {
		TimetableBean bean = model.findByPk(1L);
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getId());
			/*
			 * System.out.println(bean.getName());
			 */		System.out.println(bean.getDescription());
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
}
