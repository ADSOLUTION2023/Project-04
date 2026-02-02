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
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {
	static TimetableModel model = new TimetableModel();
	static TimetableBean bean;

	public static void main(String[] args) {
		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testCheckByCourseName();
//		testCheckByExamTime();
//		testCheckBySemester();
//		testCheckBySubjectName();
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
		bean = new TimetableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setSemester("2");
			bean.setDescription("Mid Sem");
			bean.setExamDate(sdf.parse("2025-12-05"));
			bean.setExamTime("10:30 AM - 12:00 PM");
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			System.out.println("Timetable added seccessfully.");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {
		bean = new TimetableBean();
		bean.setId(3);
		try {
			model.delete(bean);
			System.out.println("Timetable deleted seccessfully.");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testUpdate() {
		bean = new TimetableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setId(2);
			bean.setSemester("2");
			bean.setDescription("Mid Sem");
			bean.setExamDate(sdf.parse("2025-12-05"));
			bean.setExamTime("10:30 AM - 12:00 PM");
			bean.setCourseId(1);
			bean.setSubjectId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("Timetable updated seccessfully.");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {
		try {
			bean = model.findByPk(2);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Semester : " + bean.getSemester() + "\t");
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("Exam Date : " + bean.getExamDate() + "\t");
			System.out.println("Exam Time : " + bean.getExamTime());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testCheckByCourseName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date ed = sdf.parse("2025-12-05");
			bean = model.checkByCourseName(1L, ed);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Semester : " + bean.getSemester() + "\t");
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("Exam Date : " + bean.getExamDate() + "\t");
			System.out.println("Exam Time : " + bean.getExamTime());
		} catch (ApplicationException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testCheckBySubjectName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date ed = sdf.parse("2025-12-05");
			bean = model.checkBySubjectName(1L, 1L, ed);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Semester : " + bean.getSemester() + "\t");
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("Exam Date : " + bean.getExamDate() + "\t");
			System.out.println("Exam Time : " + bean.getExamTime());
		} catch (ApplicationException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testCheckBySemester() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date ed = sdf.parse("2025-12-05");
			bean = model.checkBySemester(1L, 1L, "2", ed);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Semester : " + bean.getSemester() + "\t");
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("Exam Date : " + bean.getExamDate() + "\t");
			System.out.println("Exam Time : " + bean.getExamTime());
		} catch (ApplicationException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testCheckByExamTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date ed = sdf.parse("2025-12-05");
			bean = model.checkByExamTime(1L, 1L, "2", ed, "10:30 AM - 12:00 PM", "Mid Sem");
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Semester : " + bean.getSemester() + "\t");
			System.out.print("Course Name : " + bean.getCourseName() + "\t");
			System.out.print("Subject : " + bean.getSubjectName() + "\t");
			System.out.print("Exam Date : " + bean.getExamDate() + "\t");
			System.out.println("Exam Time : " + bean.getExamTime());
		} catch (ApplicationException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testSearch() {
		bean = new TimetableBean();
		bean.setDescription("Mid Sem");
		List list = new ArrayList();
		try {
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimetableBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Semester : " + bean.getSemester() + "\t");
				System.out.print("Course Name : " + bean.getCourseName() + "\t");
				System.out.print("Subject : " + bean.getSubjectName() + "\t");
				System.out.print("Exam Date : " + bean.getExamDate() + "\t");
				System.out.println("Exam Time : " + bean.getExamTime());
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
				bean = (TimetableBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Semester : " + bean.getSemester() + "\t");
				System.out.print("Course Name : " + bean.getCourseName() + "\t");
				System.out.print("Subject : " + bean.getSubjectName() + "\t");
				System.out.print("Exam Date : " + bean.getExamDate() + "\t");
				System.out.println("Exam Time : " + bean.getExamTime());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
		}
	}
}
