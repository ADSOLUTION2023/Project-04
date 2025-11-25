package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel { 
	public static UserModel model = new UserModel();

	public static void main(String[] args) {

		testAdd();
		 //testUpdate();
		//testDelete();
		//testfindByPk();
		//testFindByLogin();
		//testSearch();
	}

	public static void testAdd() {
		
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			bean.setFirstName("raj");
			bean.setLastName("Sharma");
			bean.setLogin("2762avb@gmail.com");
			bean.setPassword("raj123");
			try {
				bean.setDob(sdf.parse("2002-09-27"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			bean.setMobileNo("9673890712");
			bean.setRoleId(2);
			bean.setGender("Male");
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
			UserBean bean = model.findByPk(2L);
			bean.setFirstName("Rishab1");
			bean.setLastName("Sharma");
			bean.setLogin("amit123@gmail.com");
			bean.setPassword("amit123");
			bean.setMobileNo("9673890712");
			bean.setGender("Male");
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
			UserBean bean = new UserBean();
			long pk = 1L;
			bean.setId(pk);
			model.delete(bean);
			UserBean deletedbean = model.findByPk(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testfindByPk() {
		try {
			UserBean bean = model.findByPk(2L);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByLogin() {
		try {
			UserBean bean = model.findByLogin("raj@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By Login fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("Raj");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
