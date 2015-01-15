package ro.info.asticlib.io.server;

import java.io.InputStream;
import java.io.OutputStream;

public class ClientIO {

	public InputStream in;
	public OutputStream out;
	
	public ClientIO(InputStream in,OutputStream out){
		this.in = in;
		this.out = out;
	}
}
