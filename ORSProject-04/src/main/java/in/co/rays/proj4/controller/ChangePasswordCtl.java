 package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * ChangePasswordCtl is responsible for handling password change operations for
 * logged-in users. It validates old password, new password rules, matching of
 * confirm password, and interacts with UserModel to update the password.
 * 
 * <p>
 * This controller prevents users from reusing their old password and enforces
 * password complexity rules (uppercase, lowercase, digit, special character).
 * </p>
 * 
 * @author Amit Chandsarkar
 * @version 1.0
 */
@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl {

	/** Operation constant for redirecting to My Profile page */
	public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";

	/**
	 * Validates user input for password change. Ensures:
	 * <ul>
	 * <li>Old password is required</li>
	 * <li>New password is required</li>
	 * <li>New password meets complexity and length rules</li>
	 * <li>Confirm password must match new password</li>
	 * </ul>
	 * 
	 * @param request HttpServletRequest object
	 * @return true if validation is successful, else false
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;
		String op = request.getParameter("operation");

		// Skip validation for profile change request
		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			return pass;
		}

		// Validate old password
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		} else if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Old and New passwords should be different");
			pass = false;
		}

		// Validate new password
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Password should be 8 to 12 characters");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

		// Validate confirm password
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New and confirm passwords not matched");
			pass = false;
		}

		return pass;
	}

	/**
	 * Populates a UserBean with password fields from the request.
	 * 
	 * @param request HttpServletRequest
	 * @return BaseBean containing populated UserBean data
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();

		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		populateDTO(bean, request);

		return bean;
	}

	/**
	 * Handles GET requests and forwards to the Change Password view.
	 * 
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Handles POST operations for:
	 * 
	 * <ul>
	 * <li>Saving new password</li>
	 * <li>Navigating to My Profile page</li>
	 * </ul>
	 * 
	 * <p>
	 * It verifies the old password, updates the password if valid, refreshes the
	 * session, and displays success or error messages accordingly.
	 * </p>
	 * 
	 * @param request  HttpServletRequest containing form data
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		String newPassword = request.getParameter("newPassword");

		UserBean bean = (UserBean) populateBean(request);
		UserModel model = new UserModel();

		HttpSession session = request.getSession(true);
		UserBean user = (UserBean) session.getAttribute("user");
		long id = user.getId();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			try {
				boolean flag = model.changePassword(id, bean.getPassword(), newPassword);

				if (flag) {
					// Refresh session user details after password update
					bean = model.findByLogin(user.getLogin());
					session.setAttribute("user", bean);

					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully", request);
				}

			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Old Password is Invalid", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}

		ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
	}

	/**
	 * Returns the view page for the Change Password screen.
	 * 
	 * @return String JSP view for Change Password
	 */
	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}
}