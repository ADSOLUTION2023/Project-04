package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimetableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Controller class to handle operations related to Timetable List including
 * Search, Pagination, Delete, and Navigation.
 * 
 * This servlet loads subject and course lists, displays timetable records,
 * and provides functionality to search, reset, navigate (next/previous), and
 * delete timetable entries.
 * 
 * @author Amit Chandsarkar
 * @version 1.0
 */
@WebServlet(name = "TimetableListCtl", urlPatterns = { "/ctl/TimetableListCtl" })
public class TimeTableListCtl extends BaseCtl {

    /**
     * Preloads reference data such as Subject List and Course List to be
     * displayed on the Timetable List screen.
     * 
     * @param request the HttpServletRequest containing client request data
     */
    @Override
    protected void preload(HttpServletRequest request) {
        SubjectModel subjectModel = new SubjectModel();
        CourseModel courseModel = new CourseModel();

        try {
            List subjectList = subjectModel.list();
            request.setAttribute("subjectList", subjectList);

            List courseList = courseModel.list();
            request.setAttribute("courseList", courseList);

        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates TimeTableBean with form input values.
     * 
     * @param request the incoming client request
     * @return populated TimeTableBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        TimeTableBean bean = new TimeTableBean();

        bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
        bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
        bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

        return bean;
    }

    /**
     * Handles HTTP GET requests. Fetches and displays the first page of
     * Timetable records.
     * 
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        TimeTableBean bean = (TimeTableBean) populateBean(request);
        TimetableModel model = new TimetableModel();

        try {
            List<TimeTableBean> list = model.search(bean, pageNo, pageSize);
            List<TimeTableBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Handles HTTP POST requests used for Search, Pagination, Delete, Reset,
     * and Navigation operations.
     * 
     * @param request client request containing form data
     * @param response server response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        TimeTableBean bean = (TimeTableBean) populateBean(request);
        TimetableModel model = new TimetableModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");

        try {
            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    TimeTableBean deleteBean = new TimeTableBean();
                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getInt(id));
                        model.delete(deleteBean);
                        ServletUtility.setSuccessMessage("Data is deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Returns the view for Timetable List.
     * 
     * @return path constant of the JSP view
     */
    @Override
    protected String getView() {
        return ORSView.TIMETABLE_LIST_VIEW;
    }
}