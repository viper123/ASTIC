package ro.info.astic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import ro.info.asticlib.io.AsticStream;
import ro.info.asticlib.io.server.BaseServer;
import ro.info.asticlib.query.ClusterL0DataInterpretor;
import ro.info.asticlib.query.Query;
import ro.info.asticlib.query.QueryHq;
import ro.info.asticlib.query.QueryResult;

import com.google.gson.stream.JsonReader;


public class Server extends BaseServer {
	
	private QueryHq queryHq;
	
	public Server(){
		queryHq = new QueryHq();
		queryHq.addDataInterpretor(new ClusterL0DataInterpretor());
	}
	
	@Override
	public void run(AsticStream io) {
		while(!isStoped()){
			try{
				String clientRequest = io.ReadString();
				System.out.println(clientRequest);
				JsonReader reader = new JsonReader(new StringReader(clientRequest));
				reader.setLenient(true);
				Query query = gson.fromJson(reader,Query.class);
				QueryResult result = queryHq.query(query);
				String response = gson.toJson(result);
				io.WriteString(response);
			}catch(Exception e){
				e.printStackTrace();
				restart(retries++);
				break;
			}
		}
		System.out.println("Query Server stoped");
	}

	@Override
	public void run() {
		try {
			System.out.println("waiting for clients:");
			connectionSocket = welcomeSocket.accept(); 
			System.out.println("client accepted");
			InputStream in = connectionSocket.getInputStream();
			OutputStream out = connectionSocket.getOutputStream();
			stream = new AsticStream(in, out);
			run(stream);
		} catch (Exception e) {
			e.printStackTrace();
			restart(retries++);
		}
	}
	
	public void restart(int retries){
		if(retries<RETRY_MAX){
			run();
		}
	}
	
}
	