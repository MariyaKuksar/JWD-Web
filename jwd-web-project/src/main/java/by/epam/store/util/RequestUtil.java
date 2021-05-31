package by.epam.store.util;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import by.epam.store.controller.command.ParameterAndAttribute;

public final class RequestUtil {
	private static final String BUNDLE_NAME = "path";
	private static final String PATH_IMG = "path.img";

	private RequestUtil() {
	}

	public static Map<String, String> getParametersFromMultipartRequest(HttpServletRequest request)
			throws IOException, ServletException {
		Enumeration<String> parameterNames = request.getParameterNames();
		Map<String, String> parameters = new HashMap<>();
		for (String name : Collections.list(parameterNames)) {
			parameters.put(name, request.getParameter(name));
		}
		for (Part part : request.getParts()) {
			if (part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
				String imageName = FileUtil.generateName(part.getSubmittedFileName());
				String path = ResourceBundle.getBundle(BUNDLE_NAME).getString(PATH_IMG);
				part.write(path + imageName);
				parameters.put(ParameterAndAttribute.IMAGE_NAME, imageName);
				parameters.put(ParameterAndAttribute.PATH, path);
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
		return parameters;
	}
}