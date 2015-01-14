package ro.info.asticlib.files;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class FileProcessor {
	
	public  abstract void processFile(File file);

	public abstract boolean isFileAcceptable(File file,BasicFileAttributes attrs);
}