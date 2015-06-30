using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2
{
    public class Previewer
    {
        public delegate void OnDone(Dictionary<String,List<String>> previews);
        private BackgroundWorker worker;
        private PreviewHelper previewHelper;
        private OnDone onDone;
        private Dictionary<String, List<String>> preview;
        private List<String> file;
        private String[] query;

        public Previewer(OnDone done)
        {
            previewHelper = new PreviewHelper();
            worker = new BackgroundWorker();
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += new DoWorkEventHandler(work);
            worker.RunWorkerCompleted += new RunWorkerCompletedEventHandler(completed);
            onDone = done;
        }

        public void completed(object target, RunWorkerCompletedEventArgs args)
        {
            onDone(preview);
            preview = null;
            query = null;
        }

        public void work(object target, DoWorkEventArgs args)
        {
            if (file != null)
            {
                foreach (String f in file)
                {
                    preview.Add(f,previewHelper.getPreview(query, f));
                }
            }
        }

        private void canceled(object target, CancelEventArgs args)
        {
            if (file != null)
            {
                worker.RunWorkerAsync();
            }
        }

        public void getPreview(List<String> file,String [] query)
        {
            this.file = file;
            this.query = query;
            if (!worker.IsBusy)
            {
                worker.RunWorkerAsync();
            }
            else
            {
                worker.CancelAsync();
            }
        }
    }
}
