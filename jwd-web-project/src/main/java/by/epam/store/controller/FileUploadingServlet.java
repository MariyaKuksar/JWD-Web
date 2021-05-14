package by.epam.store.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ResourceBundle;

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
	private static final String BUNDLE_NAME = "path";
	private static final String PATH_IMG = "path.img";
	private static final String FORMAT_FILE_NAME = "jpg";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String imageName = request.getParameter(ParameterAndAttribute.IMAGE_NAME);
		String path = ResourceBundle.getBundle(BUNDLE_NAME).getString(PATH_IMG);
		File file = new File(path + imageName);
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
	}
}
