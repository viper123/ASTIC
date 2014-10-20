/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers;

import astic.dpm.parsers.lang.SupportedLanguage1;
import astic.io.IOHelper;
import java.io.File;

/**
 *
 * @author Elvis
 */
public class TxtParser extends TextDocumentParser {

    public TxtParser()
    {
        super(new SupportedLanguage1());
    }
    
    @Override
    public String parseContent(File file) 
    {
        return IOHelper.readContentFromFile(file);
    } 
    
}
