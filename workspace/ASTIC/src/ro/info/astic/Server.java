package ro.info.astic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import ro.info.asticlib.io.server.BaseServer;
import ro.info.asticlib.io.server.ClientIO;
import ro.info.asticlib.tests.TestTree;
import ro.info.asticlib.tree.Tree;


public class Server extends BaseServer {

	
	
	
	@Override
	public void run(ClientIO io) {
		
		Tree<String> tree = new TestTree().getTree();
		
		byte[] bytes = new byte[1024];
		while(true){
			try{
				io.out.write(tree.toString().getBytes());
				/*int readed = io.in.read(bytes);
				String str = new String(bytes, "US-ASCII");
				System.out.println("Message:"+str);
				io.out.write(bytes, 0, readed);*/
			}catch(Exception e){
				e.printStackTrace();
				restart(retries++);
				break;
			}
		}
	}
	
}
	