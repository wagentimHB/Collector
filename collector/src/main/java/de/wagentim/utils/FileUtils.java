package de.wagentim.collector.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public final class FileUtils
{
	public static final boolean fileExist(Path filePath)
	{
		LinkOption[] options = { LinkOption.NOFOLLOW_LINKS };

		return Files.exists(filePath, options);
	}
	
	public static final boolean createNewFile(Path filePath)
	{
		try
		{
			Path parent = filePath.getParent();
			
			if(!Files.exists(parent))
			{
				Files.createDirectories(parent);
			}
			
			Files.createFile(filePath);
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return true;
	}

	public static String removeFileExtension(String filename, boolean removeAllExtensions) {
		if (filename == null || filename.isEmpty()) {
			return filename;
		}

		String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
		return filename.replaceAll(extPattern, "");
	}

}
