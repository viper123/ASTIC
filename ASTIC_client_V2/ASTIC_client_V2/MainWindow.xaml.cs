﻿using ASTIC_client;
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
using System.Windows.Media.Animation;


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
        private Dictionary<String,List<String>> currentPreviews;
        private QueryWorker queryer;
        private Model hexModel;
        private Board board;
        private GraphicsEngine engine;
        private Previewer previewer;
        private PreviewHelper previewHelper;
        

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
            cb_view_type.Items.Add(DisplayType.CLASIC);
            cb_view_type.Items.Add(DisplayType.GRAFIC);
            cb_view_type.SelectedIndex = 1;
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
            previewer = new Previewer(onHavePreview);
            previewHelper = new PreviewHelper();
        }

        private void onHavePreview(Dictionary<String, List<String>> preview)
        {

        }

        private void onQueryComplete(QueryResult result)
        {
            if (result.clusterList.Count == 0)
            {
                changeStatus("No results found");
                changeProgress(100);
                return;
            }
            if (result.getResultTree() != null)
            {
                Console.WriteLine("Sucess");
                displayTree(result.getQuery(), result.getResultTree());
                //displayGraph(result.clusterList);
                displayHexGraph(result.clusterList, result.distanceMatrix);
                changeStatus("Rezultate gasite");
                changeProgress(100);
            }
            else
            {
                Console.WriteLine("Error");
                changeStatus("Eruare");
                treeView1.Items.Clear();
            }
            changeProgress(100);
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

        public void displayList()
        {
            displayList(currentList, getCurrentQuery());
        }

        /**
         * Transforma din date de tip file in informatii care se pot afisa in lista
         * */
        private void displayList(List<String> list, string [] query)
        {
            currentList = list;
            
            List<ListViewResult> model = new List<ListViewResult>();
            foreach(String file in list)
            {
                FileType fileType= FileTypeFactory.FromFile(file);
                if (fileType == filter || filter == FileType.All)
                {
                    List<String> preview = previewHelper.getPreview(query, file);
                    model.Add(new ListViewResult(file,preview,query));
                }
            }
            model.Sort(new RelavanceComparator());
            currentListViewModel = model;
            lb_results.Items.Clear();
            model.ForEach(item => { lb_results.Items.Add(item); });
            clusterPreview.Items.Clear();
            model.ForEach(item => { clusterPreview.Items.Add(item); });
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
            startQuery(textBox1.Text.Trim());
            lb_predictions.Visibility = Visibility.Hidden;
            changeStatus("Se cauta interogarea, va rugam asteptati");
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

                displayList(files, getCurrentQuery());
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
                String[] queryArray = getCurrentQuery();
                queryArray[queryArray.Length - 1] = selected;
                textBox1.Text = "";
                foreach(String query in queryArray){
                    textBox1.Text +=query + " ";
                }
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
            String [] queryArray = getCurrentQuery();
            predictor.predict(queryArray[queryArray.Length-1]);
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
                displayList(currentList, getCurrentQuery());
            }
        }

        private void cb_display_types_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            DisplayType type = (DisplayType)cb_view_type.SelectedItem;
            switch (type)
            {
                case DisplayType.CLASIC:
                    lb_results.Visibility = System.Windows.Visibility.Visible;
                    treeView1.Visibility = Visibility.Visible;
                    canvas.Visibility = Visibility.Collapsed;
                    clusterPreview.Visibility = Visibility.Collapsed;
                    break;
                case DisplayType.GRAFIC:
                    lb_results.Visibility = System.Windows.Visibility.Collapsed;
                    treeView1.Visibility = Visibility.Collapsed;
                    canvas.Visibility = Visibility.Visible;
                    clusterPreview.Visibility = Visibility.Visible;
                    break;
            }
        }

        private void lb_results_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            int selectedIndex = lb_results.SelectedIndex;
            if (selectedIndex == -1)
            {
                selectedIndex = clusterPreview.SelectedIndex;
            }
            if (currentListViewModel != null &&
                selectedIndex >= 0 &&
                selectedIndex < currentListViewModel.Count)
            {
                FileLauncher.launchFile(currentListViewModel[selectedIndex].FilePath);
            }
        }

        private void cluster_preview_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            int selectedIndex = clusterPreview.SelectedIndex;
            
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
            if (progress < 100)
            {
                this.toolStripProgressBar1.IsIndeterminate = true;
            }
            else
            {
                this.toolStripProgressBar1.IsIndeterminate = false;
            }
            
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
            if (hexModel == null)
            {
                hexModel = new Model();
            }
            board = hexModel.getHexBoard(list, matrix);
            engine = new GraphicsEngine(board);
            ((SpecialCanvas)canvas).Engine = engine;
            scroll.ScrollToVerticalOffset(engine.Height / 2 - 120);

        }

        #endregion

        private void showPreviewForCluster(Cluster c)
        {
            List<String> files = c.fileWordMap.Keys.ToList();
            displayList(files, getCurrentQuery());
            if (clusterPreview.Width > 0)
            {
                return;
            }
            DoubleAnimation sizeAnimation = new DoubleAnimation(0.0, 220.0,
                    new Duration(TimeSpan.FromMilliseconds(300)));

            clusterPreview.BeginAnimation(ListView.WidthProperty, sizeAnimation);
        }

        private void hidePreview()
        {
            if (clusterPreview.Width == 0)
            {
                return;
            }
            DoubleAnimation sizeAnimation = new DoubleAnimation(220.0, 0.0,
                    new Duration(TimeSpan.FromMilliseconds(300)));

            clusterPreview.BeginAnimation(ListView.WidthProperty, sizeAnimation);
        }

        private void clusterPreview_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            if (board == null)
            {
                return;
            }
            Point mouseClick = new Point(e.GetPosition(canvas).X - board.XOffset,
                e.GetPosition(canvas).Y - board.YOffset);
            Hex clickedHex = board.FindHexMouseClick((int)mouseClick.X, (int)mouseClick.Y);

            if (clickedHex == null)
            {
                Console.WriteLine("No hex was clicked.");
                board.BoardState.ActiveHex = null;
                hidePreview();
            }

            else
            {
                Console.WriteLine("Hex was clicked.");
                board.BoardState.ActiveHex = clickedHex;
                if (clickedHex.Cluster == null)
                {
                    hidePreview();
                }
                else
                {
                    showPreviewForCluster(clickedHex.Cluster);
                }
            }
            ((SpecialCanvas)canvas).Engine = engine;
        }






















    }
	
	
}
