package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {
	
	public static StudentModel model = new StudentModel();

		public static void main(String[] args) {

			testAdd();
			 //testUpdate();
			//testDelete();
			//testfindByPk();
			//testFindByLogin();
			//testSearch();
		}

public static void testAdd() {
			
			StudentBean bean = new StudentBean();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			try {
				bean.setFirstName("raj");
				bean.setLastName("Sharma");
				try {
					bean.setDob(sdf.parse("2002-09-27"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				bean.setMobileNo("9673890712");
				bean.setEmail("Raj@gmail.com");
				bean.setGender("Male");
				bean.setCollegeId(5);
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
			
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			

			try {
				bean.setFirstName("raj");
				bean.setLastName("Sharma");
				try {
					bean.setDob(sdf.parse("2002-09-27"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				bean.setMobileNo("9673890712");
				bean.setEmail("Raj@gmail.com");
				bean.setGender("Male");
				bean.setCollegeId(0001);
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
				StudentBean bean = new StudentBean();
				long pk = 1L;
				bean.setId(pk);
				model.delete(bean);
				StudentBean deletedbean = model.findByPk(pk);
				if (deletedbean != null) {
					System.out.println("Test Delete fail");
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

		public static void testfindByPk() {
		
			
			try {
				StudentBean bean = model.findByPk(2L);
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

		public static void testFindByEmailId() {
			
			
			try {
				StudentBean bean = model.findByPk(0001);
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
				StudentBean bean = new StudentBean();
				List list = new ArrayList();
				bean.setFirstName("Raj");
				list = model.search(bean, 0, 0);
				if (list.size() < 0) {
					System.out.println("Test Search fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (StudentBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getFirstName());
					System.out.println(bean.getLastName());
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}

}
