package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "CourseCtl", urlPatterns = { "/CourseCtl" })
public class CourseCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Course Name");
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("duration"))) {
            request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CourseBean bean = new CourseBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        bean.setDuration(DataUtility.getString(request.getParameter("duration")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        CourseModel model = new CourseModel();

        if (id > 0 || op != null) {
            try {
                CourseBean bean = model.findByPk(id);
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

        CourseModel model = new CourseModel();
        CourseBean bean = (CourseBean) populateBean(request);

        if (OP_SAVE.equalsIgnoreCase(op)) {

            if (!validate(request)) {
                ServletUtility.setBean(bean, request);
                ServletUtility.forward(getView(), request, response);
                return;
            }

            try {
                model.add(bean);
                ServletUtility.setSuccessMessage("Course added successfully!", request);
                ServletUtility.setBean(bean, request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Course already exists!", request);

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
                ServletUtility.setSuccessMessage("Course updated successfully!", request);
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
                ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
                return;
            } catch (ApplicationException e) {
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            return;
        }

        else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.COURSE_VIEW;
    }
}
