package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {
	static CourseModel model = new CourseModel();
	static CourseBean bean;
	public static void main(String[] args) {
//		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
//		testSearch();
//		testList();
//		testNextPk();
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
				bean = (CourseBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Name : " + bean.getName() + "\t");
				System.out.print("Duration : " + bean.getDuration() + "\t");
				System.out.println("Discraption : " + bean.getDescription());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testSearch() {
		bean = new CourseBean();
		bean.setName("dev");
		List list = new ArrayList();
		try {
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Name : " + bean.getName() + "\t");
				System.out.print("Duration : " + bean.getDuration() + "\t");
				System.out.println("Discraption : " + bean.getDescription());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testFindByName() {
		try {
			bean = model.findByName("Java Development");
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Name : " + bean.getName() + "\t");
			System.out.print("Duration : " + bean.getDuration() + "\t");
			System.out.println("Discraption : " + bean.getDescription());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testFindByPk() {
		try {
			bean = model.findByPk(1);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Name : " + bean.getName() + "\t");
			System.out.print("Duration : " + bean.getDuration() + "\t");
			System.out.println("Discraption : " + bean.getDescription());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testUpdate() {
		bean = new CourseBean();
		bean.setId(2);
		bean.setName("Java Development");
		bean.setDuration("6 Mounths");
		bean.setDescription("Core Java, DSA");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.update(bean);
			System.out.println("Course updated successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testDelete() {
		bean = new CourseBean();
		bean.setId(3);
		try {
			model.delete(bean);
			System.out.println("Course deleted successfully");

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testAdd() {
		bean = new CourseBean();
		bean.setName("Android Development");
		bean.setDuration("6 Mounths");
		bean.setDescription("HTML, CSS, JavaScript");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.add(bean);
			System.out.println("Course added successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
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
