using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ASTIC_client_V2
{
    public class FileLauncher
    {
        public static void launchFile( String fn)
        {
            System.Diagnostics.Process.Start(fn);
            ////majority was taken from
            ////http://stackoverflow.com/questions/24954/windows-list-and-launch-applications-associated-with-an-extension
            //const string extPathTemplate = @"HKEY_CLASSES_ROOT\{0}";
            //const string cmdPathTemplate = @"HKEY_CLASSES_ROOT\{0}\shell\open\command";

            //string ext = Path.GetExtension(fn);

            //var extPath = string.Format(extPathTemplate, ext);

            //var docName = Registry.GetValue(extPath, string.Empty, string.Empty) as string;
            //if (!string.IsNullOrEmpty(docName))
            //{
            //    // 2. Find out which command is associated with our extension
            //    var associatedCmdPath = string.Format(cmdPathTemplate, docName);
            //    var associatedCmd = Registry.GetValue(associatedCmdPath, string.Empty, string.Empty) as string;

            //    if (!string.IsNullOrEmpty(associatedCmd))
            //    {
            //        //Console.WriteLine("\"{0}\" command is associated with {1} extension", associatedCmd, ext);
            //        var p = new Process();
            //        p.StartInfo.FileName = associatedCmd.Split(' ')[0];
            //        string s2 = associatedCmd.Substring(p.StartInfo.FileName.Length + 1);
            //        s2 = s2.Replace("%1", string.Format("\"{0}\"", fn));
            //        p.StartInfo.Arguments = s2;//string.Format("\"{0}\"", fn);
            //        p.Start();
            //    }
            //}
        }
    }
}
