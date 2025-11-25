package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name ="SubjectCtl", urlPatterns ={ "/SubjectCtl" })

public class SubjectCtl extends BaseCtl{
	
	@Override
	protected void preload(HttpServletRequest request) {
	    CourseModel model = new CourseModel();
	    try {
	        List<CourseBean> courseList = model.list();
	        request.setAttribute("courseList", courseList);
	        System.out.println("COURSE LIST IN PRELOAD = " + courseList);
	    } catch (ApplicationException e) {
	        e.printStackTrace();
	    }
	}
	
	    @Override
	    protected boolean validate(HttpServletRequest request) {
	        boolean pass = true;

	        if (DataValidator.isNull(request.getParameter("name"))) {
	            request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
	            pass = false;
	        } else if (!DataValidator.isName(request.getParameter("name"))) {
	            request.setAttribute("name", "Invalid Subject Name");
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("description"))) {
	            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("courseId"))) {
	            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Id"));
	            pass = false;
	        }
	        if (DataValidator.isNull(request.getParameter("collegeName"))) {
				request.setAttribute("collegeName", PropertyReader.getValue("error.require", "College Name"));
				pass = false;
			}

	        return pass;
	    }

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {

	        SubjectBean bean = new SubjectBean();

	        bean.setId(DataUtility.getLong(request.getParameter("id")));
	        bean.setName(DataUtility.getString(request.getParameter("name")));
	        bean.setDescription(DataUtility.getString(request.getParameter("description")));
	        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
	        bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
	        
	        populateDTO(bean, request);

	        return bean;
	    }

	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String op = DataUtility.getString(request.getParameter("operation"));
	        long id = DataUtility.getLong(request.getParameter("id"));

	        SubjectModel model = new SubjectModel();

	        if (id > 0 || op != null) {
	            try {
	                SubjectBean bean = model.findByPk(id);
	                ServletUtility.setBean(bean, request);
	            } catch (ApplicationException e) {
	                e.printStackTrace();
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }

	        ServletUtility.forward(getView(), request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String op = DataUtility.getString(request.getParameter("operation"));

	        SubjectModel model = new SubjectModel();
	        SubjectBean bean = (SubjectBean) populateBean(request);

	        if (OP_SAVE.equalsIgnoreCase(op)) {

	            if (!validate(request)) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.forward(getView(), request, response);
	                return;
	            }

	            try {
	                model.add(bean);
	                ServletUtility.setSuccessMessage("Subject added successfully!", request);
	                ServletUtility.setBean(bean, request);

	            } catch (DuplicateRecordException e) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("Subject already exists!", request);

	            } catch (ApplicationException e) {
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }

	        else if (OP_UPDATE.equalsIgnoreCase(op)) {

	            if (!validate(request)) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.forward(getView(), request, response);
	                return;
	            }

	            try {
	                model.update(bean);
	                ServletUtility.setSuccessMessage("Subject updated successfully!", request);
	                ServletUtility.setBean(bean, request);

	            } catch (ApplicationException e) {
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DuplicateRecordException e) {
					e.printStackTrace();
				}
	        }

	        else if (OP_DELETE.equalsIgnoreCase(op)) {

	            try {
	                model.delete(bean);
	                ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
	                return;
	            } catch (ApplicationException e) {
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }

	        else if (OP_CANCEL.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
	            return;
	        }

	        else if (OP_RESET.equalsIgnoreCase(op)) {
	            ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
	            return;
	        }

	        ServletUtility.forward(getView(), request, response);
	    }


	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

}
