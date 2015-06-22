using ASTIC_client.query;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2
{
    public class QueryWorker
    {
        public delegate void OnProgressChanged(int progress);
        public delegate void OnCompleted(QueryResult result);

        private BackgroundWorker worker;
        private OnProgressChanged onProgressChaged;
        private OnCompleted onCompleted;
        private  ASTICStream IO;
        private QueryResult result;
        private String queryStr;
        

        public QueryWorker(OnProgressChanged progressChanged,OnCompleted onCompleted,ASTICStream io)
        {
            this.IO = io;
            this.onCompleted = onCompleted;
            this.onProgressChaged = progressChanged;
            worker = new BackgroundWorker();
            worker.WorkerSupportsCancellation = true;
            worker.WorkerReportsProgress = true;
            worker.DoWork += new DoWorkEventHandler(work);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(completed);
            worker.ProgressChanged += new ProgressChangedEventHandler(progress);
        }

        public void work(object target, DoWorkEventArgs args)
        {
            worker.ReportProgress(10);
            Query query = new Query();
            query.setLevel(Query.LEVEL_2);
            string[] queryParts = queryStr.Split(' ');
            string[] queryArray;
            if (queryParts.Length > 1)
            {
                queryArray = new string[queryParts.Length + 1];
                int k = 0;
                foreach (String part in queryParts)
                {
                    queryArray[k++] = part;
                }
                queryArray[queryParts.Length] = queryStr;
            }
            else
            {
                queryArray = queryParts;
            }
            query.setQueryArray(queryArray);
            worker.ReportProgress(20);
            string queryString = JsonConvert.SerializeObject(query);
            worker.ReportProgress(25);
            IO.WriteString(queryString);
            worker.ReportProgress(30);
            String fromServer = IO.ReadString();
            worker.ReportProgress(70);
            result = JsonConvert.DeserializeObject<QueryResult>(fromServer);
            worker.ReportProgress(100);
            
        }

        public void completed(object target, RunWorkerCompletedEventArgs args)
        {
            onCompleted(result);
        }

        public void progress(object target, ProgressChangedEventArgs args)
        {
            onProgressChaged(args.ProgressPercentage);
        }

        private void canceled(object target, CancelEventArgs args)
        {
            if (queryStr != null)
            {
                worker.RunWorkerAsync();
            }
        }

        public void query(String q)
        {
            if (!worker.IsBusy)
            {
                queryStr = q;
                worker.RunWorkerAsync();
            }
            else
            {
                queryStr = q;
                worker.CancelAsync();
            }
        }

    }
}
