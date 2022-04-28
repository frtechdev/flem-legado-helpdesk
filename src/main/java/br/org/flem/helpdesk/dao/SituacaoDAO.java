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
import br.org.flem.helpdesk.negocio.Situacao;
import org.hibernate.Session;


/**
 *
 * @author mario
 */
public class SituacaoDAO extends BaseDAOAb<Situacao> {
    
    public SituacaoDAO() throws AcessoDadosException {
        super();
    }
    
    public SituacaoDAO(Session s) {
        super(s);
    }
    
    @Override
    protected Class<Situacao> getClasseDto() {
        return Situacao.class;
    }
    
}
