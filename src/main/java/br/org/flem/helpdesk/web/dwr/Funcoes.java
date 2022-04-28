/*
 * Funcoes.java
 *
 * Created on 9 de Outubro de 2006, 11:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.dwr;



import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.negocio.Categoria;
import br.org.flem.helpdesk.negocio.Situacao;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.validator.GenericValidator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;




/**
 *
 * @author MJPEREIRA
 */
@RemoteProxy
public class Funcoes {
    
    @RemoteMethod
    public List subCategoria(String indice) {
        List lista = new ArrayList();
        if (GenericValidator.isLong(indice)) {
            try {
                Categoria pai = new Categoria();
                pai.setId(Integer.valueOf(indice));
                lista = new CategoriaDAO().obterSubCategorias(pai);
            } catch (AcessoDadosException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
    
    @RemoteMethod
    public String faqSubCategoria(String indice) {
        String retorno = "";
        if (GenericValidator.isLong(indice)) {
            try {
                Categoria sub = new Categoria();
                sub = new CategoriaDAO().obterPorPk(Integer.valueOf(indice));
                //lista = new CategoriaDAO().obterSubCategorias(pai);
                if (sub != null && sub.getFaq() != null && !sub.getFaq().equals("")) {
                    retorno = sub.getFaq();
                }
            } catch (AcessoDadosException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }
    
    @RemoteMethod
    public List usuarioDepartamento(String indice) {
       List lista = new ArrayList();
        if (!GenericValidator.isBlankOrNull(indice)) {
            UsuarioDAO uDao = new UsuarioDAO();
            if(!indice.equals("TODOS COLABORADORES")) {
                lista = uDao.obterPorDepartamento(indice);
            } else {
                lista = uDao.obterTodos();
            }
        }
        return lista;
    }
    
    @RemoteMethod
    public String respostaAutomatica(String indice) {
        String retorno = "";
        if (GenericValidator.isLong(indice)) {
            try {
                Situacao sit = new Situacao();
                sit = new SituacaoDAO().obterPorPk(Integer.valueOf(indice));
                //lista = new CategoriaDAO().obterSubCategorias(pai);
                if (sit != null && sit.getResposta() != null && !sit.getResposta().equals("")) {
                    retorno = sit.getResposta();
                }
            } catch (AcessoDadosException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }
    
}