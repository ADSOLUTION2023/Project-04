package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {
	public static void main(String[] args) {
		System.out.println(testNextPk());
		testAdd();
//		System.out.println(testNextPk());
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByName();
//		testSearch();
//		testList();
	}

	public static int testNextPk() {
		RoleModel model = new RoleModel();
		try {
			return model.nextPk();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static void testAdd() {
		RoleModel model = new RoleModel();
		try {
			RoleBean bean = new RoleBean();
			bean.setName("kiosk");
			bean.setDescription("kiosk");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			try {
				long pk = model.add(bean);
				System.out.println("role added successfully");
			} catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		RoleModel model = new RoleModel();
		try {
			RoleBean bean = new RoleBean();
			bean.setId(2);
			model.delete(bean);
			System.out.println("role deleted successfully");

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		RoleModel model = new RoleModel();

		RoleBean bean;
		try {
			bean = model.findByPk(2L);
			bean.setName("hr");
			model.update(bean);
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("role updated successfully");
	}

	public static void testFindByPk() {
		RoleModel model = new RoleModel();

		try {
			RoleBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println("Id : " + bean.getId());
			System.out.println("Name : " + bean.getName());
			System.out.println("Description : " + bean.getDescription());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		RoleModel model = new RoleModel();

		try {
			RoleBean bean = model.findByName("student");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println("Id : " + bean.getId());
			System.out.println("Name : " + bean.getName());
			System.out.println("Description : " + bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		RoleModel model = new RoleModel();
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("student");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		RoleModel model = new RoleModel();
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
