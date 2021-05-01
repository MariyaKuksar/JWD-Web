package by.epam.store.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.CommandProvider;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.Router;
import by.epam.store.util.ParameterAndAttribute;

@WebServlet(name = "upload", urlPatterns = { "/upload" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {
	private static final Logger logger = LogManager.getLogger();
	private static final String FORMAT_FILE_NAME = "jpg";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter(ParameterAndAttribute.URL);
		File file = new File(url);
		BufferedImage bufferedImage = ImageIO.read(file);
		try (OutputStream outputStream = response.getOutputStream()) {
			ImageIO.write(bufferedImage, FORMAT_FILE_NAME, outputStream);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandName = request.getParameter(ParameterAndAttribute.COMMAND);
		logger.debug("command = " + commandName);
		if (commandName != null) {
			Command command = CommandProvider.defineCommand(commandName);
			Router router = command.execute(request);
			switch (router.getRouteType()) {
			case REDIRECT:
				logger.debug("redirect " + router.getPagePath());
				response.sendRedirect(router.getPagePath());
				break;
			case FORWARD:
				logger.debug("forward " + router.getPagePath());
				RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
				dispatcher.forward(request, response);
				break;
			default:
				logger.error("incorrect route type");
				response.sendRedirect(PagePath.ERROR);
				break;
			}
		} else {
			logger.error("null command");
			response.sendRedirect(PagePath.ERROR);
		}
	}
}
