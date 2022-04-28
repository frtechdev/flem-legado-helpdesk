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
import br.org.flem.helpdesk.negocio.Anexo;
import org.hibernate.Session;

/**
 *
 * @author mario
 */
public class AnexoDAO extends BaseDAOAb<Anexo> {
    
    public AnexoDAO() throws AcessoDadosException {
        super();
    }
    
    public AnexoDAO(Session s) {
        super(s);
    }
    
    @Override
    protected Class<Anexo> getClasseDto() {
        return Anexo.class;
    }
    
}
