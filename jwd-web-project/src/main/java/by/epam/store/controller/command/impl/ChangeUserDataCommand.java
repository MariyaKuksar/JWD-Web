package by.epam.store.controller.command.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.ParameterAndAttribute;
import by.epam.store.controller.command.Router;
import by.epam.store.controller.command.Router.RouteType;
import by.epam.store.entity.UserRole;
import by.epam.store.model.service.InvalidDataException;
import by.epam.store.model.service.ServiceException;
import by.epam.store.model.service.ServiceFactory;
import by.epam.store.model.service.UserService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.UserControl;

/**
 * The command is responsible for changing user data
 * 
 * @author Mariya Kuksar
 * @see Command
 */
public class ChangeUserDataCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request) {
		Router router;
		if (!UserControl.isLoggedInUser(request) || !UserControl.isValidForRole(request, UserRole.CLIENT)) {
			router = new Router(PagePath.GO_TO_MAIN_PAGE, RouteType.REDIRECT);
			return router;
		}
		HttpSession session = request.getSession(true);
		UserService userService = ServiceFactory.getInstance().getUserService();
		Map<String, String> userInfo = RequestUtil.getRequestParameters(request);
		String currentLogin = (String) session.getAttribute(ParameterAndAttribute.LOGIN);
		Long userId = (Long) session.getAttribute(ParameterAndAttribute.USER_ID);
		userInfo.put(ParameterAndAttribute.CURRENT_LOGIN, currentLogin);
		userInfo.put(ParameterAndAttribute.USER_ID, String.valueOf(userId));
		try {
			if (userService.changeUserData(userInfo)) {
				session.setAttribute(ParameterAndAttribute.INFO_MESSAGE, MessageKey.INFO_SAVED_SUCCESSFULLY_MESSAGE);
			} else {
				session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, MessageKey.ERROR_INCORRECT_PASSWORD_MESSAGE);
			}
			router = new Router(PagePath.GO_TO_PROFILE_PAGE, RouteType.REDIRECT);
		} catch (InvalidDataException e) {
			logger.error("invalid data", e);
			session.setAttribute(ParameterAndAttribute.ERROR_MESSAGE, e.getErrorDescription());
			router = new Router(PagePath.GO_TO_PROFILE_PAGE, RouteType.REDIRECT);
		} catch (ServiceException e) {
			logger.error("user data changing error", e);
			router = new Router(PagePath.ERROR, RouteType.REDIRECT);
		}
		return router;
	}

}
