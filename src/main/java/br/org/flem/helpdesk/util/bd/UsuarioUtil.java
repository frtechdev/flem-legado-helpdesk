/*
 * UsuarioUtil.java
 *
 * Created on 20 de Junho de 2007, 16:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.util.bd;

import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.helpdesk.relatorio.ChamadoRelatorio;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author dbbarreto
 */
public class UsuarioUtil {
    
    private Map<Integer, Usuario> usuarios = null;
    
    /** Creates a new instance of UsuarioUtil */
    public UsuarioUtil() {
       usuarios = new UsuarioDAO().obterTodosMap();
    }
    
    public void atualizaColaboradoresRelatorio(Collection<ChamadoRelatorio> chamadosRelatorio) {
        for (ChamadoRelatorio chamado : chamadosRelatorio) {
            chamado.setColaborador(usuarios.get(chamado.getUsuario()));
        }
    }
    
    public void atualizaColaboradoresListagem(Collection<ChamadoRelatorio> chamadosRelatorio) {
        for (ChamadoRelatorio chamado : chamadosRelatorio) {
            chamado.setColaborador(usuarios.get(chamado.getResponsavel()));
        }
    }
    
    public void atualizaSolicitanteColaboradores(Collection<ChamadoRelatorio> chamadosRelatorio) {
        for (ChamadoRelatorio chamado : chamadosRelatorio) {
            chamado.setSolicitante(usuarios.get(chamado.getUsuario()));
            chamado.setColaborador(usuarios.get(chamado.getResponsavel()));
        }
    }
    
}
