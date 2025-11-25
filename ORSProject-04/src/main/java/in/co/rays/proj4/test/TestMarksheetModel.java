package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarkSheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarkSheetModel;

public class TestMarksheetModel {
	
public static MarkSheetModel model = new MarkSheetModel();

		public static void main(String[] args) {

			//testAdd();
			 testUpdate();
			//testDelete();
			//testfindByPk();
			//testFindByLogin();
			//testSearch();
		}
		
public static void testAdd() {
			
			MarkSheetBean bean = new MarkSheetBean();
			
			
			try {
				bean.setRollNo("1");
				bean.setStudentId(1);
				bean.setPhysics(64);
				bean.setChemistry(75);
				bean.setMaths(75);
				bean.setCreatedBy("admin");
				bean.setModifiedBy("admin");
				bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
				bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
				long pk = model.add(bean);
				System.out.println("Test add Successfull");

			} catch (ApplicationException | DuplicateRecordException e) {
				e.printStackTrace();
			}
		}
public static void testUpdate() {
	try {
		MarkSheetBean bean = model.findByPk(1L);
		bean.setRollNo("34");
		bean.setStudentId(1);
		bean.setPhysics(64);
		bean.setChemistry(75);
		bean.setMaths(75);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(bean);
		System.out.println("Test Update Succesfull");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
public static void testDelete() {
	try {
		MarkSheetBean bean = new MarkSheetBean();
		long pk = 2L;
		bean.setId(pk);
		model.delete(bean);
		MarkSheetBean deletedbean = model.findByPk(pk);
		System.out.println("Test Delete Sucessfull");
		if (deletedbean != null) {
			System.out.println("Test Delete fail");
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testfindByPk() {
	try {
		MarkSheetBean bean = model.findByPk(2L);
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getRollNo());
		System.out.println(bean.getStudentId());
		System.out.println(bean.getName());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getChemistry());
		System.out.println(bean.getMaths());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
	
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testFindByRollNo() {
	try {
		MarkSheetBean bean = model.findByRollNo("09234");
		if (bean == null) {
			System.out.println("Test Find By Name fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getStudentId());
		System.out.println(bean.getName());
		System.out.println(bean.getPhysics());
		System.out.println(bean.getChemistry());
		System.out.println(bean.getMaths());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
public static void testSearch() {
	try {
		MarkSheetBean bean = new MarkSheetBean();
		List list = new ArrayList();
		bean.setName("Amit");
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Search fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (MarkSheetBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
}

