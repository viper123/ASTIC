using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace ASTIC_client
{
    public class ServerIO
    {
        public Stream IO;

        public ServerIO(Stream io)
        {
            this.IO = io;
        }
    }
}
