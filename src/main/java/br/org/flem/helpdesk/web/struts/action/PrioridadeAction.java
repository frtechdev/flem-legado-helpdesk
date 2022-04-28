/*
 * PrioridadeAction.java
 *
 * Created on 10 de Setembro de 2006, 17:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.helpdesk.dao.PrioridadeDAO;
import br.org.flem.helpdesk.negocio.Prioridade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mario
 */
public class PrioridadeAction extends DispatchAction {

    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista",new PrioridadeDAO().obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("cadastro");
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        PrioridadeDAO prioridadeDao = null;
        try {
            prioridadeDao = new PrioridadeDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Prioridade prioridade = new Prioridade();
        prioridade.setSigla(dyna.getString("sigla"));
        prioridade.setDescricao(dyna.getString("descricao"));
        try {
            prioridadeDao.inserir(prioridade);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",prioridadeDao.obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }

     public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        PrioridadeDAO prioridadeDao = null;
        try {
            prioridadeDao = new PrioridadeDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Prioridade prioridade = new Prioridade();
        prioridade.setId(Integer.valueOf(request.getParameter("id")));
        try {
            prioridadeDao.excluir(prioridade);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",prioridadeDao.obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }
    
}
