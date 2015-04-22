package ro.info.asticlib.io.parsers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitor extends SimpleFileVisitor<Path>  {
	
	private String root;
	private FileVisitObserver observer;
	private FileVisitCallback fileVisitCallback;
	private boolean stoped = false;
	
	public FileVisitor(String root,FileVisitObserver observer,FileVisitCallback callback) {
		this.root = root;
		this.observer = observer;
		this.fileVisitCallback = callback;
	}
	
	public void startVisitor(){
		try {
			Files.walkFileTree(new File(root).toPath(), this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public FileVisitResult visitFile(final Path file, BasicFileAttributes attrs)  throws IOException {
		observer.onStartVisitFile();
		fileVisitCallback.visitFile(file, attrs);
		observer.onProbablyDone();
		return isStoped()?FileVisitResult.TERMINATE:FileVisitResult.CONTINUE;
	}
	
	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		System.err.println(file.toFile().getAbsolutePath());
		observer.onProbablyDone();
		return isStoped()?FileVisitResult.TERMINATE:FileVisitResult.SKIP_SIBLINGS;
	}
	
	public synchronized void stop(){
		stoped = true;
	}
	
	public synchronized boolean isStoped(){
		return stoped;
	}
	
	public interface FileVisitObserver {
		public void onProbablyDone();
		public void onStartVisitFile();
	}
	
	public interface FileVisitCallback{
		public void visitFile(final Path file, BasicFileAttributes attrs);
	}

}
