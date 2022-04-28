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
import br.org.flem.helpdesk.negocio.Categoria;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author mario
 */
public class CategoriaDAO extends BaseDAOAb<Categoria> {
    
    public CategoriaDAO() throws AcessoDadosException {
        super();
    }
    
    public CategoriaDAO(Session s) {
        super(s);
    }
    
    public List<Categoria> obterCategoriasOrdenadas() throws AcessoDadosException {
        try {
            return this.session.createQuery("from Categoria c where c.pai is null order by  c.sigla").list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Categoria> obterSubCategorias(Categoria pai) throws AcessoDadosException {
        try {
            return this.session.createQuery("from Categoria c where c.pai = :categoria order by  c.sigla").setEntity("categoria",pai).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    @Override
    protected Class<Categoria> getClasseDto() {
        return Categoria.class;
    }
    
    
    
}
