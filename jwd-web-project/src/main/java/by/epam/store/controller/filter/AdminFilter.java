package by.epam.store.controller.filter;

import java.io.IOException;
import java.util.Arrays;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.model.entity.UserRole;
import by.epam.store.util.MessageKey;

@WebFilter(urlPatterns = { "/jsp/admin/*" })
public class AdminFilter implements Filter {
	private static final Logger logger = LogManager.getLogger();
	private static final String SLASH = "/";

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("AdminFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession(true);
		if (session.getAttribute(ParameterAndAttribute.ROLE) != UserRole.ADMIN) {
			((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + SLASH + PagePath.LOGIN);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE_LIST,
					Arrays.asList(MessageKey.ERROR_ACCESS_MESSAGE));
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
