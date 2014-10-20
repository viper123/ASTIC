/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

/**
 *
 * @author Elvis
 */
public interface ICroiseCallback {
    public void OnDone();
    public void OnProgress(String filename);
}
