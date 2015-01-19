package ro.info.astic;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import ro.info.asticlib.io.server.BaseServer;
import ro.info.asticlib.io.server.ClientIO;
import ro.info.asticlib.query.Query;
import ro.info.asticlib.query.QueryHq;
import ro.info.asticlib.query.QueryResult;
import ro.info.asticlib.tests.TestTree;
import ro.info.asticlib.tree.Tree;


public class Server extends BaseServer {
	
	private QueryHq queryHq;
	
	public Server(){
		queryHq = new QueryHq();
	}
	
	@Override
	public void run(ClientIO io) {
		byte[] buffer = new byte[1024];
		while(true){
			try{
				cleanBuffer(buffer);
				io.in.read(buffer);
				
				String clientRequest = new String(buffer,"US-ASCII");
				Query query = new Gson().fromJson(clientRequest,Query.class);
				QueryResult result = queryHq.query(query);
				//transforma result in string
				// trimite la server;
			}catch(Exception e){
				e.printStackTrace();
				restart(retries++);
				break;
			}
		}
	}
	
	private void cleanBuffer(byte[] buffer){
		for(int i=buffer.length;i>=0;i--){
			buffer[i] = 0;
		}
	}
	
}
	