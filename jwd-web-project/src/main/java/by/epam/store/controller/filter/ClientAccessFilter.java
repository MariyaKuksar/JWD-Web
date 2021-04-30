package by.epam.store.controller.filter;

import java.io.File;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.PagePath;
import by.epam.store.model.entity.UserRole;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;

@WebFilter(urlPatterns = { "/jsp/client/*" })
public class ClientAccessFilter implements Filter {
	private static final Logger logger = LogManager.getLogger();

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.debug("ClientAccessFilter");
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);
		if (session.getAttribute(ParameterAndAttribute.ROLE) != UserRole.CLIENT) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + File.separator + PagePath.GO_TO_MAIN_PAGE);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_ACCESS_MESSAGE);
			return;
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
