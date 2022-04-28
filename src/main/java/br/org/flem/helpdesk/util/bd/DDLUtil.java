/*
 * DDLUtil.java
 *
 * Created on 19 de Setembro de 2006, 13:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.util.bd;


import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 *
 * @author mjpereira
 */
public class DDLUtil {
    
    /** Creates a new instance of DDLUtil */
    public DDLUtil() {
    }
    
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        conf.configure();         
        SchemaExport se = new SchemaExport(conf);
        se.setFormat(true);
        se.setDelimiter(";");
        se.setOutputFile("helpdesk.ddl");
        se.create(true,true);
        se.drop(true,true);
        
    }
}
