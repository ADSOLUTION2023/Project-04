package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {

public static SubjectModel model = new SubjectModel();

	public static void main(String[] args) {

		testAdd();
		//testUpdate();
		//testDelete(); 
		//testfindByPk();
		//testFindByName();
		//testSearch(); 
	}

	public static void testAdd() {
		try {
			SubjectBean bean = new SubjectBean();
			bean.setSubjectName("hjjk");
			bean.setCourseId(001);
			bean.setDescription("admin");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("Test add");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	public static void testUpdate() {
		try {
			SubjectBean bean = model.findByPk(1L);
			bean.setSubjectName("Amit");
			bean.setDescription("Amit");
			model.update(bean);
			System.out.println("Test Update");
			
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	
public static void testDelete() {
	try {
		SubjectBean bean = new SubjectBean();
		long pk = 1L;
		bean.setId(pk);
		model.delete(bean);
		SubjectBean deletedbean = model.findByPk(pk);
		if (deletedbean != null) {
			System.out.println("Test Delete fail");
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}


public static void testfindByPk() {
	try {
		SubjectBean bean = model.findByPk(1L);
		if (bean == null) {
			System.out.println("Test Find By PK fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getDescription());
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

public static void testFindByName() {
	try {
		SubjectBean bean = model.findByName("Amit");
		if (bean == null) {
			System.out.println("Test Find By Name fail");
		}
		System.out.println(bean.getId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getDescription());
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
public static void testSearch() {
	try {
		SubjectBean bean = new SubjectBean();
		List list = new ArrayList();
		bean.setSubjectName("student");
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Serach fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (SubjectBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
}

