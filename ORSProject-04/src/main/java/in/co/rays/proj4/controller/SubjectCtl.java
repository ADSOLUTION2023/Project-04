package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Controller class for handling Subject operations such as add, update, preload,
 * validation and form submissions.
 * 
 * This servlet interacts with {@link SubjectModel} and {@link SubjectBean} to
 * process Subject-related actions.
 * 
 * @author Amit Chandsarkar
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {

    /**
     * Preloads the list of Courses to be displayed in the Subject form.
     * 
     * @param request the HttpServletRequest object
     */
    @Override
    protected void preload(HttpServletRequest request) {

        CourseModel model = new CourseModel();

        try {
            List courseList = model.list();
            request.setAttribute("courseList", courseList);
        } catch (ApplicationException e) {

            e.printStackTrace();
        }
    }

    /**
     * Validates user inputs from the Subject form.
     * 
     * @param request the HttpServletRequest object
     * @return true if validation passes, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a SubjectBean with form data.
     * 
     * @param request the HttpServletRequest object
     * @return populated SubjectBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        SubjectBean bean = new SubjectBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setSubjectName(DataUtility.getString(request.getParameter("name")));
        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles HTTP GET requests for Subject operations (load for update).
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        SubjectModel model = new SubjectModel();

        if (id > 0) {
            try {

                SubjectBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (Exception e) {
                ServletUtility.handleException(e, request, response);
                return;

            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests for save, update, cancel, and reset operations.
     * 
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        SubjectModel model = new SubjectModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            SubjectBean bean = (SubjectBean) populateBean(request);
            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Subject added successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject Name already exists", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        } else if (OP_UPDATE.equalsIgnoreCase(op)) {
            SubjectBean bean = (SubjectBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Subject updated successfully", request);
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Subject Name Alredy Exist", request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
            return;
        }

        else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
            return;
        }
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for Subject.
     * 
     * @return Subject view JSP path
     */
    @Override
    protected String getView() {

        return ORSView.SUBJECT_VIEW;
    }

}