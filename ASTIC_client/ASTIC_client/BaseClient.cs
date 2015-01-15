using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;

namespace ASTIC_client
{
    public abstract class BaseClient
    {

        private ServerIO serverIO;

        public void connect()
        {
            TcpClient tcpclnt = new TcpClient();
            Console.WriteLine("Connecting.....");
            tcpclnt.Connect("localhost", Conf.SERVER_PORT);
            Console.WriteLine("Conected");
            serverIO = new ServerIO(tcpclnt.GetStream());
            run(serverIO);
        }

        public abstract void run(ServerIO io);

    }
}
