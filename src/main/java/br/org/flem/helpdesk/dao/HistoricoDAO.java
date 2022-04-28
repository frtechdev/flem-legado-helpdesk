/*
 * HistoricoDAO.java
 *
 * Created on 7 de Novembro de 2006, 16:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.helpdesk.negocio.Historico;
import org.hibernate.Session;

/**
 *
 * @author mjpereira
 */
public class HistoricoDAO extends BaseDAOAb<Historico>{
    
    
    public HistoricoDAO() throws AcessoDadosException {
        super();
    }
    
    public HistoricoDAO(Session s) {
        super(s);
    }
    
    @Override
    protected Class<Historico> getClasseDto() {
        return Historico.class;
    }
    
    
    
}
