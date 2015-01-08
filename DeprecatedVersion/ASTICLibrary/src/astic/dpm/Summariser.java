/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm;

import astic.dpm.parsers.Parser;
import astic.drm.Summary;
import java.io.File;

/**
 *
 * @author Elvis
 */
@Deprecated
public abstract class Summariser {
    
    public abstract Summary summaryse(File file,Parser parser);
}
