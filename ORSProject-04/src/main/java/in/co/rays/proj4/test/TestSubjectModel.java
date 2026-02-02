package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {
	static SubjectModel model = new SubjectModel();
	static SubjectBean bean;
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
				bean = (SubjectBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Name : " + bean.getName() + "\t");
				System.out.print("Course Name : " + bean.getCourseName() + "\t");
				System.out.println("Discraption : " + bean.getDescription());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		}
	}
	public static void testSearch() {
		bean = new SubjectBean();
		bean.setName("HTML");
		List list = new ArrayList();
		try {
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Name : " + bean.getName() + "\t");
				System.out.print("Course Name : " + bean.getCourseName() + "\t");
				System.out.println("Discraption : " + bean.getDescription());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		}
	}
	public static void testFindByName() {
		try {
			bean = model.findByName("CSS");
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Name : " + bean.getName() + "\t");
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
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
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
			System.out.println("Discraption : " + bean.getDescription());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testUpdate() {
		bean = new SubjectBean();
		bean.setId(2);
		bean.setName("CSS");
		bean.setCourseId(1);
		bean.setDescription("CSS, Telwint CSS");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.update(bean);
			System.out.println("Subject updated successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testDelete() {
		bean = new SubjectBean();
		bean.setId(3);
		try {
			model.delete(bean);
			System.out.println("Subject deleted successfully");

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testAdd() {
		bean = new SubjectBean();
		bean.setName("JS");
		bean.setCourseId(1);
		bean.setDescription("HTML5, HTML advance");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.add(bean);
			System.out.println("Subject added successfully");
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
