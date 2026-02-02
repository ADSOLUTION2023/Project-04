package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.MarkSheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MarkSheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;


/**
* MarksheetMeritListCtl Servlet.
* <p>
* This controller handles displaying the merit list of marksheets.
* It supports pagination and back navigation to the welcome page.
* </p>
* 
* Author: Amit Chandsarkar
* @version 1.0
*/


@WebServlet(name = "MarkSheetMeritListCtl", urlPatterns = { "/ctl/MarkSheetMeritListCtl" })
public class MarkSheetMeritListCtl extends BaseCtl {

    /**
     * Handles HTTP GET requests.
     * Loads the merit list of marksheets with pagination.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        MarkSheetModel model = new MarkSheetModel();

        try {
            List<MarkSheetBean> list = model.getMeritList(pageNo, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    /**
     * Handles HTTP POST requests.
     * Supports the Back operation to return to the welcome page.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_BACK.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
        }
    }

    /**
     * Returns the view for the merit list page.
     *
     * @return String view page
     */
    @Override
    protected String getView() {
        return ORSView.MARKSHEET_MERIT_LIST_VIEW;
    }
}