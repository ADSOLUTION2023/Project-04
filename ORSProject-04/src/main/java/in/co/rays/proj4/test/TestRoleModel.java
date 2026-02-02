package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DataBaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {

public static RoleModel model = new RoleModel();

	public static void main(String[] args) {

		//testAdd();
		//testUpdate();
		//testDelete(); 
	testfindByPk();
		//testFindByName();
		//testSearch(); 
	}

	public static void testAdd() {
		try {
			RoleBean bean = new RoleBean();
			bean.setName("student2");
			bean.setDescription("student");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			try {
				long pk = model.add(bean);
			} catch (DataBaseException e) {
				e.printStackTrace();
			}
			System.out.println("Test add");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	public static void testUpdate() {
		try {
			RoleBean bean = model.findByPk(1L);
			bean.setName("Amit");
			bean.setDescription("Amit");
			model.update(bean);
			System.out.println("Test Update");
			
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
public static void testDelete() {
	try {
		RoleBean bean = new RoleBean();
		long pk = 1L;
		bean.setId(pk);
		model.delete(bean);
		RoleBean deletedbean = model.findByPk(pk);
		if (deletedbean != null) {
			System.out.println("Test Delete fail");
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}


public static void testfindByPk() {
	try {
		RoleBean bean = model.findByPk(1);
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testFindByName() {
	try {
		RoleBean bean = model.findByName("Amit");
		if (bean == null) {
			System.out.println("Test Find By Name fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
public static void testSearch() {
	try {
		RoleBean bean = new RoleBean();
		List list = new ArrayList();
		bean.setName("Amit");
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
}
