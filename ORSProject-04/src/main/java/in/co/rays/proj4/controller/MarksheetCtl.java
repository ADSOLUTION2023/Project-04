package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarkSheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarkSheetModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "MarksheetCtl", urlPatterns = { "/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel StudentModel = new StudentModel();
		List<StudentBean> studentList;
		try {
			studentList = StudentModel.list();
			request.setAttribute("studentList", studentList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		super.preload(request);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		MarkSheetModel model = new MarkSheetModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			MarkSheetBean bean = (MarkSheetBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Marksheet added successfully", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		 
		 boolean pass = true;

		    // ---------- Roll Number ----------
		    if (DataValidator.isNull(request.getParameter("rollNo"))) {
		        request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
		        pass = false;
		    } else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
		        request.setAttribute("rollNo", "Invalid Roll Number (e.g. 101 or CS101)");
		        pass = false;
		    }

		    // ---------- Student ID ----------
		    if (DataValidator.isNull(request.getParameter("studentId"))) {
		        request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student"));
		        pass = false;
		    } else if (!DataValidator.isLong(request.getParameter("studentId"))) {
		        request.setAttribute("studentId", "Invalid Student");
		        pass = false;
		    }

		    // ---------- Student Name ----------
		    if (DataValidator.isNull(request.getParameter("name"))) {
		        request.setAttribute("name", PropertyReader.getValue("error.require", "Student Name"));
		        pass = false;
		    } else if (!DataValidator.isName(request.getParameter("name"))) {
		        request.setAttribute("name", "Invalid Name");
		        pass = false;
		    }

		    // ---------- Physics ----------
		    if (DataValidator.isNull(request.getParameter("physics"))) {
		        request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics Marks"));
		        pass = false;
		    } else if (!DataValidator.isInteger(request.getParameter("physics"))) {
		        request.setAttribute("physics", "Marks must be numeric");
		        pass = false;
		    } else {
		        int p = Integer.parseInt(request.getParameter("physics"));
		        if (p < 0 || p > 100) {
		            request.setAttribute("physics", "Marks must be between 0 and 100");
		            pass = false;
		        }
		    }

		    // ---------- Chemistry ----------
		    if (DataValidator.isNull(request.getParameter("chemistry"))) {
		        request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Marks"));
		        pass = false;
		    } else if (!DataValidator.isInteger(request.getParameter("chemistry"))) {
		        request.setAttribute("chemistry", "Marks must be numeric");
		        pass = false;
		    } else {
		        int c = Integer.parseInt(request.getParameter("chemistry"));
		        if (c < 0 || c > 100) {
		            request.setAttribute("chemistry", "Marks must be between 0 and 100");
		            pass = false;
		        }
		    }

		    // ---------- Maths ----------
		    if (DataValidator.isNull(request.getParameter("maths"))) {
		        request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths Marks"));
		        pass = false;
		    } else if (!DataValidator.isInteger(request.getParameter("maths"))) {
		        request.setAttribute("maths", "Marks must be numeric");
		        pass = false;
		    } else {
		        int m = Integer.parseInt(request.getParameter("maths"));
		        if (m < 0 || m > 100) {
		            request.setAttribute("maths", "Marks must be between 0 and 100");
		            pass = false;
		        }
		    }

		    return pass;
		}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		 
			MarkSheetBean bean = new MarkSheetBean();

			bean.setId(DataUtility.getLong(request.getParameter("id")));
			bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
			bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
			bean.setName(DataUtility.getString(request.getParameter("name")));
			bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
			bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
			bean.setMaths(DataUtility.getInt(request.getParameter("maths")));;
			 
			populateDTO(bean, request);
			
			return bean;
		}

	@Override
	protected String getView() {
		 
		return ORSView.MARKSHEET_VIEW;
	}

}
