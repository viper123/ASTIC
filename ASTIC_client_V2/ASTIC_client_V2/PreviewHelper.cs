using iTextSharp.text.pdf;
using iTextSharp.text.pdf.parser;
using PdfToText;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2
{
    public class PreviewHelper
    {
        Dictionary<String, List<String>> cache;
        public PreviewHelper()
        {
            cache = new Dictionary<string, List<string>>();
        }

        public List<String> getPreview(String[] query, String file)
        {
            if (cache.ContainsKey(file))
            {
                return cache[file];
            }
            else
            {
                List<String> preview = getPreviewParser(query, file);
                if (preview != null)
                {
                    cache.Add(file,preview);
                }
                return preview;
            }
        }

        public List<String> getPreviewParser(String [] query,String file)
        {
            List<string> preview = new List<string>();
            String ext = file.Substring(file.LastIndexOf(".")+1) ;
            if (ext.Equals("txt"))
            {
                //get preview txt;
                String content = GetTextFromTxtFile(file);
                return getPreview(content, query, file);
            }
            else if (ext.Equals("pdf"))
            {
                //get preview pdf
                String content = GetTextFromAllPages(file);
                return getPreview(content, query, file);
            }

            return preview;
        }

        private List<String> getPreview(String content,String [] query,String file)
        {
            List<String> preview = new List<String>();
            if (content != null)
            {
                StringBuilder lineBuilder = new StringBuilder();
                string[] lines = content.Split('\n');
                foreach (string q in query)
                {
                    foreach (string line in lines)
                    {
                        if (line.ToLower().Contains(q.ToLower()))
                        {
                            String copyLine = line;
                            lineBuilder = new StringBuilder();
                            lineBuilder.Append("<Bold>");
                            lineBuilder.Append(q);
                            lineBuilder.Append("</Bold>");
                            copyLine = line.Replace("<", "");
                            copyLine = copyLine.Replace(">", "");
                            String formatedLine = copyLine.ToLower()
                                .Replace(q.ToLower(), lineBuilder.ToString());
                            preview.Add(formatedLine);
                            break;
                        }
                    }
                }
            }
            return preview;
        }

        private  string GetTextFromAllPages(String pdfPath)
        {
            PdfReader reader = new PdfReader(pdfPath);

            StringWriter output = new StringWriter();

            for (int i = 1; i <= reader.NumberOfPages; i++)
                output.WriteLine(PdfTextExtractor.GetTextFromPage(reader, i, new SimpleTextExtractionStrategy()));

            return output.ToString();
        }

        private string GetTextFromTxtFile(String path)
        {
            return File.ReadAllText(path);
        }
        
    }
}
