package in.co.rays.proj4.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.proj4.util.ServletUtility;

/**
 * FrontController Filter.
 * <p>
 * This filter intercepts all requests to "/ctl/*" and "/doc/*" URLs. It checks
 * if a user session exists and prevents access if the session is expired.
 * </p>
 * 
 * @author Amit Chandsarkar
 * @version 1.0
 */

@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })
public class FrontController implements Filter {

	/**
	 * Performs filtering for incoming requests. Checks if the user session exists,
	 * otherwise sets an error message.
	 * 
	 * @param req   ServletRequest
	 * @param resp  ServletResponse
	 * @param chain FilterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		// System.out.println("FrontController DoFilter");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		HttpSession session = request.getSession();

		String uri = request.getRequestURI();
		request.setAttribute("uri", uri);

		if (session.getAttribute("user") == null) {
			ServletUtility.setErrorMessage("Your Session has been Expired... Please Login Again", request);
			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
			return;
		} else {
			chain.doFilter(req, resp);
		}
	}

	/**
	 * Initializes the filter.
	 * 
	 * @param conf FilterConfig
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig conf) throws ServletException {
		// No specific initialization required
	}

	/**
	 * Cleans up resources when the filter is destroyed.
	 */
	@Override
	public void destroy() {
		System.out.println("Hello Amit sir.....");
	}
}