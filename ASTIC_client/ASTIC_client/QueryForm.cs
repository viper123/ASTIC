using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using ASTIC_client.query;
using Newtonsoft.Json;

namespace ASTIC_client
{
    public partial class QueryForm : Form 
    {
        private ServerIO ServerIO;
        private byte[] buffer = new byte[1024];

        public QueryForm()
        {
            InitializeComponent();
        }

        private void QueryForm_Load(object sender, EventArgs e)
        {
            Client client = new Client();
            client.runListener += setServerIO;
            client.connect();
        }

        #region Actions

        public void setServerIO(ServerIO io)
        {
            this.ServerIO = io;
        }

        private void cb_query_TextChanged(object sender, EventArgs e)
        {
            cb_query.DroppedDown = false;
            if (cb_query.Text.Length < 3)
            {
                
                return;
            }
            Query query = new Query();
            query.setLevel(Query.LEVEL_0);
            string output = JsonConvert.SerializeObject(query);
            byte [] message  =EncodeUtil.encode(output);
            ServerIO.IO.Write(message,0,message.Length);
            //clear the buffer;
            int read = ServerIO.IO.Read(buffer, 0, buffer.Length);
            String fromServer = EncodeUtil.decode(buffer,read);
            //cb_query.Items.Clear();
            QueryResult result = JsonConvert.DeserializeObject<QueryResult>(fromServer);
            Console.WriteLine(result!=null?"success":"cineva nu o sa doarma");
        }

        #endregion

        #region ClientServer
        class Client : BaseClient
        {

            public delegate void ToRun(ServerIO io);

            public ToRun runListener;

            public override void run(ServerIO io)
            {
                runListener(io);
            }

        }
        #endregion

        private void btn_search_MouseClick(object sender, MouseEventArgs e)
        {
            String query = cb_query.Text;
            byte [] bytes = EncodeUtil.encode(query);
            ServerIO.IO.Write(bytes, 0, bytes.Length);
            int read =ServerIO.IO.Read(buffer, 0, buffer.Length);
            MessageBox.Show(EncodeUtil.decode(buffer,read));
        }
    }
}
