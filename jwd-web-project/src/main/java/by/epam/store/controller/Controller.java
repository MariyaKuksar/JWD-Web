package by.epam.store.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.CommandProvider;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.model.connection.ConnectionPool;
import by.epam.store.model.connection.ConnectionPoolException;

@WebServlet(name = "controller", urlPatterns = { "/controller" })
public class Controller extends HttpServlet {
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		logger.debug("doGet");
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("doPost");
		processRequest(req, resp);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
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

	@Override
	public void destroy() {
		super.destroy();
		try {
			ConnectionPool.getInstance().destroyPool();
		} catch (ConnectionPoolException e) {
			logger.fatal("error closing connection", e);
			throw new RuntimeException("error closing connection", e);
		}
	}
}