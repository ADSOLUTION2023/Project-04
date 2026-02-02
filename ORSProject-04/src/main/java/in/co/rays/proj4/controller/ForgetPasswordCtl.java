package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
* ForgetPasswordCtl Servlet Controller.
* <p>
* Handles the "Forgot Password" functionality.
* Validates user email and triggers sending of password to the registered email.
* </p>
* 
* @author Amit Chandsarkar
* @version 1.0
*/

@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

    /**
     * Validates the login input from the user.
     * 
     * @param request HTTP request
     * @return true if input is valid, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a UserBean from request parameters.
     * 
     * @param request HTTP request
     * @return populated UserBean
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        UserBean bean = new UserBean();
        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        return bean;
    }

    /**
     * Handles GET requests to forward to the Forget Password view.
     * 
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST requests for the Forget Password operation.
     * Validates input and triggers password recovery via email.
     * 
     * @param request  HTTP request
     * @param response HTTP response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        UserBean bean = (UserBean) populateBean(request);
        UserModel model = new UserModel();

        if (OP_GO.equalsIgnoreCase(op)) {
            try {
                boolean flag = model.forgetPassword(bean.getLogin());
                if (flag) {
                    ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
                }
            } catch (RecordNotFoundException e) {
            	ServletUtility.setErrorMessage("Email ID does not exists..!!", request);
                //ServletUtility.setErrorMessage(e.getMessage(), request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.setErrorMessage("Please check your internet connection..!!", request);
                
            }
            ServletUtility.forward(getView(), request, response);
        }
    }

    /**
     * Returns the view for the Forget Password page.
     * 
     * @return path of FORGET_PASSWORD_VIEW JSP
     */
    @Override
    protected String getView() {
        return ORSView.FORGET_PASSWORD_VIEW;
    }
}