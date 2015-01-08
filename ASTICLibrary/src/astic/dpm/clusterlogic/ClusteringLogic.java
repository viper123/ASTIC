/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

/**
 *
 * @author Elvis
 */
public abstract class ClusteringLogic<T,M> {
    public abstract T apply(String path,M param);
}
