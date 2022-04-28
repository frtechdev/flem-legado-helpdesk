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
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.negocio.Situacao;
import java.util.ArrayList;
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
public class SituacaoAction extends DispatchAction {

    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("situacoes",new SituacaoDAO().obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("resposta");
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        SituacaoDAO situacaoDao = null;
        try {
            situacaoDao = new SituacaoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Situacao situacao = new Situacao();
        situacao.setSigla(dyna.getString("sigla"));
        situacao.setDescricao(dyna.getString("descricao"));
        try {
            situacaoDao.inserir(situacao);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",situacaoDao.obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }

     public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        SituacaoDAO situacaoDao = null;
        try {
            situacaoDao = new SituacaoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Situacao situacao = new Situacao();
        situacao.setId(Integer.valueOf(request.getParameter("id")));
        try {
            situacaoDao.excluir(situacao);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",situacaoDao.obterTodos());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }
     
     public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
       
        try {
            SituacaoDAO situacaoDAO = new SituacaoDAO();
            Situacao situacao = situacaoDAO.obterPorPk(Integer.valueOf( dyna.getString("situacao")));
            situacao.setResposta(dyna.getString("resposta"));
            situacaoDAO.alterar(situacao);
            ArrayList erros = new ArrayList();
            erros.add("Resposta Autom�tica alterada com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS,erros);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        /*try {
            Chamado c= new Chamado();
            UserLogin usuario = (UserLogin) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            c.setUsuario(usuario.getId());
            request.setAttribute("lista",new ChamadoDAO().obterPorFiltro(c));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }*/
        
        request.setAttribute("acao", "Situacao.do");
        return mapping.findForward("redirect");
    }
    
}
