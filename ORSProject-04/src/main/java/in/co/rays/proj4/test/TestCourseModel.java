package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {
	
	public static CourseModel model = new CourseModel();
	
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
		CourseBean bean = new CourseBean();
		bean.setName("Python");
		bean.setDescription("student");
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
		CourseBean bean = model.findByPk(1L);
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
	CourseBean bean = new CourseBean();
	long pk = 1L;
	bean.setId(pk);
	model.delete(bean);
	CourseBean deletedbean = model.findByPk(pk);
	if (deletedbean != null) {
		System.out.println("Test Delete fail");
	}
} catch (ApplicationException e) {
	e.printStackTrace();
}
}


public static void testfindByPk() {
try {
	CourseBean bean = model.findByPk(1L);
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
	CourseBean bean = model.findByName("Amit");
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
	CourseBean bean = new CourseBean();
	List list = new ArrayList();
	bean.setName("Amit");
	list = model.search(bean, 0, 0);
	if (list.size() < 0) {
		System.out.println("Test Serach fail");
	}
	Iterator it = list.iterator();
	while (it.hasNext()) {
		bean = (CourseBean) it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
	}
} catch (ApplicationException e) {
	e.printStackTrace();
}
}
}
