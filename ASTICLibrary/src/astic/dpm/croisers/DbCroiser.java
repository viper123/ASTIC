/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

import astic.io.db.DBHelper;
import astic.io.db.RequestQueue;

/**
 *
 * @author Elvis
 */
public abstract class DbCroiser extends Croiser {
    
    protected RequestQueue mDbQueue ;
    
    public DbCroiser() 
    {
        mDbQueue = DBHelper.getInstance().createRequestQueue();
    }
    
    public abstract void croise();
}
