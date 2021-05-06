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

import by.epam.store.controller.command.PagePath;
import by.epam.store.model.entity.UserRole;
import by.epam.store.util.MessageKey;
import by.epam.store.util.ParameterAndAttribute;

@WebFilter(urlPatterns = { "/jsp/admin/*" })
public class AdminAccessFilter implements Filter {

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);
		if (session.getAttribute(ParameterAndAttribute.ROLE) != UserRole.ADMIN) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + File.separator + PagePath.GO_TO_MAIN_PAGE);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_ACCESS_MESSAGE);
			return;
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}
}
