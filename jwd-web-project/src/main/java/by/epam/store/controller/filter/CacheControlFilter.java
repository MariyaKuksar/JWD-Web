package by.epam.store.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/*" })
public class CacheControlFilter implements Filter{
	public static final String HTTP_HEADER_CACHE_CONTROL = "Cache-Control";
	public static final String CACHING_INSTRUCTIONS = "no-store, no-cache, must-revalidate";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader(HTTP_HEADER_CACHE_CONTROL, CACHING_INSTRUCTIONS);
		chain.doFilter(request, response);	
	}
}
