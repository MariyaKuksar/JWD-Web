package by.epam.store.controller.command;

import java.util.EnumMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.impl.ChangeLocaleCommand;
import by.epam.store.controller.command.impl.ConfirmRegistrationCommand;
import by.epam.store.controller.command.impl.DefaultCommand;
import by.epam.store.controller.command.impl.FindUsersByNameCommand;
import by.epam.store.controller.command.impl.GoToMainPageCommand;
import by.epam.store.controller.command.impl.SignInCommand;
import by.epam.store.controller.command.impl.SignOutCommand;
import by.epam.store.controller.command.impl.SignUpCommand;
import by.epam.store.controller.command.impl.FindAllUsersCommand;

public final class CommandProvider {
	private static final Logger logger = LogManager.getLogger();
	private static Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

	static {
		commands.put(CommandType.FIND_ALL_USERS, new FindAllUsersCommand());
		commands.put(CommandType.FIND_USERS_BY_NAME, new FindUsersByNameCommand());
		commands.put(CommandType.SIGN_IN, new SignInCommand());
		commands.put(CommandType.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
		commands.put(CommandType.EN, new ChangeLocaleCommand());
		commands.put(CommandType.RU, new ChangeLocaleCommand());
		commands.put(CommandType.SIGN_UP, new SignUpCommand());
		commands.put(CommandType.DEFAULT, new DefaultCommand());
		commands.put(CommandType.CONFIRM_REGISTRATION, new ConfirmRegistrationCommand());
		commands.put(CommandType.SIGN_OUT, new SignOutCommand());
	}

	private CommandProvider() {
	}

	public static Command defineCommand(String commandName) {
		CommandType commandType;
		try {
			commandType = CommandType.valueOf(commandName.toUpperCase());
		} catch (IllegalArgumentException e) {
			logger.error("no such command name");
			commandType = CommandType.DEFAULT;
		}
		return commands.get(commandType);
	}
}