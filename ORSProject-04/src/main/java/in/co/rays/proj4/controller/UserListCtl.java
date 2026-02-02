package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Controller class to handle User list operations such as search, pagination,
 * and deletion.
 * 
 * Displays list of users and processes list-related actions.
 * 
 * @author Amit Chandsarkar
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	
	private static Logger log = Logger.getLogger(UserListCtl.class);

    /**
     * Loads preload lists such as Role list to populate dropdown filters.
     *
     * @param request the HttpServletRequest
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	
    	log.debug("UserListCtl Method preload started");
    	
        RoleModel rolemodel = new RoleModel();

        try {
            List roleList = rolemodel.list();
            request.setAttribute("roleList", roleList);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        log.debug("UserListCtl Method preload ended");
    }

    /**
     * Populates a UserBean with request parameters for search filters.
     *
     * @param request the HttpServletRequest
     * @return populated UserBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	
    	log.debug("UserListCtl Method populate started");

        UserBean bean = new UserBean();

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));

        log.debug("UserListCtl Method populate ended");
        return bean;
    }

    /**
     * Handles GET request to display the initial User list with pagination.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("UserListCtl Method doGet started");

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

        try {
            List<UserBean> list = model.search(bean, pageNo, pageSize);
            List<UserBean> next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("no record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            return;
        }
        log.debug("UserListCtl Method doGet ended");
    }

    /**
     * Handles POST request for operations:
     * Search, Next, Previous, New, Delete, Reset, Back.
     *
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	log.debug("UserListCtl Method doPost started");

        List list = null;
        List next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

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
                ServletUtility.redirect(ORSView.USER_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    UserBean deletebean = new UserBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("User deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
                return;

            } else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
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
            return;
        }
        
        log.debug("UserListCtl Method doPost ended");
    }

    /**
     * Returns the view for User List screen.
     *
     * @return USER_LIST_VIEW constant
     */
    @Override
    protected String getView() {
        return ORSView.USER_LIST_VIEW;
    }
}