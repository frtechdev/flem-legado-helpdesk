/*
 * SituacaoAction.java
 *
 * Created on 10 de Setembro de 2006, 17:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.helpdesk.dao.MeioComunicacaoDAO;
import br.org.flem.helpdesk.negocio.MeioComunicacao;
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
public class MeioComunicacaoAction extends DispatchAction {

    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista",new MeioComunicacaoDAO().obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("cadastro");
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        MeioComunicacaoDAO meioComunicacaoDao = null;
        try {
            meioComunicacaoDao = new MeioComunicacaoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        MeioComunicacao meioComunicacao = new MeioComunicacao();
        meioComunicacao.setSigla(dyna.getString("sigla"));
        meioComunicacao.setDescricao(dyna.getString("descricao"));
        try {
            meioComunicacaoDao.inserir(meioComunicacao);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",meioComunicacaoDao.obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }

     public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        MeioComunicacaoDAO meioComunicacaoDao = null;
        try {
            meioComunicacaoDao = new MeioComunicacaoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        MeioComunicacao meioComunicacao = new MeioComunicacao();
        meioComunicacao.setId(Integer.valueOf(request.getParameter("id")));
        try {
            meioComunicacaoDao.excluir(meioComunicacao);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",meioComunicacaoDao.obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }
    
}
