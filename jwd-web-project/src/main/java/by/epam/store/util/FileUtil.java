package by.epam.store.util;

import java.io.File;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FileUtil {
	private static final Logger logger = LogManager.getLogger();
	private static final String FILE_EXTENSION_SEPARATOR = ".";

	private FileUtil() {
	}

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

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		file.delete();
	}
}
