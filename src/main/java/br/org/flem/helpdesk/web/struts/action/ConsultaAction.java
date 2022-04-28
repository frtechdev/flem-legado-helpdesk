/*
 * RelatorioAction.java
 *
 * Created on 20 de Junho de 2007, 09:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dao.legado.chrbi.DepartamentoDAO;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.dao.ChamadoDAO;
import br.org.flem.helpdesk.dao.MeioComunicacaoDAO;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.negocio.Categoria;
import br.org.flem.helpdesk.negocio.Chamado;
import br.org.flem.helpdesk.negocio.MeioComunicacao;
import br.org.flem.helpdesk.negocio.Situacao;
import br.org.flem.helpdesk.relatorio.ChamadoRelatorio;
import br.org.flem.helpdesk.util.bd.UsuarioUtil;
import java.util.ArrayList;
import java.util.Collection;
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
 * @author dbbarreto
 */
public class ConsultaAction extends DispatchAction{
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("situacoes", new SituacaoDAO().obterTodos());
        request.setAttribute("categorias", new CategoriaDAO().obterCategoriasOrdenadas());
        request.setAttribute("origens",new MeioComunicacaoDAO().obterTodos());
        List<Departamento> lista = new ArrayList<Departamento>();
        Departamento dep = new Departamento();
        dep.setCodigo("TODOS COLABORADORES");
        lista.add(dep);
        lista.addAll(new DepartamentoDAO().obterDepartamentos());
        request.setAttribute("departamentos", lista);
        
        request.setAttribute("usuarios",this.getListaUsuarios(request));
        request.setAttribute("subcategorias",this.getListaSubCategorias(request));
        
        return mapping.findForward("consultaChamados");
    }
    
    public ActionForward consultaChamados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Collection<Chamado> chamados;
        Collection<ChamadoRelatorio> chamadosRelatorio = new ArrayList<ChamadoRelatorio>();
        String situacaoId = (String) dyna.get("situacao");
        String categoriaId = (String) dyna.get("categoria");
        String subcategoriaId = (String) dyna.get("subcategoria");
        String usuarioId = (String) dyna.get("usuario");
        String origemId = (String) dyna.get("origem");
        String departamentoId = (String) dyna.get("departamento");
        String assunto = (String) dyna.get("assunto");
        String historico = (String) dyna.get("historico");
        //Date inicio = new SimpleDateFormat("dd/MM/yyyy").parse((String) dyna.get("inicio"));
        //Date fim = new SimpleDateFormat("dd/MM/yyyy").parse((String) dyna.get("fim"));
        
        Situacao situacao = null;
        Categoria categoria = null;
        Categoria subcategoria = null;
        Integer usuario = null;
        MeioComunicacao origem = null;
        
        if (assunto != null) {
            request.getSession().setAttribute("ConsultaAction_assunto", assunto);
        }
        
        if (historico != null) {
            request.getSession().setAttribute("ConsultaAction_historico", historico);
        }
        
        if (situacaoId != null && !situacaoId.equals("")) {
            situacao = new Situacao();
            situacao.setId(Integer.valueOf(situacaoId));
            request.getSession().setAttribute("ConsultaAction_situacao", situacaoId);
        }
        
        if (categoriaId != null) {
            request.getSession().setAttribute("ConsultaAction_categoria", categoriaId);
            if (!categoriaId.equals("")) {
                categoria = new Categoria();
                categoria.setId(Integer.valueOf(categoriaId));
            }
        }
        
        if (subcategoriaId != null && !subcategoriaId.equals("")) {
            subcategoria = new Categoria();
            subcategoria.setId(Integer.valueOf(subcategoriaId));
            request.getSession().setAttribute("ConsultaAction_subcategoria", subcategoriaId);
        }
        
        if (usuarioId != null && !usuarioId.equals("")) {
            usuario = Integer.valueOf(usuarioId);
            request.getSession().setAttribute("ConsultaAction_usuario", usuarioId);
        }
        
        if (departamentoId != null) {
            request.getSession().setAttribute("ConsultaAction_departamento", departamentoId);
        }
        
        if (origemId != null && !origemId.equals("")) {
            origem = new MeioComunicacao();
            origem.setId(Integer.valueOf(origemId));
            request.getSession().setAttribute("ConsultaAction_origem", origemId);
        }
        
        chamados = new ChamadoDAO().obterChamadosPorSituacaoUsuarioOrigemPalavras(usuario, situacao, categoria, subcategoria, origem, assunto, historico);
        
        for (Chamado chamado: chamados) {
            ChamadoRelatorio chamadoRel = new ChamadoRelatorio(chamado);
            chamadosRelatorio.add(chamadoRel);
        }
        
        new UsuarioUtil().atualizaColaboradoresRelatorio(chamadosRelatorio);
        
        request.setAttribute("listaChamados", chamadosRelatorio);
        
        return unspecified(mapping, form, request, response);
    }
    
    private ArrayList<Usuario> getListaUsuarios(HttpServletRequest request) {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        
        Usuario usuario = new Usuario();
        usuario.setNome("Selecione:");
        
        usuarios.add(usuario);
        
        String departamento = (String) request.getSession().getAttribute("ConsultaAction_departamento");
        
        if (departamento != null && !departamento.equals("")) {
            usuarios.addAll(new UsuarioDAO().obterPorDepartamento(departamento));
        }
        
        return usuarios;
    }
    
    private ArrayList<Categoria> getListaSubCategorias(HttpServletRequest request) throws AcessoDadosException {
        ArrayList<Categoria> categorias = new ArrayList<Categoria>();
        
        Categoria categoria = new Categoria();
        categoria.setSigla("Selecione:");
        
        categorias.add(categoria);
        
        String pai = (String) request.getSession().getAttribute("ConsultaAction_categoria");
        
        if (pai != null && !pai.equals("")) {
            Categoria cPai = new Categoria();
            cPai.setId(Integer.valueOf(pai));
            categorias.addAll(new CategoriaDAO().obterSubCategorias(cPai));
        }
        
        return categorias;
    }
    
}
