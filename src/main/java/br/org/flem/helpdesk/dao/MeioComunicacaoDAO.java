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
import br.org.flem.helpdesk.negocio.MeioComunicacao;
import org.hibernate.Session;

/**
 *
 * @author mario
 */
public class MeioComunicacaoDAO extends BaseDAOAb<MeioComunicacao> {
    
    public MeioComunicacaoDAO() throws AcessoDadosException {
        super();
    }
    
    public MeioComunicacaoDAO(Session s) {
        super(s);
    }
    
    @Override
    protected Class<MeioComunicacao> getClasseDto() {
        return MeioComunicacao.class;
    }
    
}
