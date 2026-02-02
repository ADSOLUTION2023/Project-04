package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * LoginCtl Servlet.
 * <p>
 * This controller handles user login, logout, and redirects to registration.
 * It validates login credentials, authenticates the user, and manages the session.
 * </p>
 * 
 * Author: Amit Chandsarkar
 * @version 1.0
 */

@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

    public static final String OP_SIGN_IN = "Sign In";
    public static final String OP_SIGN_UP = "Sign Up";

    /**
     * Validates the login input fields.
     * Checks for null or invalid email for login and empty password.
     *
     * @param request HttpServletRequest
     * @return boolean true if valid, false otherwise
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        String op = request.getParameter("operation");

        if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
            return pass;
        }

        if (DataValidator.isNull(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("login"))) {
            request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populates a UserBean from request parameters.
     *
     * @param request HttpServletRequest
     * @return BaseBean containing login and password
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        UserBean bean = new UserBean();

        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setPassword(DataUtility.getString(request.getParameter("password")));

        return bean;
    }

    /**
     * Handles HTTP GET requests.
     * Handles logout operation and forwards to the login view.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_LOG_OUT.equals(op)) {
            session.invalidate();
            ServletUtility.setSuccessMessage("Logout Successful!", request);
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles HTTP POST requests.
     * Performs login, authentication, and redirects to appropriate views.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserModel model = new UserModel();
        RoleModel role = new RoleModel();
        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_SIGN_IN.equalsIgnoreCase(op)) {
            UserBean bean = (UserBean) populateBean(request);

            try {
                bean = model.authenticate(bean.getLogin(), bean.getPassword());

                if (bean != null) {
                    session.setAttribute("user", bean);
                    
                    RoleBean rolebean = role.findByPk(bean.getRoleId());
                    if (rolebean != null) {
                        session.setAttribute("role", rolebean.getName());
                    }

                    String uri = (String) request.getParameter("uri");
					if (uri == null || "null".equalsIgnoreCase(uri)) {
						//System.out.println("LOGIN SUCCESS");
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(uri, request, response);
						return;
					}

                } else {
                    bean = (UserBean) populateBean(request);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
                }

            } catch (ApplicationException e) {
                e.printStackTrace();
                return;
            }

        } else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns the login view page.
     *
     * @return String view page
     */
    @Override
    protected String getView() {
        return ORSView.LOGIN_VIEW;
    }
}