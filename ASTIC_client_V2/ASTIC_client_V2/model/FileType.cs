using System;
using System.Collections.Generic;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace ASTIC_client_V2
{
	public enum FileType
	{
		App,
        Video,
        Audio,
        Photo,
        Text,
         Other,
        All
        
	}

    public class FileTypeFactory
    {
        public static FileType FromFile(String path)
        {
            String fileName = System.IO.Path.GetFileName(path);
            if(fileName.Contains("."))
            {
                return FromExt(fileName.Substring(fileName.LastIndexOf('.')));
            }else{
                return FileType.Other;
            }
        }

        private static FileType FromExt(String ext)
        {
            switch (ext)
            {
                case ".txt" :
                case ".pdf" :
                    return FileType.Text;
                case ".jpg":
                case ".png":
                    return FileType.Photo;
                case ".mp4":
                case ".avi":
                case ".mkv":
                    return FileType.Video;
                case ".mp3":
                case ".wav":
                    return FileType.Audio;
                case ".exe":
                    return FileType.App;
            }
            return FileType.Other;
        }
    }
}