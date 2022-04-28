/*
 * CorLinhaChamadoUtil.java
 *
 * Created on 4 de Julho de 2007, 12:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.util;

import br.org.flem.helpdesk.negocio.Chamado;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dbbarreto
 */
public class CorLinhaChamadoUtil {
    
    public static String getStyleClass(Chamado chamado) {
        String retorno = null;
        
        if (chamado != null) {
        
            retorno = verificaRegraAtivo24h(chamado);

            if (retorno == null) {
                retorno = verificaRegraQualquer48h(chamado);
            }

            if (retorno == null) {
                retorno = verificaRegraQualquer72h(chamado);
            }
        }
        
        if (retorno == null) {
            retorno = "";
        }
        
        return retorno;
    }
    
    private static String verificaRegraAtivo24h(Chamado chamado) {
        String retorno = null;
        
        long horas = (((new Date().getTime() - chamado.getUltimaAcao().getTime())/1000)/60)/60;
       
        if ((chamado.getSituacao().getId() == 1) && (horas >= 24)) {
            retorno = "linhaPreta";
        }
        
        return retorno;
    }
    
    private static String verificaRegraQualquer48h(Chamado chamado) {
        String retorno = null;
        
        long horas = (((new Date().getTime() - chamado.getUltimaAcao().getTime())/1000)/60)/60;
        
        if ((chamado.getSituacao().getId() != 2) && (horas >= 48) && (horas < 72)) {
            retorno = "linhaAmarela";
        }
        
        return retorno;
    }
    
    private static String verificaRegraQualquer72h(Chamado chamado) {
        String retorno = null;
        
        long horas = (((new Date().getTime() - chamado.getUltimaAcao().getTime())/1000)/60)/60;
        
        if ((chamado.getSituacao().getId() != 2) && (horas >= 72)) {
            retorno = "linhaVermelha";
        }
        
        return retorno;
    }
    
    public static void main(String[] args) throws ParseException {
        Date data1 = new Date();
        Date data2 = new SimpleDateFormat("dd/MM/yyyy").parse("01/07/2007");
        
        System.out.println(((((data1.getTime() - data2.getTime())/1000)/60)/60));
        
    }
    
}
