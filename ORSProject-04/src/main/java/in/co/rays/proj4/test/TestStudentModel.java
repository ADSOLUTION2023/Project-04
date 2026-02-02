package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {
	static StudentModel model = new StudentModel();

	public static void main(String[] args) {
//		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByEmail();
//		testSearch();
//		testList();
//		testNextPk();
	}

	public static void testList() {
		try {
			StudentBean bean = null;
			List list = new ArrayList();
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println("Id : " + bean.getId());
				System.out.println("Name : " + bean.getFirstName() + " " + bean.getLastName());
				System.out.println("DOB : " + bean.getDob());
				System.out.println("Mobile no. : " + bean.getMobileNo());
				System.out.println("Email : " + bean.getEmail());
				System.out.println("College ID : " + bean.getCollegeId());
				System.out.println("College Name : " + bean.getCollegeName());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			StudentBean bean = new StudentBean();
			bean.setFirstName("Chaitanya");
			List list = new ArrayList();
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println("Id : " + bean.getId());
				System.out.println("Name : " + bean.getFirstName() + " " + bean.getLastName());
				System.out.println("DOB : " + bean.getDob());
				System.out.println("Mobile no. : " + bean.getMobileNo());
				System.out.println("Email : " + bean.getEmail());
				System.out.println("College ID : " + bean.getCollegeId());
				System.out.println("College Name : " + bean.getCollegeName());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByEmail() {
		try {
			StudentBean bean = model.findByEmailId("mb@gmail.com");
			System.out.println("Id : " + bean.getId());
			System.out.println("Name : " + bean.getFirstName() + " " + bean.getLastName());
			System.out.println("DOB : " + bean.getDob());
			System.out.println("Mobile no. : " + bean.getMobileNo());
			System.out.println("Email : " + bean.getEmail());
			System.out.println("College ID : " + bean.getCollegeId());
			System.out.println("College Name : " + bean.getCollegeName());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			StudentBean bean = model.findByPk(1);
			System.out.println("Id : " + bean.getId());
			System.out.println("Name : " + bean.getFirstName() + " " + bean.getLastName());
			System.out.println("DOB : " + bean.getDob());
			System.out.println("Mobile no. : " + bean.getMobileNo());
			System.out.println("Email : " + bean.getEmail());
			System.out.println("College ID : " + bean.getCollegeId());
			System.out.println("College Name : " + bean.getCollegeName());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		StudentBean bean = new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setId(2);
			bean.setFirstName("Mihir");
			bean.setLastName("Bhatt");
			bean.setDob(sdf.parse("2002-08-11"));
			bean.setGender("mail");
			bean.setMobileNo("7220044837");
			bean.setEmail("mb@gmail.com");
			bean.setCollegeId(4);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("Student Updated Successfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		StudentBean bean = new StudentBean();
		bean.setId(3);
		try {
			model.delete(bean);
			System.out.println("Student Deleted Successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testAdd() {
		StudentBean bean = new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setFirstName("Ruby");
			bean.setLastName("Rani");
			bean.setDob(sdf.parse("2002-02-16"));
			bean.setGender("femail");
			bean.setMobileNo("8401644837");
			bean.setEmail("rr@gmail.com");
			bean.setCollegeId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			System.out.println("Student Added Successfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testNextPk() {
		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
