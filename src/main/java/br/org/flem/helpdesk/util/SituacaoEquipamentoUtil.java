/*
 * UsuarioUtil.java
 *
 * Created on 20 de Junho de 2007, 16:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.util;

import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dto.AtivoFixo;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.helpdesk.relatorio.ChamadoRelatorio;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author dbbarreto
 */
public class SituacaoEquipamentoUtil {
    
    public void atualizaSituacoes(Collection<AtivoFixo> equipamentos) {
        for (AtivoFixo equipamento : equipamentos) {
            equipamento.setSituacao(converteSituacao(equipamento.getSituacao()));
        }
    }
    
    public String converteSituacao(String situacao) {
        String retorno = "";
        
        if (situacao.trim().equalsIgnoreCase("0")) {
            retorno = "Ativo em uso";
        }
        else if (situacao.trim().equalsIgnoreCase("1")) {
            retorno = "Ativo fora uso";
        }
        else if (situacao.trim().equalsIgnoreCase("2")) {
            retorno = "Inativo";
        }
        else {
            retorno = "";
        }
	return retorno;
    }
    
}
