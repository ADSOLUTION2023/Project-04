 package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * CollegeCtl handles operations related to adding, updating, and validating
 * College records. It processes both GET and POST requests, validates inputs,
 * populates CollegeBean, interacts with CollegeModel, and forwards or redirects
 * to the appropriate view pages.
 * 
 * <p>
 * Supported operations:
 * <ul>
 * <li>Save College</li>
 * <li>Update College</li>
 * <li>Cancel to list page</li>
 * <li>Reset form</li>
 * </ul>
 * </p>
 * 
 * @author Amit Chandsarkar
 * @version 1.0
 */
@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {

    /**
     * Validates College input fields. Ensures:
     * <ul>
     * <li>Name is required and alphabetic</li>
     * <li>Address, State, City are required</li>
     * <li>Phone number is required, must be 10 digits, and valid</li>
     * </ul>
     *
     * @param request HttpServletRequest containing user input data
     * @return true if validation succeeds, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Validate Name
        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
            pass = false;
        } else if (!DataValidator.isName(request.getParameter("name"))) {
            request.setAttribute("name", "Invalid Name");
            pass = false;
        }

        // Validate Address
        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }

        // Validate State
        if (DataValidator.isNull(request.getParameter("state"))) {
            request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
            pass = false;
        }

        // Validate City
        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
            pass = false;
        }

        // Validate Phone No
        if (DataValidator.isNull(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        } else if (!DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", "Phone No must have 10 digits");
            pass = false;
        } else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", "Invalid Phone No");
            pass = false;
        }

        return pass;
    }

    /**
     * Populates CollegeBean using request parameters.
     * 
     * @param request HttpServletRequest containing form input
     * @return BaseBean populated with College details
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        CollegeBean bean = new CollegeBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setAddress(DataUtility.getString(request.getParameter("address")));
        bean.setState(DataUtility.getString(request.getParameter("state")));
        bean.setCity(DataUtility.getString(request.getParameter("city")));
        bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request. Loads a College record in edit mode if ID is provided.
     * Otherwise, opens the blank form.
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        CollegeModel model = new CollegeModel();

        if (id > 0) {
            try {
                CollegeBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST operations:
     * <ul>
     * <li><b>Save</b>: Add new College</li>
     * <li><b>Update</b>: Update existing College</li>
     * <li><b>Cancel</b>: Redirect to College List</li>
     * <li><b>Reset</b>: Reload empty College form</li>
     * </ul>
     *
     * @param request  HttpServletRequest containing form data
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        CollegeModel model = new CollegeModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            CollegeBean bean = (CollegeBean) populateBean(request);

            try {
                long pk = model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("College added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }
        }

        else if (OP_UPDATE.equalsIgnoreCase(op)) {

            CollegeBean bean = (CollegeBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("College updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
            return;
        }

        else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the JSP page for College form view.
     * 
     * @return view page path
     */
    @Override
    protected String getView() {
        return ORSView.COLLEGE_VIEW;
    }

}