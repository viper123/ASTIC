/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.io.db;

/**
 *
 * @author Elvis
 */
public interface IDBListener<T> {
    public void onSucces(T result) ;
    public void onError(Exception e);
}
