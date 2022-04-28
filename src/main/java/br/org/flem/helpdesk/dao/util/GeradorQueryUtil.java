/*
 * GeradorQueryUtil.java
 *
 * Created on 08/08/2007, 11:06:49
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.dao.util;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author dbbarreto
 */
public class GeradorQueryUtil {

    public static Criterion montaBuscaTextoComplexa(String texto, String atributo) {
        Conjunction retorno = Restrictions.conjunction();

        String[] parseSinalMais = texto.trim().split("\\+");
        //System.out.println("Parse Sinal Mais: " + parseSinalMais);
        //System.out.print("(");
        for (int i = 0; i < parseSinalMais.length; i++) {
            
            if (!parseSinalMais[i].trim().equals("")) {
                
                if (parseSinalMais[i].contains("\"")) {
                    String[] parseAspas = parseSinalMais[i].trim().split("\"");
                    //System.out.println("Parse Aspas: " + parseAspas);
                    Conjunction palavraToda = Restrictions.conjunction();
                    //System.out.print("(");
                    for (int j = 0; j < parseAspas.length; j++) {
                        if (!parseAspas[j].trim().equals("")) {
                            palavraToda.add(Restrictions.like(atributo, "%"+parseAspas[j].trim()+"%"));
                            //System.out.print(atributo + " like " + "%"+parseAspas[j].trim()+"%"+ " and ");
                        }
                    }
                    retorno.add(palavraToda);
                    //System.out.print(")");
                } 
                else {
                    String[] parseEspaco = parseSinalMais[i].trim().split(" ");
                    //System.out.println("Parse Espaco: " + parseEspaco);
                    for (int j = 0; j < parseEspaco.length; j++) {
                        if (!parseEspaco[j].equals("")) {
                            retorno.add(Restrictions.like(atributo, "%"+parseEspaco[j].trim()+"%"));
                            //System.out.print(atributo + " like " + "%"+parseEspaco[j].trim()+"%" + " or ");
                        }
                    }
                }
            
            }
        
        }
        //System.out.println(")");
        return retorno;
    }
   
}
