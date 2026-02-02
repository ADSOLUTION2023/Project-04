package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;

public class TestMarksheetModel {
	static MarksheetModel model = new MarksheetModel();
	static MarksheetBean bean = null;

	public static void main(String[] args) {
//		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByRollNo();
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
				bean = (MarksheetBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Roll No. : " + bean.getRollNo()+ "\t");
				System.out.print("Name : " + bean.getName()+ "\t");
				System.out.print("Maths : " + bean.getMaths()+ "\t");
				System.out.print("Physics : " +bean.getPhysics()+ "\t");
				System.out.println("Chemistry : " + bean.getChemistry());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		bean = new MarksheetBean();
		bean.setName("Bhatt");
		List list = new ArrayList();
		try {
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.print("Id : " + bean.getId() + "\t");
				System.out.print("Roll No. : " + bean.getRollNo()+ "\t");
				System.out.print("Name : " + bean.getName()+ "\t");
				System.out.print("Maths : " + bean.getMaths()+ "\t");
				System.out.print("Physics : " +bean.getPhysics()+ "\t");
				System.out.println("Chemistry : " + bean.getChemistry());
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByRollNo() {
		
		try {
			bean = model.findByRollNo("200303105216");
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Roll No. : " + bean.getRollNo()+ "\t");
			System.out.print("Name : " + bean.getName()+ "\t");
			System.out.print("Maths : " + bean.getMaths()+ "\t");
			System.out.print("Physics : " +bean.getPhysics()+ "\t");
			System.out.println("Chemistry : " + bean.getChemistry());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			bean = model.findByPk(2);
			System.out.print("Id : " + bean.getId() + "\t");
			System.out.print("Roll No. : " + bean.getRollNo()+ "\t");
			System.out.print("Name : " + bean.getName()+ "\t");
			System.out.print("Maths : " + bean.getMaths()+ "\t");
			System.out.print("Physics : " +bean.getPhysics()+ "\t");
			System.out.println("Chemistry : " + bean.getChemistry());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static void testUpdate() {
		bean = new MarksheetBean();
		bean.setId(2);
		bean.setRollNo("200303105211");
		bean.setStudentId(3);
		bean.setPhysics(80);
		bean.setChemistry(89);
		bean.setMaths(70);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.update(bean);
			System.out.println("Marksheet updated successfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		bean = new MarksheetBean();
		bean.setId(2);
		try {
			model.delete(bean);
			System.out.println("Marksheet deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testAdd() {
		bean = new MarksheetBean();
		bean.setRollNo("200303105211");
		bean.setStudentId(1);
		bean.setPhysics(80);
		bean.setChemistry(89);
		bean.setMaths(70);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.add(bean);
			System.out.println("Marksheet added successfully");
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
