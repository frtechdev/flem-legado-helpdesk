/*
 * RelatorioAction.java
 *
 * Created on 20 de Junho de 2007, 09:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fwe.exception.RelatorioSemDadosException;
import br.org.flem.fw.persistencia.dto.AtivoFixo;
import br.org.flem.fw.service.impl.GEMImpl;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.dao.ChamadoDAO;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.negocio.Categoria;
import br.org.flem.helpdesk.negocio.Chamado;
import br.org.flem.helpdesk.negocio.Situacao;
import br.org.flem.helpdesk.relatorio.ChamadoRelatorio;
import br.org.flem.helpdesk.relatorio.HelpDeskCriadorRelatorio;
import br.org.flem.helpdesk.util.SituacaoEquipamentoUtil;
import br.org.flem.helpdesk.util.bd.UsuarioUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author dbbarreto
 */
public class RelatorioAction extends DispatchAction{
    
    public ActionForward filtroListagemChamados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("situacoes", new SituacaoDAO().obterTodos());
        request.setAttribute("categorias", new CategoriaDAO().obterCategoriasOrdenadas());
        return mapping.findForward("filtroListagemChamados");
    }
    
    public ActionForward listagemChamados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;
        Collection<Chamado> chamados;
        Collection<ChamadoRelatorio> chamadosRelatorio = new ArrayList<ChamadoRelatorio>();
        String situacaoId = (String) dyna.get("situacao");
        String categoriaId = (String) dyna.get("categoria");  
        String subcategoriaId = (String) dyna.get("subcategoria");
        Date inicio = dyna.get("inicio") != null && !dyna.get("inicio").equals("") ? new SimpleDateFormat("dd/MM/yyyy").parse((String) dyna.get("inicio")) : null;
        Date fim = dyna.get("fim") != null && !dyna.get("fim").equals("") ? new SimpleDateFormat("dd/MM/yyyy").parse((String) dyna.get("fim")) : null;
        
        Situacao situacao = null;
        Categoria categoria = null;
        Categoria subcategoria = null;
        boolean naoFechados = false;
                
        if (situacaoId != null && !situacaoId.equals("")) {
            //System.out.println(">>>>>>>>>>>> "+situacaoId);
            if (situacaoId.equalsIgnoreCase("Nao")) {
                naoFechados = true;
            }
            else {
                situacao = new Situacao();
                situacao.setId(Integer.valueOf(situacaoId));
            }
        } 
        
        if (categoriaId != null && !categoriaId.equals("")) {
            categoria = new Categoria();
            categoria.setId(Integer.valueOf(categoriaId));
        } 
        
        if (subcategoriaId != null && !subcategoriaId.equals("")) {   
            subcategoria = new Categoria();
            subcategoria.setId(Integer.valueOf(subcategoriaId));
        }
        
        chamados = new ChamadoDAO().obterChamadosPorPeriodoSituacao(inicio, fim, situacao, categoria, subcategoria, naoFechados);
        
        for (Chamado chamado: chamados) {
            ChamadoRelatorio chamadoRel = new ChamadoRelatorio(chamado);
            chamadosRelatorio.add(chamadoRel);
        }
        
        new UsuarioUtil().atualizaColaboradoresRelatorio(chamadosRelatorio);
        
        Map parametros = new HashMap();
        parametros.put("dataInicial", inicio != null ? (String) dyna.get("inicio") : " - ");
        parametros.put("dataFinal", fim != null ? (String) dyna.get("fim") : " - ");
        parametros.put("situacao", situacao != null ? situacao.getSigla() : "Todas");
        HelpDeskCriadorRelatorio criadorRelatorio = new HelpDeskCriadorRelatorio();
        
        //criadorRelatorio.exportaRelatorioPDF(request, response, arquivoRelatorio, parametros, chamadosRelatorio);
        
        try {
            criadorRelatorio.exportaRelatorioPDF(request, response, "/relatorio/relListagemChamados.jasper", parametros, chamadosRelatorio);
        }
        catch(JRException jre) {
            ArrayList mensagens = new ArrayList();
            mensagens.add("Ocorreu um erro na geração do relatório.\n"+jre.getMessage());
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        catch(RelatorioSemDadosException rsde) {
            ArrayList mensagens = new ArrayList();
            mensagens.add("Não existem dados a serem exibidos nesse relatório");
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return filtroListagemChamados(mapping, form, request, response);
    }
    
    public ActionForward listagemEquipamentos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String codigo = (String) request.getSession().getAttribute("EquipamentoAction_codigo");
        String descricao = (String) request.getSession().getAttribute("EquipamentoAction_descricao");
        String responsavel = (String) request.getSession().getAttribute("EquipamentoAction_responsavel");
        String detentor = (String) request.getSession().getAttribute("EquipamentoAction_detentor");
        String tombo = (String) request.getSession().getAttribute("EquipamentoAction_tombo");
        String situacao = (String) request.getSession().getAttribute("EquipamentoAction_situacao");
        String localizacao = (String) request.getSession().getAttribute("EquipamentoAction_localizacao");
        String serial = (String) request.getSession().getAttribute("EquipamentoAction_serial");
        String fabricante = (String) request.getSession().getAttribute("EquipamentoAction_fabricante");
        String modelo = (String) request.getSession().getAttribute("EquipamentoAction_modelo");
        
        AtivoFixo ativoFixo = new AtivoFixo();
        ativoFixo.setCodigo(codigo == null ? "" : codigo);
        ativoFixo.setDescricao(descricao == null ? "" : descricao);
        ativoFixo.setDetentor(detentor == null ? "" : detentor);
        ativoFixo.setFabricante(fabricante == null ? "" : fabricante);
        ativoFixo.setLocalizacao(localizacao == null ? "" : localizacao);
        ativoFixo.setModelo(modelo == null ? "" : modelo);
        ativoFixo.setResponsavel(responsavel == null ? "" : responsavel);
        ativoFixo.setSituacao(situacao == null ? "" : situacao);
        ativoFixo.setSerial(serial == null ? "" : serial);
        ativoFixo.setTombo(tombo == null ? "" : tombo);
        
        Collection equipamentos = new GEMImpl().obterEquipamentosInformaticaFiltro(ativoFixo);
        
        new SituacaoEquipamentoUtil().atualizaSituacoes(equipamentos);
        
        String arquivoRelatorio = new String("/relatorio/relListagemEquipamentos.jasper");
        
        Map parametros = new HashMap();
        HelpDeskCriadorRelatorio criadorRelatorio = new HelpDeskCriadorRelatorio();
        
        try {
            criadorRelatorio.exportaRelatorioPDF(request, response, arquivoRelatorio, parametros, equipamentos);
        }
        catch(JRException jre) {
            ArrayList mensagens = new ArrayList();
            mensagens.add("Ocorreu um erro na geração do relatório.\n"+jre.getMessage());
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        catch(RelatorioSemDadosException rsde) {
            ArrayList mensagens = new ArrayList();
            mensagens.add("Não existem dados a serem exibidos nesse relatório");
            request.setAttribute(MensagemTag.LISTA_MENSAGENS, mensagens);
        }
        return new EquipamentoAction().unspecified(mapping, form, request, response);
    }
    
}
