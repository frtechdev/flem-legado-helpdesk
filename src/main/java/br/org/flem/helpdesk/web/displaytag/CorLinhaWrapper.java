/*
 * DateWrapper.java
 *
 * Created on 13 de Setembro de 2006, 11:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.displaytag;

import org.displaytag.decorator.TableDecorator;

/**
 *
 * @author dbbarreto
 */
public class CorLinhaWrapper extends TableDecorator {
    protected Object evaluate(String propertyName) {
        Object retValue;
        
        retValue = super.evaluate(propertyName);
        return "Nega -> ";
    }
    
   
    
}
