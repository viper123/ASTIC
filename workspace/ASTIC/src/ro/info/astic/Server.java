package ro.info.astic;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

import ro.info.asticlib.io.server.BaseServer;
import ro.info.asticlib.io.server.ClientIO;
import ro.info.asticlib.query.ClusterL0DataInterpretor;
import ro.info.asticlib.query.Query;
import ro.info.asticlib.query.QueryHq;
import ro.info.asticlib.query.QueryResult;


public class Server extends BaseServer {
	
	private QueryHq queryHq;
	
	public Server(){
		queryHq = new QueryHq();
		queryHq.addDataInterpretor(new ClusterL0DataInterpretor());
	}
	
	@Override
	public void run(ClientIO io) {
		byte[] buffer = new byte[2048];
		while(true){
			try{
				cleanBuffer(buffer);
				io.in.read(buffer);
				
				String clientRequest = new String(buffer,"US-ASCII");
				System.out.println(clientRequest);
				JsonReader reader = new JsonReader(new StringReader(clientRequest));
				reader.setLenient(true);
				Query query = gson.fromJson(reader,Query.class);
				QueryResult result = queryHq.query(query);
				String response = gson.toJson(result);
				System.out.println(response);
				io.out.write(response.getBytes());
			}catch(Exception e){
				e.printStackTrace();
				restart(retries++);
				break;
			}
		}
	}
	
	private void cleanBuffer(byte[] buffer){
		for(int i=buffer.length-1;i>=0;i--){
			buffer[i] = 0;
		}
	}
	
}
	