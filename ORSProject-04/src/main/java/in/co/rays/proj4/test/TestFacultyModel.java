package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FacultyModel;

public class TestFacultyModel {
	static FacultyModel model = new FacultyModel();
	static FacultyBean bean;

	public static void main(String[] args) {
		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByEmail();
//		testSearch();
//		testList();
		testNextPk();
	}

	public static void testNextPk() {
		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testAdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean = new FacultyBean();
			bean.setFirstName("Chaitanya");
			bean.setLastName("Bhatt");
			bean.setDob(sdf.parse("2001-07-14"));
			bean.setGender("mail");
			bean.setMobileNo("0123456789");
			bean.setEmail("cb@gmail.com");
			bean.setCollegeId(1);
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			System.out.println("Faculty added successfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		bean = new FacultyBean();
		bean.setId(3);
		try {
			model.delete(bean);
			System.out.println("Faculty deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean = new FacultyBean();
			bean.setId(2);
			bean.setFirstName("Ruby");
			bean.setLastName("Rani");
			bean.setDob(sdf.parse("2002-02-16"));
			bean.setGender("mail");
			bean.setMobileNo("3216549870");
			bean.setEmail("rr@gmail.com");
			bean.setCollegeId(2);
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("Faculty added successfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			bean = model.findByPk(2);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Name : " + bean.getFirstName() + " " + bean.getLastName()+ "\t");
			System.out.print("Course : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("College : " + bean.getCollegeName() + "\t");
			System.out.println("Email : " + bean.getEmail());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByEmail() {
		try {
			bean = model.findByEmail("cb@gmail.com");
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Name : " + bean.getFirstName() + " " + bean.getLastName()+ "\t");
			System.out.print("Course : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("College : " + bean.getCollegeName() + "\t");
			System.out.println("Email : " + bean.getEmail());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		bean.setFirstName("Chaitanya");;
		List list = new ArrayList();
		try {
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Name : " + bean.getFirstName() + " " + bean.getLastName()+ "\t");
				System.out.print("Course : " + bean.getCourseName() + "\t");
				System.out.print("Subject : " + bean.getSubjectName() + "\t");
				System.out.print("College : " + bean.getCollegeName() + "\t");
				System.out.println("Email : " + bean.getEmail());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		}
	}

	public static void testList() {
		List list = new ArrayList();
		try {
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Name : " + bean.getFirstName() + " " + bean.getLastName()+ "\t");
				System.out.print("Course : " + bean.getCourseName() + "\t");
				System.out.print("Subject : " + bean.getSubjectName() + "\t");
				System.out.print("College : " + bean.getCollegeName() + "\t");
				System.out.println("Email : " + bean.getEmail());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		}
	}
}
