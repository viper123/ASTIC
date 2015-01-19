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
				String query = new String(buffer,"US-ASCII");
				String [] queryArray = query.split(Query.QUERY_SEP);
				QueryResult result = queryHq.query(new Query(queryArray, Query.LEVEL_2));
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
	