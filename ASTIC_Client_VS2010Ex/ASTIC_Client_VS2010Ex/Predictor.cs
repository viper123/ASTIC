using ASTIC_client.query;
using ASTIC_client_V2;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;

namespace ASTIC_client
{
    class Predictor
    {
        BackgroundWorker worker;
        String queryStr;
        public delegate void OnDone(List<String> predictions);
        List<String> predictions;
        private byte[] buffer = new byte[1048576];
        Cache cache;
        OnDone done;
        ASTICStream io;

        public Predictor(OnDone onDone,ASTICStream stream)
        {
            cache = new Cache();
            worker = new BackgroundWorker();
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += new DoWorkEventHandler(work);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(completed);
            done = onDone;
            this.io = stream;
        }

        public void completed(object target, RunWorkerCompletedEventArgs args)
        {
            done(predictions);
            predictions = null;
            queryStr = null;
        }

        public void work(object target, DoWorkEventArgs args)
        {
            List<String> cached = cache.get(queryStr);
            if (cached != null && cached.Count > 0)
            {
                predictions = cached;
                return;
            }
            Query query = new Query();
            query.setLevel(Query.LEVEL_0);
            query.setQuery(queryStr);
            string output = JsonConvert.SerializeObject(query);
            io.WriteString(output);
            String fromServer = io.ReadString();
            QueryResult result = JsonConvert.DeserializeObject<QueryResult>(fromServer);
            if (result.getPredictions().Count > 0)
            {
                cache.put(queryStr, result.getPredictions());
                predictions = result.getPredictions();
            }
        }

        private void canceled(object target, CancelEventArgs args)
        {
            if (queryStr != null)
            {
                worker.RunWorkerAsync();
            }
        }

        public void predict(String q)
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
