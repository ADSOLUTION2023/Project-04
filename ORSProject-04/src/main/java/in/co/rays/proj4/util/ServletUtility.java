package in.co.rays.proj4.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.controller.BaseCtl;
import in.co.rays.proj4.controller.ORSView;

/**
 * <p>
 * ServletUtility is a helper class used in Servlets and Controllers.  
 * It provides common reusable functions for:
 * </p>
 *
 * <ul>
 *     <li>Forwarding and redirecting requests</li>
 *     <li>Setting and getting error/success messages</li>
 *     <li>Managing beans, lists, and parameters in the request scope</li>
 *     <li>Handling exceptions and redirecting to error pages</li>
 *     <li>Supporting pagination (pageNo, pageSize)</li>
 * </ul>
 *
 * <p>
 * These utilities reduce code duplication across controllers and ensure
 * consistent responses throughout the ORS application.
 * </p>
 *
 * @author 
 * @version 1.0
 */
public class ServletUtility {

    /**
     * Forwards the request to the specified JSP page.
     *
     * @param page     the target JSP page
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException
     * @throws ServletException
     */
    public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

    /**
     * Redirects the response to the given page URL.
     *
     * @param page     URL to redirect
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException
     * @throws ServletException
     */
    public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.sendRedirect(page);
    }

    /**
     * Retrieves an error message stored in the request scope by its key.
     *
     * @param property message key
     * @param request  HttpServletRequest object
     * @return error message or empty string if missing
     */
    public static String getErrorMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        return (val == null) ? "" : val;
    }

    /**
     * Retrieves a general message stored in the request scope.
     *
     * @param property message key
     * @param request  HttpServletRequest object
     * @return message or empty string if missing
     */
    public static String getMessage(String property, HttpServletRequest request) {
        String val = (String) request.getAttribute(property);
        return (val == null) ? "" : val;
    }

    /**
     * Stores an error message under the default error key.
     *
     * @param msg     the error message
     * @param request HttpServletRequest object
     */
    public static void setErrorMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_ERROR, msg);
    }

    /**
     * Retrieves the default error message from request scope.
     *
     * @param request HttpServletRequest object
     * @return error message or empty string
     */
    public static String getErrorMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
        return (val == null) ? "" : val;
    }

    /**
     * Stores a success message under the default success key.
     *
     * @param msg     the success message
     * @param request HttpServletRequest object
     */
    public static void setSuccessMessage(String msg, HttpServletRequest request) {
        request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
    }

    /**
     * Retrieves the default success message from request scope.
     *
     * @param request HttpServletRequest object
     * @return success message or empty string
     */
    public static String getSuccessMessage(HttpServletRequest request) {
        String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
        return (val == null) ? "" : val;
    }

    /**
     * Stores a bean object into the request scope.
     *
     * @param bean    the bean to store
     * @param request HttpServletRequest object
     */
    public static void setBean(BaseBean bean, HttpServletRequest request) {
        request.setAttribute("bean", bean);
    }

    /**
     * Retrieves the bean stored in the request scope.
     *
     * @param request HttpServletRequest object
     * @return bean object or null
     */
    public static BaseBean getBean(HttpServletRequest request) {
        return (BaseBean) request.getAttribute("bean");
    }

    /**
     * Retrieves a request parameter by name.
     *
     * @param property parameter name
     * @param request  HttpServletRequest object
     * @return parameter value or empty string
     */
    public static String getParameter(String property, HttpServletRequest request) {
        String val = request.getParameter(property);
        return (val == null) ? "" : val;
    }

    /**
     * Stores a list in the request scope.
     *
     * @param list    List of objects (beans)
     * @param request HttpServletRequest object
     */
    public static void setList(List list, HttpServletRequest request) {
        request.setAttribute("list", list);
    }

    /**
     * Retrieves a list from the request scope.
     *
     * @param request HttpServletRequest object
     * @return list object
     */
    public static List getList(HttpServletRequest request) {
        return (List) request.getAttribute("list");
    }

    /**
     * Stores a page number for pagination.
     *
     * @param pageNo  current page number
     * @param request HttpServletRequest object
     */
    public static void setPageNo(int pageNo, HttpServletRequest request) {
        request.setAttribute("pageNo", pageNo);
    }

    /**
     * Retrieves the stored page number.
     *
     * @param request HttpServletRequest object
     * @return page number
     */
    public static int getPageNo(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageNo");
    }

    /**
     * Stores page size for pagination.
     *
     * @param pageSize number of records per page
     * @param request  HttpServletRequest object
     */
    public static void setPageSize(int pageSize, HttpServletRequest request) {
        request.setAttribute("pageSize", pageSize);
    }

    /**
     * Retrieves page size from request scope.
     *
     * @param request HttpServletRequest object
     * @return page size
     */
    public static int getPageSize(HttpServletRequest request) {
        return (Integer) request.getAttribute("pageSize");
    }

    /**
     * Handles exceptions by storing the exception and redirecting to an error page.
     *
     * @param e        the exception thrown
     * @param request  HttpServletRequest object
     * @param response HttpServletResponse object
     * @throws IOException
     * @throws ServletException
     */
    public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("exception", e);
        response.sendRedirect(ORSView.ERROR_CTL);
    }
}
