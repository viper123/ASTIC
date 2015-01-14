package ro.info.asticlib.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;

public class Croiser extends SimpleFileVisitor<Path> {
	
	private String root;
	private FileProcessor fileProcessor;
	
	public Croiser(String root,FileProcessor procesor) {
		this.root = root;
		this.fileProcessor = procesor;
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		System.out.println("Error:"+file);
		return FileVisitResult.SKIP_SIBLINGS;
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		if(isRoot(dir.toString())){
			return FileVisitResult.CONTINUE;
		}
		DosFileAttributes dosAttrs = (DosFileAttributes) attrs;
		if(dosAttrs!=null){
			return dosAttrs.isHidden()||dosAttrs.isOther()?
					FileVisitResult.SKIP_SUBTREE:FileVisitResult.CONTINUE;
		}
		return FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		File fileFromPath = file.toFile();
		if(fileProcessor.isFileAcceptable(fileFromPath,attrs)){
			fileProcessor.processFile(fileFromPath);
		}
		return FileVisitResult.CONTINUE;
	}
	
	private boolean isRoot(String path){
		return path.equals(root);
	}

}
