package ro.info.asticlib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AsticStream {
	public static final int BUFFER_SIZE = 1024;
	public InputStream in;
	public OutputStream out;
    private byte endSep = (byte)'|';
    private byte[] buffer;
    private boolean bufferDirty = false;

    public AsticStream(InputStream in,OutputStream out)
    {
        this.in = in;
        this.out = out;
        buffer = new byte [BUFFER_SIZE];
    }

    public String ReadString() throws IOException
    {
        if (bufferDirty)
        {
            clearBuffer();
        }
        StringBuilder builder  =  new StringBuilder();
        byte abyte = 0;
        do
        {
            int read = in.read(buffer, 0, buffer.length);
            bufferDirty = true;
            if(read > 0){
            	abyte = buffer[read - 1];
            	builder.append(new String(buffer,0,(abyte == endSep?read - 1:read),"US-ASCII"));
            }else{
            	abyte = endSep;
            }
        } while (abyte != endSep);
        return builder.toString();
    }

    public void WriteString(String message) throws IOException
    {
        byte [] messageBytes = message.getBytes();
        int messageSize = messageBytes.length;
        byte [] buffer = new byte[messageSize + 1];
        buffer[messageSize] = endSep;
        for (int i = buffer.length - 2; i >= 0; i-- )
        {
            buffer[i] = messageBytes[i];
        }
        out.write(buffer, 0, buffer.length);
    }

    private void clearBuffer()
    {
        for (int i = buffer.length - 1; i >= 0; i--)
        {
            buffer[i] = 0;
        }
        bufferDirty = false;
    }
}
