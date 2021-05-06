package by.epam.store.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class RequestUtil {
	private static final Logger logger = LogManager.getLogger();
	private static final String PATH = "C:\\Users\\User\\Desktop\\img\\";

	private RequestUtil() {
	}

	public static Map<String, String> getParametersFromMultipartRequest(HttpServletRequest request)
			throws IOException, ServletException {
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, String> parameters = new HashMap<>();
		for (String name : Collections.list(parameterNames)) {
			parameters.put(name, request.getParameter(name));
		}
		logger.debug(parameters);
		for (Part part : request.getParts()) {
			if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
				String imageName = FileUtil.generateName(part.getSubmittedFileName());
				part.write(PATH + imageName);
				parameters.put(ParameterAndAttribute.IMAGE_NAME, imageName);
				logger.debug(imageName);
			}
		}
		return parameters;
	}

	public static Map<String, String> getRequestParameters(HttpServletRequest request) {
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, String> parameters = new HashMap<>();
		for (String name : Collections.list(parameterNames)) {
			parameters.put(name, request.getParameter(name));
		}
		logger.debug(parameters);
		return parameters;
	}
}