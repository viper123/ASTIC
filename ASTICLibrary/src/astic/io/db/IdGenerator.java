/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.io.db;

import astic.io.IOHelper;
import java.lang.management.ManagementFactory;

/**
 *
 * @author Elvis
 */
public class IdGenerator {
    private static final String FILE="generator";
    private static long ID_CONTER ;
    static
    {
        try{
            ID_CONTER = (long) IOHelper.loadObject(FILE) ;
        }catch(Exception e){
            ID_CONTER = 0 ;
        }
    }
    public static long generateUniqueID()
    {
        ++ID_CONTER;
        IOHelper.saveObject(FILE, ID_CONTER); 
        return ID_CONTER ;
           
    }
}
