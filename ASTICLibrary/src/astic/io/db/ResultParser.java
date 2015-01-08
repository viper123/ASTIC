/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.io.db;

import java.sql.ResultSet;

/**
 *
 * @author Elvis
 */
public abstract class ResultParser<T> {
    public abstract T parse(ResultSet rs);
}
