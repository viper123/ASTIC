package ro.info.astic;

import org.apache.xmlrpc.webserver.WebServer;


public class Server {
	
	public void server(){
	try {

	     System.out.println("Attempting to start XML-RPC Server...");
	     WebServer server = new WebServer(333);
	     //server.addHandler("sample", new Server());
	     server.start();
	     System.out.println("Started successfully.");
	     System.out.println("Accepting requests. (Halt program to stop.)");
	   } catch (Exception exception) {
	     System.err.println("JavaServer: " + exception);
	   }
	}
}
	