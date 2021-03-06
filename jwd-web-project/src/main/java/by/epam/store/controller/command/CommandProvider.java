package by.epam.store.controller.command;

import java.util.EnumMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.store.controller.command.impl.AcceptProductsCommand;
import by.epam.store.controller.command.impl.AddProductToBasketCommand;
import by.epam.store.controller.command.impl.AddProductToCatalogCommand;
import by.epam.store.controller.command.impl.AddProductToSupplyCommand;
import by.epam.store.controller.command.impl.BlockUserCommand;
import by.epam.store.controller.command.impl.CancelOrderCommand;
import by.epam.store.controller.command.impl.ChangeQuantityOfProductInBasketCommand;
import by.epam.store.controller.command.impl.ChangeLocaleCommand;
import by.epam.store.controller.command.impl.ChangePasswordCommand;
import by.epam.store.controller.command.impl.ChangeProductDataCommand;
import by.epam.store.controller.command.impl.ChangeUserDataCommand;
import by.epam.store.controller.command.impl.CheckoutCommand;
import by.epam.store.controller.command.impl.ConfirmRegistrationCommand;
import by.epam.store.controller.command.impl.DefaultCommand;
import by.epam.store.controller.command.impl.FindOrderByIdCommand;
import by.epam.store.controller.command.impl.FindOrdersByStatusCommand;
import by.epam.store.controller.command.impl.FindUserByLoginCommand;
import by.epam.store.controller.command.impl.FindUserOrdersCommand;
import by.epam.store.controller.command.impl.RemoveProductFromBasketCommand;
import by.epam.store.controller.command.impl.SendMessageCommand;
import by.epam.store.controller.command.impl.ForgotPasswordCommand;
import by.epam.store.controller.command.impl.GoToBasketPageCommand;
import by.epam.store.controller.command.impl.GoToMainPageCommand;
import by.epam.store.controller.command.impl.GoToOrdersPageCommand;
import by.epam.store.controller.command.impl.GoToProfilePageCommand;
import by.epam.store.controller.command.impl.ProcessOrderCommand;
import by.epam.store.controller.command.impl.GoToClientsPageCommand;
import by.epam.store.controller.command.impl.ShowProductsFromCategoryCommand;
import by.epam.store.controller.command.impl.ShowProductsInStockCommand;
import by.epam.store.controller.command.impl.ShowProductsOnOrderCommand;
import by.epam.store.controller.command.impl.SignInCommand;
import by.epam.store.controller.command.impl.SignOutCommand;
import by.epam.store.controller.command.impl.SignUpCommand;
import by.epam.store.controller.command.impl.UnblockUserCommand;
import by.epam.store.controller.command.impl.FindProductsByNameCommand;
import by.epam.store.controller.command.impl.FindUserByIdCommand;

/**
 * CommandProvider is used for defining command
 * 
 * @author Mariya Kuksar
 */
public final class CommandProvider {
	private static final Logger logger = LogManager.getLogger();
	private static Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);

	static {
		commands.put(CommandType.ACCEPT_PRODUCTS, new AcceptProductsCommand());
		commands.put(CommandType.ADD_PRODUCT_TO_BASKET, new AddProductToBasketCommand());
		commands.put(CommandType.ADD_PRODUCT_TO_CATALOG, new AddProductToCatalogCommand());
		commands.put(CommandType.ADD_PRODUCT_TO_SUPPLY, new AddProductToSupplyCommand());
		commands.put(CommandType.BLOCK_USER, new BlockUserCommand());
		commands.put(CommandType.CANCEL_ORDER, new CancelOrderCommand());
		commands.put(CommandType.CHANGE_QUANTITY_OF_PRODUCT_IN_BASKET, new ChangeQuantityOfProductInBasketCommand());
		commands.put(CommandType.CHANGE_PASSWORD, new ChangePasswordCommand());
		commands.put(CommandType.CHANGE_PRODUCT_DATA, new ChangeProductDataCommand());
		commands.put(CommandType.CHANGE_USER_DATA, new ChangeUserDataCommand());
		commands.put(CommandType.CHECKOUT, new CheckoutCommand());
		commands.put(CommandType.CONFIRM_REGISTRATION, new ConfirmRegistrationCommand());
		commands.put(CommandType.DEFAULT, new DefaultCommand());
		commands.put(CommandType.EN, new ChangeLocaleCommand());
		commands.put(CommandType.FIND_ORDER_BY_ID, new FindOrderByIdCommand());
		commands.put(CommandType.FIND_ORDERS_BY_STATUS, new FindOrdersByStatusCommand());
		commands.put(CommandType.FIND_PRODUCTS_BY_NAME, new FindProductsByNameCommand());
		commands.put(CommandType.FIND_USER_BY_ID, new FindUserByIdCommand());
		commands.put(CommandType.FIND_USER_BY_LOGIN, new FindUserByLoginCommand());
		commands.put(CommandType.FIND_USER_ORDERS, new FindUserOrdersCommand());
		commands.put(CommandType.FORGOT_PASSWORD, new ForgotPasswordCommand());
		commands.put(CommandType.GO_TO_BASKET_PAGE, new GoToBasketPageCommand());
		commands.put(CommandType.GO_TO_CLIENTS_PAGE, new GoToClientsPageCommand());
		commands.put(CommandType.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
		commands.put(CommandType.GO_TO_ORDERS_PAGE, new GoToOrdersPageCommand());
		commands.put(CommandType.GO_TO_PROFILE_PAGE, new GoToProfilePageCommand());
		commands.put(CommandType.PROCESS_ORDER, new ProcessOrderCommand());
		commands.put(CommandType.REMOVE_PRODUCT_FROM_BASKET, new RemoveProductFromBasketCommand());
		commands.put(CommandType.RU, new ChangeLocaleCommand());
		commands.put(CommandType.SEND_MESSAGE, new SendMessageCommand());
		commands.put(CommandType.SHOW_PRODUCTS_FROM_CATEGORY, new ShowProductsFromCategoryCommand());
		commands.put(CommandType.SHOW_PRODUCTS_IN_STOCK, new ShowProductsInStockCommand());
		commands.put(CommandType.SHOW_PRODUCTS_ON_ORDER, new ShowProductsOnOrderCommand());
		commands.put(CommandType.SIGN_IN, new SignInCommand());
		commands.put(CommandType.SIGN_OUT, new SignOutCommand());
		commands.put(CommandType.SIGN_UP, new SignUpCommand());
		commands.put(CommandType.UNBLOCK_USER, new UnblockUserCommand());
	}

	private CommandProvider() {
	}

	/**
	 * Defines command
	 * 
	 * @param commandName {@link String} command name
	 * @return {@link Command}
	 */
	public static Command defineCommand(String commandName) {
		if (commandName == null) {
			logger.error("null command");
			return commands.get(CommandType.DEFAULT);
		}
		CommandType commandType;
		try {
			commandType = CommandType.valueOf(commandName.toUpperCase());
		} catch (IllegalArgumentException e) {
			logger.error("no such command name " + commandName, e);
			commandType = CommandType.DEFAULT;
		}
		return commands.get(commandType);
	}
}
