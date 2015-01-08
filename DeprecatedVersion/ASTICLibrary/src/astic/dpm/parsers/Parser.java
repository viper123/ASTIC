/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers;

import java.io.File;

/**
 *
 * @author Elvis
 */
public interface Parser {
    public String [] getWords(File file);
}
