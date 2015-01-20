package ro.info.asticlib.io.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import ro.info.asticlib.conf.Conf;

public abstract class BaseServer {

	private static final int RETRY_MAX = 40;
	
	private ClientIO clientIO;
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	protected int retries;
	protected Gson gson = new Gson();
	
	public BaseServer(){
		try {
			welcomeSocket = new ServerSocket(Conf.SERVER_PORT_NUMBER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void run(ClientIO io);
	
	
	
	public ClientIO getClientIO(){
		return clientIO;
	}
	
	public void start(){
		 try {
			
			System.out.println("waiting for clients:");
			connectionSocket = welcomeSocket.accept(); 
			System.out.println("client accepted");
			InputStream in = connectionSocket.getInputStream();
			OutputStream out = connectionSocket.getOutputStream();
			ClientIO io = new ClientIO(in, out);
			run(io);
		} catch (IOException e) {
			e.printStackTrace();
			restart(retries++);
		} 
	}
	
	public void restart(int retries){
		if(retries<RETRY_MAX){
			start();
		}
	}
}
