package by.epam.store.util;

import java.io.File;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The utility is responsible for working with files
 * 
 * @author Mariya Kuksar
 */
public final class FileUtil {
	private static final Logger logger = LogManager.getLogger();
	private static final String FILE_EXTENSION_SEPARATOR = ".";

	private FileUtil() {
	}

	/**
	 * Generates new file name
	 * 
	 * @param fileName {@link String} current file name
	 * @return {@link String} generated new file name
	 */
	public static String generateName(String fileName) {
		if (fileName == null || !fileName.contains(FILE_EXTENSION_SEPARATOR)) {
			logger.error("incorrect fileName");
			return StringUtils.EMPTY;
		}
		UUID uuid = UUID.randomUUID();
		String name = uuid.toString();
		int index = fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		String extension = fileName.substring(index);
		return name + extension;
	}

	/**
	 * Deletes the file
	 * 
	 * @param filePath {@link String} path to the file to be deleted
	 * @return boolean true if and only if the file or directory is successfully
	 *         deleted; false otherwise
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return file.delete();
	}
}
