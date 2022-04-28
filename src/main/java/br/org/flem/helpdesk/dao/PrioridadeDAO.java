/*
 * ChamadoDAO.java
 *
 * Created on 10 de Setembro de 2006, 12:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.helpdesk.negocio.Prioridade;
import org.hibernate.Session;


/**
 *
 * @author mario
 */
public class PrioridadeDAO extends BaseDAOAb<Prioridade> {
    
    public PrioridadeDAO() throws AcessoDadosException {
        super();
    }
    
    public PrioridadeDAO(Session s) {
        super(s);
    }
    
    @Override
    protected Class<Prioridade> getClasseDto() {
        return Prioridade.class;
    }
    
}
