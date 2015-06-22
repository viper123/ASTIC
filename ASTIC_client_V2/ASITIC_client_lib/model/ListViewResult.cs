using System;
using System.Collections.Generic;
using System.IO;
using System.Text;
using System.Windows;

namespace ASTIC_client_V2
{
	public class ListViewResult
	{
		public NameClass Name {get;set;}
        public string FilePath { get; set; }
		public string FileTypeName {get;set;}
		public string Size {get;set;}
		public string Date {get ;set;}
        public List<String> Preview
        {
            get;
            set;
        }
		
		public ListViewResult()
		{
			
		}

        public ListViewResult(String filePath,List<String> preview,String [] query)
        {
            FilePath = filePath;
            FileTypeName = FileTypeFactory.FromFile(filePath).ToString();
            Name = new NameClass(System.IO.Path.GetFileName(filePath),getPreview(preview));
            FileInfo f = new FileInfo(filePath);
            Size = getStringWithMeasurement(f.Length);
            DateTime lastModified = System.IO.File.GetLastWriteTime(filePath);
            Date = lastModified.ToShortDateString();
        }

        public String getStringWithMeasurement(long length)
        {
            if (length > 1024 * 1024 * 1024)
            {
                return length / (1024 * 1024 * 1024) + "GB";
            }
            else if (length > 1024 * 1024)
            {
                return length / (1024 * 1024) + "Mb";
            }
            else if (length > 1024)
            {
                return length / 1024  + "kB";
            }
            else
            {
                return length + "bytes";
            }
        }

        public string getPreview(List<String> preview)
        {
            StringBuilder builder = new StringBuilder();
            foreach(String line in preview){
                builder.Append(line);
            }
            return builder.ToString();
            //if (file.Contains("."))
            //{
            //    String ext = file.Substring(file.LastIndexOf('.'));
            //    if(ext.ToLower().Equals(".pdf"))
            //    {
            //        return "";
            //    }
            //}
            //StringBuilder builder = new StringBuilder();
            //StringBuilder lineBuilder = new StringBuilder();
            //string[] lines = System.IO.File.ReadAllLines(file);
            //int selectedLines = 0;
            //foreach (string line in lines)
            //{
            //    foreach (String query in queryArray)
            //    {
            //        if (line.ToLower().Contains(query.ToLower()) && selectedLines < 2)
            //        {
            //            lineBuilder.Clear();
            //            lineBuilder.Append("<Bold>");
            //            lineBuilder.Append(query);
            //            lineBuilder.Append("</Bold>");
            //            String formatedLine = line.ToLower().Replace(query.ToLower(), lineBuilder.ToString());
            //            builder.Append(formatedLine);
            //            builder.Append("...");
            //            selectedLines++;
            //        }
            //    }
            //}
            //return builder.ToString();
        } 
	}
	
	public class NameClass
	{
		public string FilePath {get; set;}
		public string Preview {get; set;}

        public NameClass(string name,string preview)
        {
            FilePath = name;
            Preview = preview;
        }
	}
}