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
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.negocio.Categoria;
import java.util.ArrayList;
import java.util.List;
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
public class CategoriaAction extends DispatchAction {
    
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("lista",new CategoriaDAO().obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("cadastro");
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        CategoriaDAO categoriaDao = null;
        try {
            categoriaDao = new CategoriaDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Categoria categoria = new Categoria();
        categoria.setSigla(dyna.getString("sigla"));
        categoria.setDescricao(dyna.getString("descricao"));
        try {
            categoriaDao.inserir(categoria);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("lista",categoriaDao.obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }
    
    public ActionForward excluir(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
        CategoriaDAO categoriaDao = null;
        try {
            categoriaDao = new CategoriaDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Categoria categoria = null;
        try {
            categoria = categoriaDao.obterPorPk(Integer.valueOf(request.getParameter("id")));
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        if(categoria!=null && categoria.getFilho().isEmpty()){
            try {
                categoriaDao.excluir(categoria);
            } catch (AcessoDadosException ex) {
                ex.printStackTrace();
            }
        }else{
            List lista = new ArrayList();
            lista.add("A categoria não pode ser excluida!");
            request.setAttribute(MensagemTag.LISTA_MENSAGENS,lista);
        }
        
        try {
            request.setAttribute("lista",categoriaDao.obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        
        return mapping.findForward("cadastro");
    }
    
    public ActionForward excluirSubCategoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        CategoriaDAO categoriaDao = null;
        try {
            categoriaDao = new CategoriaDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Categoria pai = null;
        try {
            Categoria categoria = categoriaDao.obterPorPk(Integer.valueOf(request.getParameter("id")));
            pai = categoria.getPai();
            categoriaDao.excluir(categoria);
            dyna.set("idPai",pai.getId()+"");
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            request.setAttribute("categoria",pai);
            request.setAttribute("lista",pai.getFilho());
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapping.findForward("subCategoria");
    }
    
    public ActionForward subCategoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        CategoriaDAO categoriaDao = null;
        try {
            categoriaDao = new CategoriaDAO();
            Categoria categoria = categoriaDao.obterPorPk(Integer.valueOf(request.getParameter("id")));
            request.setAttribute("categoria",categoria);
            request.setAttribute("lista",categoria.getFilho());
            dyna.set("idPai",categoria.getId()+"");
            dyna.set("id","");
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("subCategoria");
    }
    
    public ActionForward adicionarSubCategoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        ActionForward actionForward = null;
        if (dyna.getString("id") != null && !dyna.getString("id").equals("")) {
            actionForward = this.alterarSubCategoria(mapping, form, request, response);
        } else {
            CategoriaDAO categoriaDao = null;
            try {
                categoriaDao = new CategoriaDAO();
                Categoria categoria = new Categoria();
                categoria.setSigla(dyna.getString("sigla"));
                categoria.setDescricao(dyna.getString("descricao"));
                categoria.setFaq(dyna.getString("faq"));
                Categoria pai = categoriaDao.obterPorPk(Integer.valueOf(dyna.getString("idPai")));
                categoria.setPai(pai);
                categoriaDao.inserir(categoria);
                request.setAttribute("categoria",pai);
                request.setAttribute("lista",pai.getFilho());
                dyna.set("idPai",pai.getId()+"");
            } catch (AcessoDadosException ex) {
                ex.printStackTrace();
            }
            actionForward = mapping.findForward("subCategoria");
        }
        
        return actionForward;
    }
    
    public ActionForward alterarSubCategoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        CategoriaDAO categoriaDao = null;
        try {
            categoriaDao = new CategoriaDAO();
            Categoria categoria = new CategoriaDAO().obterPorPk(Integer.valueOf(dyna.getString("id")));
            categoria.setSigla(dyna.getString("sigla"));
            categoria.setDescricao(dyna.getString("descricao"));
            categoria.setFaq(dyna.getString("faq"));
            Categoria pai = categoriaDao.obterPorPk(Integer.valueOf(dyna.getString("idPai")));
            categoria.setPai(pai);
            categoriaDao.alterar(categoria);
            dyna.reset(mapping, request);
            request.setAttribute("categoria",pai);
            request.setAttribute("lista",pai.getFilho());
            dyna.set("idPai",pai.getId()+"");
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("subCategoria");
    }
    
    public ActionForward selecionarSubCategoria(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        CategoriaDAO categoriaDao = null;
        try {
            categoriaDao = new CategoriaDAO();
            Categoria categoria = new CategoriaDAO().obterPorPk(Integer.valueOf(request.getParameter("id")));
            dyna.set("id", categoria.getId()+"");
            dyna.set("sigla", categoria.getSigla());
            dyna.set("descricao", categoria.getDescricao());
            dyna.set("faq", categoria.getFaq());
            
            request.setAttribute("categoria",categoria.getPai());
            request.setAttribute("lista",categoria.getPai().getFilho());
            dyna.set("idPai",categoria.getPai().getId()+"");
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("subCategoria");
    }
}
