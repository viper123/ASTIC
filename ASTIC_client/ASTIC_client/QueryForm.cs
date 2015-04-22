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
using ASTIC_client.clustering;

namespace ASTIC_client
{
    public partial class QueryForm : Form 
    {
        private ServerIO ServerIO;
        private byte[] buffer = new byte[1048576];
        private string lastQuery;
        private Predictor predictor;

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

        public void setServerIO(ServerIO io)
        {
            this.ServerIO = io;
            predictor = new Predictor(onHavePredictions, ServerIO);
        }

        private void startQuery(string q)
        {
            changeProgress(10);
            Query query = new Query();
            query.setLevel(Query.LEVEL_2);
            string[] queryArray = q.Split(' ');
            query.setQueryArray(queryArray);
            string queryString = JsonConvert.SerializeObject(query);
            changeProgress(20);
            byte [] message = EncodeUtil.encode(queryString);
            ServerIO.IO.Write(message,0,message.Length);
            changeProgress(60);
            int read = ServerIO.IO.Read(buffer, 0, buffer.Length);
            String fromServer = EncodeUtil.decode(buffer, read);
            changeProgress(80);
            QueryResult result = JsonConvert.DeserializeObject<QueryResult>(fromServer);
            if (result.getResultTree() != null)
            {
                Console.WriteLine("Sucess");
                displayTree(query,result.getResultTree());
                changeStatus("Rezultate gasite");
            }
            else
            {
                Console.WriteLine("Error");
                changeStatus("Eruare");
                treeView1.Nodes.Clear();
            }
            changeProgress(100);
        }

        private void onHavePredictions(List<String> predictions)
        {
            changeProgress(100);
            changeStatus(predictions != null ? predictions.Count + " predictii gasite" : 
                "Nicio predictie gasita");
            setAutoCompleteItems(predictions);
        }
        
        private Tree<Cluster> currentTree;

        private void displayTree(Query q,Tree<Cluster> tree)
        {
            treeView1.Nodes.Clear();
            TreeNode root = new TreeNode(q.getQueryArray()[0]);
            foreach(Node<Cluster> node in tree.getFirstCildrens()){
                TreeNode tn = 
                    new TreeNode(getNameForCluster(node.value));
                tn.Tag = node;
                root.Nodes.Add(tn);
                fillChildrens(tn, node);
            }
            treeView1.Nodes.Add(root);
        }

        

        private void fillChildrens(TreeNode parentT, Node<Cluster> parent)
        {
            foreach (Node<Cluster> node in parent.childrens)
            {
                TreeNode tn =
                    new TreeNode(getNameForCluster(node.value));
                tn.Tag = node;
                parentT.Nodes.Add(tn);
                fillChildrens(tn, node);
            }
        }

        private void displayList(List<String> list)
        {
            
            lb_results.Items.AddRange(list.ToArray());
        }

        /**
         * Daca clusterul are un singur fisier afiseaza fisierul altfel creaza un nume 
         * */
        private String getNameForCluster(Cluster c)
        {
            if (c.fileWordMap.Count == 1)
            {
                return c.fileWordMap.Keys.ElementAt(0);
            }
            else
            {
                int max = -1;
                string word = null ;
                foreach (String key in c.wordWeightMap.Keys)
                {
                    if (c.wordWeightMap[key] > max)
                    {
                        word = key;
                        max = c.wordWeightMap[key];
                    }
                }
                return word;
            }
        }

        private void setAutoCompleteItems(List<String> list)
        {
            lb_predictions.Items.Clear();
            lb_predictions.Visible = false;
            if (list == null)
            {
                return;
            }
            List<String> limitedPrediction = new List<string>();
            foreach (String prediction in list)
            {
                limitedPrediction.Add(prediction);
                if (limitedPrediction.Count > 15)
                {
                    break;
                }
            }
            lb_predictions.Items.AddRange(limitedPrediction.ToArray());
            lb_predictions.Visible = true;
        }

        #region Actions
        private void btn_search_MouseClick(object sender, MouseEventArgs e)
        {
            startQuery(textBox1.Text);
        }

        private void treeView1_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            lb_results.Items.Clear();
            try
            {
                TreeNode clikedNode = treeView1.SelectedNode;
                Node<Cluster> cluster = (Node<Cluster>)clikedNode.Tag;
                List<String> files = new List<String>();
                files.AddRange(cluster.value.fileWordMap.Keys);
                displayList(files);
            }
            catch (Exception ex)
            {

            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            
        }

        private void lb_predictions_SelectedIndexChanged(object sender, EventArgs e)
        {
            String selected = (String)lb_predictions.SelectedItem;
            if (selected != null)
            {
                textBox1.Text = selected;
                lb_predictions.Visible = false;
                changeStatus("Apasa \"Cauta\" pentru a incepe cautarea");
            }
        }

        private void textBox1_KeyUp(object sender, KeyEventArgs e)
        {
            String text = textBox1.Text;
            if (text.Length == 0)
            {
                return;
            }
            changeStatus("Cauta predictie");
            changeProgress(40);
            predictor.predict(text);
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

        private void changeStatus(String status)
        {
            toolStripStatusLabel1.Text = status;
        }

        private void changeProgress(int progress)
        {
            this.toolStripProgressBar1.Value = progress;
        }

        private bool match(String s1, String s2)
        {
            if (s1.Length <= s2.Length)
            {
                return s2.Substring(0, s1.Length).Equals(s1);
            }
            else
            {
                return match(s2, s1);
            }
        }

        
        
    }
}
