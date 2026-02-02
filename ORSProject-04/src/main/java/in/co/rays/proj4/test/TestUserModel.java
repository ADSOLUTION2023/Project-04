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
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {
	static UserModel model = new UserModel();

	public static void main(String[] args) {
//		testNextPk();
//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testFindByEmail();
//		testNextPk();
//		testSearch();
//		testList();
//		testAuthenticate();
		
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			UserBean bean = new UserBean();
			bean.setFirstName("Chaitanya");
			bean.setLastName("Bhatt");
			bean.setLogin("cb@gmail.com");
			bean.setPassword("1234");
			bean.setDob(sdf.parse("2001-07-14"));
			bean.setMobileNo("7220044837");
			bean.setRoleId(1);
			bean.setGender("mail");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			System.out.println("User Added Successfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			e.printStackTrace();
		}

	}
	
	public static void testUpdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			UserBean bean = new UserBean();
			bean.setId(2);
			bean.setFirstName("Chaitanya");
			bean.setLastName("Bhatt");
			bean.setLogin("cb12@gmail.com");
			bean.setPassword("1234");
			bean.setDob(sdf.parse("2001-07-14"));
			bean.setMobileNo("7220044837");
			bean.setRoleId(1);
			bean.setGender("mail");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("User updated Successfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void testDelete() {
		UserBean bean = new UserBean();
		bean.setId(3);
		try {
			model.delete(bean);
			System.out.println("User deleted successfully.");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {
		try {
			UserBean bean = model.findByPk(1);
			System.out.println("id : "+ bean.getId());
			System.out.println("First Name : " + bean.getFirstName());
			System.out.println("Last Name : " + bean.getLastName());
			System.out.println("Email : " + bean.getLogin());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByEmail() {
		try {
			UserBean bean = model.findByLogin("cb@gmail.com");
			System.out.println("id : "+ bean.getId());
			System.out.println("First Name : " + bean.getFirstName());
			System.out.println("Last Name : " + bean.getLastName());
			System.out.println("Email : " + bean.getLogin());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testSearch() {
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("Chaitanya");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println("id : "+ bean.getId());
				System.out.println("First Name : " + bean.getFirstName());
				System.out.println("Last Name : " + bean.getLastName());
				System.out.println("Email : " + bean.getLogin());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			UserBean bean = null;
			List list = new ArrayList();
			list = model.list();
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println("id : "+ bean.getId());
				System.out.println("First Name : " + bean.getFirstName());
				System.out.println("Last Name : " + bean.getLastName());
				System.out.println("Email : " + bean.getLogin());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testAuthenticate() {
		try {
			UserBean bean = model.authenticate("cb@gmail.com","1234");
			System.out.println("id : "+ bean.getId());
			System.out.println("First Name : " + bean.getFirstName());
			System.out.println("Last Name : " + bean.getLastName());
			System.out.println("Email : " + bean.getLogin());
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
