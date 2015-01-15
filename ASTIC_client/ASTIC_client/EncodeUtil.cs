using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ASTIC_client
{
    public class EncodeUtil
    {
        public static byte[] encode(String message)
        {
            ASCIIEncoding asen = new ASCIIEncoding();
            return  asen.GetBytes(message);
        }

        public static String decode(byte[] bytes)
        {
            ASCIIEncoding asen = new ASCIIEncoding();
            return asen.GetString(bytes);
        }
    }
}
