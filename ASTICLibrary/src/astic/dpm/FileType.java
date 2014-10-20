/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm;

import java.io.File;

/**
 *
 * @author Elvis
 */
public enum FileType {
    App,Video,Audio,Photo,Text,Other;
    
    public static FileType fromExtension(String extension)
    {
        String [] mAppExt = {"exe"};
        String [] mVideo = {"mp4","avi"};
        String [] mAudio = {"mp3","wav"};
        String [] mPhoto = {"jpg","png"};
        String [] mText = {"txt"};
        for(String ext:mAppExt)
            if(ext.equals(extension))
                return App;
        for(String ext:mVideo)
            if(ext.equals(extension))
                return Video;
        for(String ext:mAudio)
            if(ext.equals(extension))
                return Audio;
        for(String ext:mPhoto)
            if(ext.equals(extension))
                return Photo;
        for(String ext:mText)
            if(ext.equals(extension))
                return Text;
        return Other;
    } 
    
    public static FileType fromFile(File file)
    {
        String name = file.getName();
        int index = name.lastIndexOf('.');
        String ext = null;
        if(index>0)
            ext = name.substring(index+1);
        //System.out.println(""+ext);
        if(ext!=null&&ext.length()>0)
            return fromExtension(ext);
        else
            return FileType.Other;
    }
  
   
}
