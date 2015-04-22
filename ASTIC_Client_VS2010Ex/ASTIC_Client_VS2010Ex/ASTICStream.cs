using ASTIC_client;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2
{
    public class ASTICStream
    {
        public const int BUFFER_SIZE = 1024;
        private Stream stream;
        private byte endSep = (byte)'|';
        private byte[] buffer;
        private bool bufferDirty = false;

        public ASTICStream(Stream stream)
        {
            this.stream = stream;
            buffer = new byte [BUFFER_SIZE];
        }

        public String ReadString()
        {
            if (bufferDirty)
            {
                clearBuffer();
            }
            StringBuilder builder  =  new StringBuilder();
            byte abyte = 0;
            do
            {
                int read = stream.Read(buffer, 0, buffer.Count());
                bufferDirty = true;
                abyte = buffer[read - 1];
                builder.Append(EncodeUtil.decode(buffer, abyte == endSep ? read - 1 : read));
            } while (abyte != endSep);
            return builder.ToString();
        }

        public void WriteString(String message)
        {
            int messageSize = System.Text.ASCIIEncoding.ASCII.GetByteCount(message);
            byte [] buffer = new byte[messageSize + 1];
            byte [] messageBytes = EncodeUtil.encode(message);
            buffer[messageSize] = endSep;
            for (int i = buffer.Count() - 2; i >= 0; i-- )
            {
                buffer[i] = messageBytes[i];
            }
            stream.Write(buffer, 0, buffer.Count());
        }

        private void clearBuffer()
        {
            for (int i = buffer.Count() - 1; i >= 0; i--)
            {
                buffer[i] = 0;
            }
            bufferDirty = false;
        }
    }
}
