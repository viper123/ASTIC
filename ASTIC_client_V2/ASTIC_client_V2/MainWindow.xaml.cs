using ASTIC_client;
using ASTIC_client.clustering;
using ASTIC_client.query;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using GraphX.Controls;
using GraphX;
using System.Data;
using GraphX.GraphSharp.Algorithms.OverlapRemoval;
using GraphX.GraphSharp.Algorithms.Layout.Simple.FDP;
using Hexagonal;
using ASTIC_client_V2.Hexagonal;


namespace ASTIC_client_V2
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private ASTICStream IO;
        private byte[] buffer = new byte[1048576];
        private string lastQuery;
        private Predictor predictor;
        private FileType filter;
        private List<ListViewResult> currentListViewModel;
        private List<String> currentList;
        private List<String> currentPreviews;
        private QueryWorker queryer;
        

        public MainWindow()
        {
            InitializeComponent();
            cb_types.Items.Add(FileType.All);
            cb_types.Items.Add(FileType.App);
            cb_types.Items.Add(FileType.Audio);
            cb_types.Items.Add(FileType.Photo);
            cb_types.Items.Add(FileType.Video);
            cb_types.Items.Add(FileType.Text);
            cb_types.Items.Add(FileType.Other);
            cb_types.SelectedIndex = 0;
            cb_view_type.Items.Add(DisplayType.CLASSIC);
            cb_view_type.Items.Add(DisplayType.GRAPH);
            cb_view_type.SelectedIndex = 0;
            filter = FileType.All;
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            Client client = new Client();
            client.runListener += setServerIO;
            client.connect();
        }

        public void setServerIO(ServerIO io)
        {
            this.IO = new ASTICStream(io.IO);
            predictor = new Predictor(onHavePredictions, IO);
        }

        private void startQuery(string q)
        {
            if (queryer == null)
            {
                queryer = new QueryWorker(onQueryProgressChanged, onQueryComplete, IO);
            }
            queryer.query(q);
        }

        private void onQueryComplete(QueryResult result)
        {
            if (result.getResultTree() != null)
            {
                Console.WriteLine("Sucess");
                displayTree(result.getQuery(), result.getResultTree());
                //displayGraph(result.clusterList);
                displayHexGraph(result.clusterList, result.distanceMatrix);
                changeStatus("Rezultate gasite");
            }
            else
            {
                Console.WriteLine("Error");
                changeStatus("Eruare");
                treeView1.Items.Clear();
            }
            changeProgress(0);
        }

        private void onQueryProgressChanged(int progress)
        {
            changeProgress(progress);
        }

        private void onHavePredictions(List<String> predictions)
        {
            if (!textBox1.IsFocused)
            {
                return;
            }
            changeProgress(100);
            changeProgress(0);
            changeStatus(predictions != null ? predictions.Count + " predictii gasite" : 
                "Nicio predictie gasita");
            setAutoCompleteItems(predictions);
             
        }
        
        private Tree<Cluster> currentTree;

        private void displayTree(Query q,Tree<Cluster> tree)
        {
            treeView1.Items.Clear();
            TreeViewItem root = new TreeViewItem();
            root.Header = q.getQueryArray()[0];
            root.Tag = tree.Root;
            foreach(Node<Cluster> node in tree.getFirstCildrens())
            {
                TreeViewItem tn =
                        new TreeViewItem();
                tn.Header = getNameForCluster(node.value);
                tn.Tag = node;
                root.Items.Add(tn);
                if (node.HasChildrens)
                {
                    fillChildrens(tn, node);
                }
            }
            treeView1.Items.Add(root);
        }

        
        private void fillChildrens(TreeViewItem parentT, Node<Cluster> parent)
        {
            foreach (Node<Cluster> node in parent.childrens)
            {
                if (node.HasChildrens)
                {
                    TreeViewItem tn =
                        new TreeViewItem();
                    tn.Header = getNameForCluster(node.value);
                    tn.Tag = node;

                    parentT.Items.Add(tn);
                    fillChildrens(tn, node);
                }
            }
        }

        /**
         * Transforma din date de tip file in informatii care se pot afisa in lista
         * */
        private void displayList(List<String> list,String [] query)
        {
            currentList = list;
            List<ListViewResult> model = new List<ListViewResult>();
            foreach(String file in list)
            {
                FileType fileType= FileTypeFactory.FromFile(file);
                if (fileType == filter || filter == FileType.All)
                {
                    model.Add(new ListViewResult(file,query));
                }
            }
            model.Sort(new RelavanceComparator());
            currentListViewModel = model;
            model.ForEach(item => { lb_results.Items.Add(item); });
        }


        /**
         * Daca clusterul are un singur fisier afiseaza fisierul altfel creaza un nume 
         * */
        private String getNameForCluster(Cluster c)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("[");
            int k = 0;
            foreach(String str in c.reprezentativeWords){
                builder.Append(str);
                if(k++ < c.wordWeightMap.Keys.Count-1){
                    builder.Append("+");
                }
                if (k > 3)
                {
                    break;
                }
            }
            builder.Append("]");
            return builder.ToString();
        }

        private void setAutoCompleteItems(List<String> list)
        {
            lb_predictions.Items.Clear();
            lb_predictions.Visibility = System.Windows.Visibility.Hidden;
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
            limitedPrediction.ForEach(item => lb_predictions.Items.Add(item));
            
            lb_predictions.Visibility = System.Windows.Visibility.Visible;
        }

        public String [] getCurrentQuery()
        {
            return textBox1.Text.Split(' ');
        }

        #region Actions
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            startQuery(textBox1.Text);
            lb_predictions.Visibility = Visibility.Hidden;
        }

        private void treeView1_SelectedItemChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
        {
            lb_results.Items.Clear();
            try
            {
                TreeViewItem clikedNode = (TreeViewItem)treeView1.SelectedItem;
                Node<Cluster> cluster = (Node<Cluster>)clikedNode.Tag;
                List<String> files = new List<String>();
                files.AddRange(cluster.value.fileWordMap.Keys);
                displayList(files,getCurrentQuery());
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex);
            }
        }

        private void lb_predictions_KeyUp(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                selectAndItemForSearch();
            }
        }

        private void selectAndItemForSearch()
        {
            String selected = (String)lb_predictions.SelectedItem;
            if (selected != null)
            {
                textBox1.Text = selected;
                lb_predictions.Visibility = System.Windows.Visibility.Hidden;
                textBox1.Focus();
                changeStatus("Apasa \"Cauta\" pentru a incepe cautarea");
            }
        }

        private void textBox1_KeyUp_1(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Down)
            {
                lb_predictions.Focus();
                return;
            }
            if (e.Key == Key.Enter)
            {
                startQuery(textBox1.Text);
                lb_predictions.Visibility = Visibility.Hidden;
                treeView1.Focus();
                return;
            }
            String text = textBox1.Text;
            if (text.Length == 0)
            {
                return;
            }
            changeStatus("Cauta predictie");
            changeProgress(40);
            predictor.predict(text);
        }

        private void textBox1_GotFocus(object sender, RoutedEventArgs e)
        {
            lb_predictions.Visibility = Visibility.Hidden;
        }

        private void cb_types_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            filter = (FileType)cb_types.SelectedItem;
            if (currentListViewModel != null)
            {
                lb_results.Items.Clear();
                displayList(currentList,getCurrentQuery());
            }
        }

        private void cb_display_types_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            DisplayType type = (DisplayType)cb_view_type.SelectedItem;
            switch (type)
            {
                case DisplayType.CLASSIC:
                    lb_results.Visibility = System.Windows.Visibility.Visible;
                    treeView1.Visibility = Visibility.Visible;
                    canvas.Visibility = Visibility.Collapsed;
                    break;
                case DisplayType.GRAPH:
                    lb_results.Visibility = System.Windows.Visibility.Collapsed;
                    treeView1.Visibility = Visibility.Collapsed;
                    canvas.Visibility = Visibility.Visible;
                    break;
            }
        }

        private void lb_results_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            int selectedIndex = lb_results.SelectedIndex;
            if (currentListViewModel != null &&
                selectedIndex >= 0 &&
                selectedIndex < currentListViewModel.Count)
            {
                FileLauncher.launchFile(currentListViewModel[selectedIndex].FilePath);
            }
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
            toolStripStatusLabel1.Content = status;
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

        #region GraphX

        private void displayHexGraph(List<Cluster> list, double [][]matrix)
        {
            Board board = new Model().drawHexGraphics(list, matrix);
            GraphicsEngine engine = new GraphicsEngine(board);
            ((SpecialCanvas)canvas).Engine = engine;
            scroll.ScrollToVerticalOffset(engine.Height / 2 - 100);
            //canvas.Children.Add()
        }

        

        private void displayGraph(List<Cluster> clusters)
        {
            Random Rand = new Random();

            //Create data graph object
            var graph = new GraphExample();
            
            //Create and add vertices using some DataSource for ID's
            foreach (var item in clusters)
                graph.AddVertex(new DataVertex() { ID = Int32.Parse(item.id), Text = item.id });

            var vlist = graph.Vertices.ToList();
            //Generate random edges for the vertices
            foreach (var item in vlist)
            {
                
                var vertex2 = vlist[Rand.Next(0, graph.VertexCount - 1)];
                graph.AddEdge(new DataEdge(item, vertex2, Rand.Next(1, 50)) { 
                    Text = string.Format("{0} -> {1}", item, vertex2) });
            }

            var LogicCore = new GXLogicCoreExample();
            //This property sets layout algorithm that will be used to calculate vertices positions
            //Different algorithms uses different values and some of them uses edge Weight property.
            LogicCore.DefaultLayoutAlgorithm = GraphX.LayoutAlgorithmTypeEnum.KK;
            //Now we can set optional parameters using AlgorithmFactory
            //NOTE: default parameters can be automatically created each time you change Default algorithms
            LogicCore.DefaultLayoutAlgorithmParams =
                               LogicCore.AlgorithmFactory.CreateLayoutParameters(GraphX.LayoutAlgorithmTypeEnum.KK);
            //Unfortunately to change algo parameters you need to specify params type which is different for every algorithm.
            ((KKLayoutParameters)LogicCore.DefaultLayoutAlgorithmParams).MaxIterations = 100;

            //This property sets vertex overlap removal algorithm.
            //Such algorithms help to arrange vertices in the layout so no one overlaps each other.
            LogicCore.DefaultOverlapRemovalAlgorithm = GraphX.OverlapRemovalAlgorithmTypeEnum.FSA;
            //Setup optional params
            LogicCore.DefaultOverlapRemovalAlgorithmParams =
                              LogicCore.AlgorithmFactory.CreateOverlapRemovalParameters(GraphX.OverlapRemovalAlgorithmTypeEnum.FSA);
            ((OverlapRemovalParameters)LogicCore.DefaultOverlapRemovalAlgorithmParams).HorizontalGap = 50;
            ((OverlapRemovalParameters)LogicCore.DefaultOverlapRemovalAlgorithmParams).VerticalGap = 50;

            //This property sets edge routing algorithm that is used to build route paths according to algorithm logic.
            //For ex., SimpleER algorithm will try to set edge paths around vertices so no edge will intersect any vertex.
            LogicCore.DefaultEdgeRoutingAlgorithm = GraphX.EdgeRoutingAlgorithmTypeEnum.SimpleER;

            //This property sets async algorithms computation so methods like: Area.RelayoutGraph() and Area.GenerateGraph()
            //will run async with the UI thread. Completion of the specified methods can be catched by corresponding events:
            //Area.RelayoutFinished and Area.GenerateGraphFinished.
            LogicCore.AsyncAlgorithmCompute = false;

            //Finally assign logic core to GraphArea object
            LogicCore.Graph = graph;
            //gg_Area.LogicCore = LogicCore;
            //gg_Area.GenerateGraph(true);
            
        }

        #endregion





















    }
	
	
}
