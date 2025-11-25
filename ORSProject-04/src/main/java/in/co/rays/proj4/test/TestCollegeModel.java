package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;

public class TestCollegeModel {
	
	public static CollegeModel model = new CollegeModel();
	
	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
	//testDelete(); 
		//testfindByPk();
		//testFindByName();
		testSearch();
		
	}
public static void testAdd() {
		try {
			CollegeBean bean = new CollegeBean();
			bean.setName("medicaps5");
			bean.setAddress("Indore");
			bean.setState("MP");
			bean.setCity("Indore");
			bean.setPhoneNo("9823678909");
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
		CollegeBean bean = model.findByPk(1L);
		bean.setName("raj");
		bean.setAddress("Indore");
		bean.setState("MP");
		bean.setCity("Indore");
		bean.setPhoneNo("9823678909");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);
		System.out.println("Test Update Succesfull");
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
public static void testDelete() {
	try {
		CollegeBean bean = new CollegeBean();
		long pk = 2L;
		bean.setId(pk);
		model.delete(bean);
		CollegeBean deletedbean = model.findByPk(pk);
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
		CollegeBean bean = model.findByPk(2L);
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
	
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testFindByName() {
	try {
		CollegeBean bean = model.findByName("Amit");
		if (bean == null) {
			System.out.println("Test Find By Name fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
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
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
		bean.setName("Amit");
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Search fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CollegeBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
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
	
