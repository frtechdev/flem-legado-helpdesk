/*
 * EquipamentoAction.java
 *
 * Created on 19/09/2007, 18:50:28
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fw.persistencia.dto.AtivoFixo;
import br.org.flem.fw.service.impl.GEMImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author mjpereira
 */
public class EquipamentoAction extends DispatchAction{

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
        
        /*Ao invés de chamar esse "obterTodos", tem que chamar um "obterProFiltro"
         *que ainda vai ser implementado no FlemBase.
         */
        request.setAttribute("lista", new GEMImpl().obterEquipamentosInformaticaFiltro(ativoFixo));
        
        return mapping.findForward("lista");
    }
    
    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        
        String codigo = (String) dyna.get("codigo");
        String descricao = (String) dyna.get("descricao");
        String responsavel = (String) dyna.get("responsavel");
        String detentor = (String) dyna.get("detentor");
        String tombo = (String) dyna.get("tombo");
        String situacao = (String) dyna.get("situacao");
        String localizacao = (String) dyna.get("localizacao");
        String serial = (String) dyna.get("serial");
        String fabricante = (String) dyna.get("fabricante");
        String modelo = (String) dyna.get("modelo");
        
        if (codigo != null && !codigo.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_codigo", codigo);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_codigo", null);
        }
        
        if (descricao != null && !descricao.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_descricao", descricao);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_descricao", null);
        }
        
        if (responsavel != null && !responsavel.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_responsavel", responsavel);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_responsavel", null);
        }
        
        if (detentor != null && !detentor.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_detentor", detentor);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_detentor", null);
        }
        
        if (tombo != null && !tombo.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_tombo", tombo);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_tombo", null);
        }
        
        if (situacao != null && !situacao.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_situacao", situacao);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_situacao", null);
        }
        
        if (localizacao != null && !localizacao.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_localizacao", localizacao);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_localizacao", null);
        }
        
        if (serial != null && !serial.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_serial", serial);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_serial", null);
        }
        
        if (fabricante != null && !fabricante.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_fabricante", fabricante);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_fabricante", null);
        }
        
        if (modelo != null && !modelo.equals("")) {
            request.getSession().setAttribute("EquipamentoAction_modelo", modelo);
        }
        else {
            request.getSession().setAttribute("EquipamentoAction_modelo", null);
        }
        
        return unspecified(mapping, form, request, response);
    }

}
