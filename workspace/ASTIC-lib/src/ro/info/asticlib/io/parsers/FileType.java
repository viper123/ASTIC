/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Elvis
 */
public enum FileType {
    App("ext"),
    Video("mp4","avi"),
    Audio("mp3","wav"),
    Photo("jpg","png"),
    Text("txt","pdf","ppt","pptx","doc","docx"),
    Other;
    
    public List<String> acceptedExtensions;
    public String selectedExtension;
    
    private FileType(String ...ext){
    	this.acceptedExtensions = new ArrayList<>();
    	if(ext!=null && ext.length>0){
    		this.acceptedExtensions.addAll(Arrays.asList(ext));
    	}
    }
    
    public static FileType fromExtension(String extension)
    {
    	extension = extension.toLowerCase();
        for(FileType type:values()){
        	if(type.acceptedExtensions!=null &&
        			type.acceptedExtensions.contains(extension)){
        		type.selectedExtension = extension;
        		return type;
        	}
        }
        return Other;
    } 
    
    public static FileType fromFile(File file)
    {
        String name = file.getName();
        int index = name.lastIndexOf('.');
        String ext = null;
        if(index>0)
            ext = name.substring(index+1);
        if(ext!=null&&ext.length()>0)
            return fromExtension(ext);
        else
            return FileType.Other;
    }
  
    public enum Extension{
    	Pdf("pdf"),Txt("txt"),
    	Docx("docx"),Doc("doc"),
    	Ppt("ppt"),Pptx("pptx");
    	
    	public String extension;
    	
    	private Extension(String str){
    		this.extension = str;
    	}
    }
   
}
