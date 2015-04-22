package ro.info.asticlib.io.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.io.AsticStream;

public abstract class BaseServer implements Runnable {

	public static final int RETRY_MAX = 40;
	
	protected AsticStream stream;
	protected ServerSocket welcomeSocket;
	protected Socket connectionSocket;
	protected int retries;
	protected Gson gson = new Gson();
	private boolean stoped = false;
	
	public BaseServer(){
		try {
			welcomeSocket = new ServerSocket(Conf.SERVER_PORT_NUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void run(AsticStream io);
	
	
	public AsticStream getClientIO(){
		return stream;
	}
	
	
	public synchronized void stop(){
		stoped = true;
		Thread.currentThread().interrupt();
	}
	
	public synchronized boolean isStoped(){
		return stoped;
	}
	
	
}
