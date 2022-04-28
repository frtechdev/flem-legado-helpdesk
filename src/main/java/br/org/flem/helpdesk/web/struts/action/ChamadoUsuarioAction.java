/*
 * SituacaoAction.java
 *
 * Created on 10 de Setembro de 2006, 17:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fw.email.EmailBuilder;
import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.fw.persistencia.dao.EquipamentoDAO;
import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dto.Equipamento;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.fw.service.IUsuario;
import br.org.flem.fw.util.Constante;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.helpdesk.dao.AnexoDAO;
import br.org.flem.helpdesk.dao.ChamadoDAO;
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.dao.MeioComunicacaoDAO;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.negocio.Anexo;
import br.org.flem.helpdesk.negocio.Chamado;
import br.org.flem.helpdesk.negocio.Historico;
import br.org.flem.helpdesk.util.UtilZip;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author mario
 */
public class ChamadoUsuarioAction extends DispatchAction {
    
    
    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        try {
            Chamado c= new Chamado();
            IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            c.setUsuario(usuario.getId());
            request.setAttribute("lista",new ChamadoDAO().obterPorFiltro(c));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("lista");
    }
    
    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            dyna.set("listacategoria",new CategoriaDAO().obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("novo");
    }
    
    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        ChamadoDAO chamadoDao = null;
        Integer idcategoria = Integer.valueOf(dyna.getString("idsubcategoria"));
        String assunto = dyna.getString("assunto");
        FormFile arquivo = (FormFile) dyna.get("arquivoAnexo");
        Integer idChamadoInserido = null;
        try {
            chamadoDao = new ChamadoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Chamado chamado = new Chamado();
        chamado.setAssunto(assunto);
        Date date = new Date();
        chamado.setCriacao(date);
        chamado.setUltimaAcao(date);
        try {
            chamado.setCategoria(new CategoriaDAO().obterPorPk(idcategoria));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        try {
            chamado.setSituacao(new SituacaoDAO().obterPorPk(1));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
        chamado.setUsuario(usuario.getId());

        try {
            chamado.setOrigem(new MeioComunicacaoDAO().obterPorPk(1));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Historico historico = new Historico();
        historico.setData(date);
        historico.setUsuario(chamado.getUsuario());
        historico.setSituacao(chamado.getSituacao());
        historico.setTexto(dyna.getString("descricao"));
        historico.setChamado(chamado);
        chamado.getHistorico().add(historico);
        Equipamento micro = new EquipamentoDAO().obterEquipamentosPorUsuario(usuario.getId());
        if(micro!=null){
            chamado.setEquipamento(micro.getId());
        }
        ArrayList erros = new ArrayList();
        try {
            HibernateUtil.beginTransaction();
            idChamadoInserido = (Integer) chamadoDao.inserir(chamado);
            /*try {
                enviaEmail(chamado, request);
            }
            catch(EmailException ee) {
                ee.printStackTrace();
                erros.add("\\n\\nO email não conseguiu ser gerado.");
            }*/
            HibernateUtil.commitTransaction();
            
            erros.add("Chamado adcionado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS,erros);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(ChamadoUsuarioAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        
        if (arquivo != null && arquivo.getFileSize() > 0) {
            try {
                Anexo anexo = new Anexo();
                anexo.setArquivo(UtilZip.compactar(arquivo));
                anexo.setChamado(new ChamadoDAO().obterPorPk(idChamadoInserido));
                new AnexoDAO().inserir(anexo);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        /*try {
            Chamado c= new Chamado();
            c.setUsuario(usuario.getId());
            request.setAttribute("lista",new ChamadoDAO().obterPorFiltro(c));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }*/
        request.setAttribute("acao", "ChamadoUsuario.do");
        return mapping.findForward("redirect");
    }
    
    public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            Chamado chamado = new ChamadoDAO().obterPorPk(Integer.valueOf(request.getParameter("id")));
            BeanUtils.copyProperties(dyna,chamado);
            StringBuffer sb = new StringBuffer();
            List<Historico> lista = new ArrayList(chamado.getHistorico());
            Collections.sort(lista,new Comparator() {
                public int compare(Object o1, Object o2) {
                    Historico h1 = (Historico) o1;
                    Historico h2 = (Historico) o2;
                    return h2.getData().compareTo(h1.getData());
                }
                
            });
            for (Historico h : lista){
                sb.append(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(h.getData()));
                sb.append(" - ");
                sb.append((new UsuarioDAO().obterPorId(h.getUsuario()).getNome()));
                sb.append(" - ");
                sb.append(h.getSituacao().getSigla());
                sb.append(" - ");
                sb.append(h.getTexto());
                sb.append("\n\n");
            }
            dyna.set("descricao",sb.toString());
            request.setAttribute("chamado",chamado);
            System.out.println(chamado.getCriacao());
            dyna.set("idcategoria",chamado.getCategoria().getId()+"");
            dyna.set("idsubcategoria",chamado.getCategoria().getPai().getId()+"");
            if(chamado.getResponsavel()!=null && chamado.getResponsavel()!=0){
                UsuarioDAO usuarioDAO =  new UsuarioDAO();
                Usuario usuario = usuarioDAO.obterPorId(chamado.getResponsavel());
                request.setAttribute("nomeresponsavel",usuario.getNome());
            }
            
            if (chamado.getAnexo().size() > 0) {
                request.setAttribute("anexo", request.getContextPath()+"/HelpDesk_Anexo"+chamado.getId()+".zip?id="+chamado.getAnexo().iterator().next().getId());
            }
            
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("alterar");
    }
    
    public ActionForward salvar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        ChamadoDAO chamadoDao = null;
        try {
            chamadoDao = new ChamadoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Chamado chamado = null;
        try {
            chamado = chamadoDao.obterPorPk(Integer.valueOf( dyna.getString("id")));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Historico historico = new Historico();
        historico.setData(new Date());
        historico.setSituacao(chamado.getSituacao());
        historico.setTexto(dyna.getString("resposta"));
        historico.setUsuario(chamado.getUsuario());
        historico.setChamado(chamado);
        chamado.getHistorico().add(historico);
        chamado.setUltimaAcao(new Date());
        ArrayList erros = new ArrayList();
        try {
            HibernateUtil.beginTransaction();
            chamadoDao.inserirOuAlterar(chamado);
            try {
                enviaEmail(chamado, request);
            }
            catch(EmailException ee) {
                ee.printStackTrace();
                erros.add("\\n\\nO email não conseguiu ser gerado.");
            }
            HibernateUtil.commitTransaction();
            
            erros.add("Chamado alterado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS,erros);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(ChamadoUsuarioAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        /*try {
            Chamado c= new Chamado();
            UserLogin usuario = (UserLogin) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
            c.setUsuario(usuario.getId());
            request.setAttribute("lista",new ChamadoDAO().obterPorFiltro(c));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }*/
        
        request.setAttribute("acao", "ChamadoUsuario.do");
        return mapping.findForward("redirect");
    }
    
    
    public ActionForward formularioEnvioArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            Chamado chamado = new ChamadoDAO().obterPorPk(Integer.valueOf(request.getParameter("id")));
            BeanUtils.copyProperties(dyna,chamado);
 
            request.setAttribute("chamado",chamado);
            //System.out.println(chamado.getCriacao());
            dyna.set("idcategoria",chamado.getCategoria().getId()+"");
            dyna.set("idsubcategoria",chamado.getCategoria().getPai().getId()+"");
            if(chamado.getResponsavel()!=null && chamado.getResponsavel()!=0){
                UsuarioDAO usuarioDAO =  new UsuarioDAO();
                Usuario usuario = usuarioDAO.obterPorId(chamado.getResponsavel());
                request.setAttribute("nomeresponsavel",usuario.getNome());
            }
            
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("envioArquivo");
    }
    
    public ActionForward enviarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws AcessoDadosException, FileNotFoundException, IOException {
        DynaActionForm dyna = (DynaActionForm) form;
        FormFile arquivo = (FormFile) dyna.get("arquivoAnexo");
        ChamadoDAO chamadoDao = null;
        try {
            chamadoDao = new ChamadoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Chamado chamado = null;
        try {
            chamado = chamadoDao.obterPorPk(Integer.valueOf( dyna.getString("id")));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Historico historico = new Historico();
        historico.setData(new Date());
        historico.setSituacao(chamado.getSituacao());
        historico.setTexto("Novo arquivo Anexado ao chamado.");
        historico.setUsuario(chamado.getUsuario());
        historico.setChamado(chamado);
        chamado.getHistorico().add(historico);
        
        Anexo anexo; 
        if (chamado.getAnexo().size() > 0) {
            anexo = new AnexoDAO().obterPorPk(chamado.getAnexo().iterator().next().getId());
        }
        else {
            anexo = new Anexo();
            anexo.setChamado(chamado);
        }
        
        anexo.setArquivo(UtilZip.compactar(arquivo));
        
        try {
            HibernateUtil.beginTransaction();
            chamadoDao.inserirOuAlterar(chamado);
            new AnexoDAO().inserirOuAlterar(anexo);
            HibernateUtil.commitTransaction();
            ArrayList erros = new ArrayList();
            erros.add("Arquivo anexado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS,erros);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            HibernateUtil.rollbackTransaction();
        }
        request.setAttribute("acao", "ChamadoUsuario.do");
        return mapping.findForward("redirect");
    }

    private void enviaEmail(Chamado chamado, HttpServletRequest request) throws EmailException {
        HtmlEmail email = new EmailBuilder().getHelpDeskEmail();
        Usuario usuario = new UsuarioDAO().obterPorId(chamado.getUsuario());
        email.addTo("suporte@flem.org.br", usuario.getNome());
        email.setSubject("Alteração no chamado do sistema HelpDesk " + chamado.getId() + "");

        StringBuffer sb = new StringBuffer();

        for (Historico h : chamado.getHistorico()) {
            sb.append(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(h.getData()));
            sb.append(" - ");
            sb.append(new UsuarioDAO().obterPorId(h.getUsuario()).getNome());
            sb.append(" - ");
            sb.append(h.getSituacao().getSigla());
            sb.append(" - ");
            sb.append(h.getTexto());
            sb.append("\n\n");
        }

        email.setMsg("\n\nCategoria: " + chamado.getCategoria().getPai().getDescricao() + "\n\nSubcategoria: " + chamado.getCategoria().getDescricao() + "\n\nAssunto: " + chamado.getAssunto() + "\n\nSituação do chamado: " + chamado.getSituacao().getDescricao() + "\n\n\tO chamado " + chamado.getId() + " foi alterado por um de nossos atendentes. O histórico do atendimento foi descrito abaixo:\n\nHistórico: " + sb.toString()+"\n\n\n");
        email.send();
    }
    
    public static void main(String args[]) throws AcessoDadosException, FileNotFoundException, IOException {
        Anexo anexo = new AnexoDAO().obterPorPk(2);
        //ByteArrayOutputStream baos = new ByteArrayOutputStreamm(anexo.getArquivo());
        FileOutputStream fos = new FileOutputStream("C:/aaaaa2.zip", false);
        fos.write(anexo.getArquivo());
        fos.close();
    }
    
    
}
