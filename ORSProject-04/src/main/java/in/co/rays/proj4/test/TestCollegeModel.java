package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;

public class TestCollegeModel {
	static CollegeModel model = new CollegeModel();

	public static void main(String[] args) {
		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
		testFindByName();
		testFindByPk();
		testSearch();
		testList();
		testNextPk();
	}

	public static void testList() {
		try {
			CollegeBean bean = null;
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println("ID : " + bean.getId());
				System.out.println("Name : " + bean.getName());
				System.out.println("State : " + bean.getState());
				System.out.println("City : " + bean.getCity());
				System.out.println("Phone No. :" + bean.getPhoneNo());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("Parul Institute of Engeneering");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println("ID : " + bean.getId());
				System.out.println("Name : " + bean.getName());
				System.out.println("State : " + bean.getState());
				System.out.println("City : " + bean.getCity());
				System.out.println("Phone No. :" + bean.getPhoneNo());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	private static void testFindByPk() {
		try {
			CollegeBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println("ID : " + bean.getId());
			System.out.println("Name : " + bean.getName());
			System.out.println("State : " + bean.getState());
			System.out.println("City : " + bean.getCity());
			System.out.println("Phone No. :" + bean.getPhoneNo());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		try {
			CollegeBean bean = model.findByName("Parul Institute of Engeneering");
			if (bean == null) {
				System.out.println("Test Find By name fail");
			}
			System.out.println("ID : " + bean.getId());
			System.out.println("Name : " + bean.getName());
			System.out.println("State : " + bean.getState());
			System.out.println("City : " + bean.getCity());
			System.out.println("Phone No. :" + bean.getPhoneNo());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {
		CollegeBean bean = new CollegeBean();
		bean.setId(2);
		bean.setName("Parul Institute of Engeneering");
		bean.setAddress("Parul University");
		bean.setState("Gujrat");
		bean.setCity("Ahmedabad");
		bean.setPhoneNo("1234567890");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.update(bean);
			System.out.println("College updated successfilly");
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		CollegeBean bean = new CollegeBean();
		bean.setId(2);
		try {
			model.delete(bean);
			System.out.println("College deleted successfilly");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testAdd() {
		CollegeBean bean = new CollegeBean();
		bean.setName("Parul Institute of Engeneering");
		bean.setAddress("Parul University");
		bean.setState("Gujrat");
		bean.setCity("Vadodara");
		bean.setPhoneNo("1234567890");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.add(bean);
			System.out.println("College added successfilly");
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
