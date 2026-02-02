package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarkSheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MarkSheetModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * GetMarksheetCtl Servlet.
 * <p>
 * This controller handles requests for searching a marksheet by roll number.
 * It validates the input, interacts with the MarksheetModel, and forwards
 * results or error messages to the appropriate view.
 * </p>
 * 
 * Author: Amit Chandsarkar
 * @version 1.0
 */

@WebServlet(name = "GetMarkSheetCtl", urlPatterns = { "/ctl/GetMarkSheetCtl" })
public class GetMarkSheetCtl extends BaseCtl {

    /**
     * Validates the input parameters from the request.
     * Checks if the roll number is provided.
     *
     * @param request HttpServletRequest
     * @return boolean true if input is valid, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("rollNo"))) {
            request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a MarksheetBean from the request parameters.
     *
     * @param request HttpServletRequest
     * @return BaseBean containing roll number
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        MarkSheetBean bean = new MarkSheetBean();
        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
        return bean;
    }

    /**
     * Handles HTTP GET requests.
     * Forwards to the get marksheet view.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Performs search for the marksheet by roll number.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        MarkSheetModel model = new MarkSheetModel();
        MarkSheetBean bean = (MarkSheetBean) populateBean(request);

        if (OP_GO.equalsIgnoreCase(op)) {
            try {
                bean = model.findByRollNo(bean.getRollNo());
                if (bean != null) {
                    ServletUtility.setBean(bean, request);
                } else {
                    ServletUtility.setErrorMessage("RollNo Does Not exist", request);
                }
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the view page for this controller.
     *
     * @return String view page
     */
    @Override
    protected String getView() {
        return ORSView.GET_MARKSHEET_VIEW;
    }
}