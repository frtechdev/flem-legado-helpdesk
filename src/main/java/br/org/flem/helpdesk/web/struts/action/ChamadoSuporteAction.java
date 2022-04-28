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
import br.org.flem.fw.persistencia.dao.legado.chrbi.DepartamentoDAO;
import br.org.flem.fw.persistencia.dao.legado.control.DepartamentoUsuarioDAO;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.dto.Equipamento;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.fw.service.IUsuario;
import br.org.flem.fw.util.Constante;
import br.org.flem.fwe.web.tag.MensagemTag;
import br.org.flem.helpdesk.dao.AnexoDAO;
import br.org.flem.helpdesk.dao.ChamadoDAO;
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.dao.MeioComunicacaoDAO;
import br.org.flem.helpdesk.dao.PrioridadeDAO;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.filter.DownloadServlet;
import br.org.flem.helpdesk.negocio.Anexo;
import br.org.flem.helpdesk.negocio.Categoria;
import br.org.flem.helpdesk.negocio.Chamado;
import br.org.flem.helpdesk.negocio.Historico;
import br.org.flem.helpdesk.negocio.Situacao;
import br.org.flem.helpdesk.relatorio.ChamadoRelatorio;
import br.org.flem.helpdesk.util.UtilZip;
import br.org.flem.helpdesk.util.bd.UsuarioUtil;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.apache.commons.validator.GenericValidator;
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
public class ChamadoSuporteAction extends DispatchAction {

    public ActionForward filtrar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        String sit = dyna.getString("idsituacao");
        String cat = dyna.getString("idcategoria");
        if (GenericValidator.isInt(cat)) {
            Categoria ct = new Categoria();
            ct.setId(Integer.valueOf(cat));
            request.getSession().setAttribute("categoria", ct);
        } else {
            request.getSession().setAttribute("categoria", null);
        }
        if (GenericValidator.isInt(sit)) {
            Situacao st = new Situacao();
            st.setId(Integer.valueOf(sit));
            request.getSession().setAttribute("situacao", st);
        } else {
            request.getSession().setAttribute("situacao", null);
        }
        return unspecified(mapping, form, request, response);
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            Situacao situacao = (Situacao) request.getSession().getAttribute("situacao");
            Categoria categoria = (Categoria) request.getSession().getAttribute("categoria");
            Collection<Chamado> lista;
            Collection listaChamadosRelatorio = new ArrayList();
            if (situacao != null && categoria != null) {
                lista = new ChamadoDAO().obterChamadosPorSituacaoCategoria(situacao, categoria);
            } else if (situacao != null && categoria == null) {
                lista = new ChamadoDAO().obterChamadosPorSituacao(situacao);
            } else if (situacao == null && categoria != null) {
                lista = new ChamadoDAO().obterChamadosPorCategoria(categoria);
            } else {
                lista = new ChamadoDAO().obterChamadosNaoFechados();
            }
            for (Chamado chamado : lista) {
                ChamadoRelatorio chamadoRel = new ChamadoRelatorio(chamado);
                listaChamadosRelatorio.add(chamadoRel);
            }
            new UsuarioUtil().atualizaSolicitanteColaboradores(listaChamadosRelatorio);
            request.setAttribute("lista", listaChamadosRelatorio);
            dyna.set("listasituacao", new SituacaoDAO().obterTodos());
            dyna.set("listacategoria", new CategoriaDAO().obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("lista");
    }

    public ActionForward novo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            dyna.set("listacategoria", new CategoriaDAO().obterCategoriasOrdenadas());
            dyna.set("listaorigem", new MeioComunicacaoDAO().obterTodos());
            List<Departamento> lista = new DepartamentoDAO().obterDepartamentos();
            Departamento dep = new Departamento();
            dep.setCodigo("TODOS COLABORADORES");
            lista.add(dep);
            dyna.set("listadepartamento", lista);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        return mapping.findForward("novo");
    }

    public ActionForward adicionar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        FormFile arquivo = (FormFile) dyna.get("arquivoAnexo");
        Integer idChamadoInserido = null;
        ChamadoDAO chamadoDao = null;
        try {

            chamadoDao = new ChamadoDAO();
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Chamado chamado = new Chamado();
        chamado.setAssunto(dyna.getString("assunto"));
        Date date = new Date();
        chamado.setCriacao(date);
        chamado.setUltimaAcao(date);
        //
        Historico historico = new Historico();
        historico.setData(date);
        String idusuario = dyna.getString("idusuario");
        if (GenericValidator.isInt(idusuario)) {
            Usuario usuario = new UsuarioDAO().obterPorId(Integer.valueOf(idusuario));
            historico.setUsuario(usuario.getId());
            chamado.setUsuario(usuario.getId());
        }
        historico.setTexto(dyna.getString("descricao"));
        chamado.getHistorico().add(historico);
        historico.setChamado(chamado);
        Equipamento micro = new EquipamentoDAO().obterEquipamentosPorUsuario(chamado.getUsuario());
        if (micro != null) {
            chamado.setEquipamento(micro.getId());
        }
        //Seleciona a categoria
        try {
            chamado.setCategoria(new CategoriaDAO().obterPorPk(Integer.valueOf(dyna.getString("idsubcategoria"))));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        //chamado na situaï¿½ï¿½o de aberto
        try {
            Situacao situacao = new SituacaoDAO().obterPorPk(1);
            chamado.setSituacao(situacao);
            historico.setSituacao(situacao);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        //Seleciona a origem do chamado
        try {
            chamado.setOrigem(new MeioComunicacaoDAO().obterPorPk(Integer.valueOf(dyna.getString("idorigem"))));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }

        try {
            HibernateUtil.beginTransaction();
            idChamadoInserido = (Integer) chamadoDao.inserir(chamado);
            HibernateUtil.commitTransaction();
            ArrayList erros = new ArrayList();
            erros.add("Chamado adcionado com sucesso.");
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(ChamadoSuporteAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

        if (arquivo != null) {
            try {
                Anexo anexo = new Anexo();
                anexo.setArquivo(UtilZip.compactar(arquivo));
                anexo.setChamado(new ChamadoDAO().obterPorPk(idChamadoInserido));
                new AnexoDAO().inserir(anexo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /*try {
        request.setAttribute("lista",new ChamadoDAO().obterTodos());
        dyna.set("listasituacao",new SituacaoDAO().obterTodos());
        dyna.set("listacategoria",new CategoriaDAO().obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
        ex.printStackTrace();
        }*/
        request.setAttribute("acao", "ChamadoSuporte.do");
        return mapping.findForward("redirect");
    }

    public ActionForward editar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        DynaActionForm dyna = (DynaActionForm) form;
        try {
            ChamadoDAO chamadoDAO = new ChamadoDAO();
            Chamado chamado = chamadoDAO.obterPorPk(Integer.valueOf(request.getParameter("id")));
            dyna.set("idcategoria", chamado.getCategoria().getPai().getId() + "");
            dyna.set("idsubcategoria", chamado.getCategoria().getId() + "");
            dyna.set("listacategoria", new CategoriaDAO().obterCategoriasOrdenadas());
            dyna.set("listasubcategoria", new ArrayList(chamado.getCategoria().getPai().getFilho()));
            dyna.set("listasituacao", new SituacaoDAO().obterTodos());
            dyna.set("listaprioridade", new PrioridadeDAO().obterTodos());
            dyna.set("listaequipamento", new EquipamentoDAO().obterEquipamentos());

            List<Departamento> listadep = new DepartamentoDAO().obterDepartamentos();
            Departamento dep = new Departamento();
            dep.setCodigo("TODOS COLABORADORES");
            listadep.add(dep);
            dyna.set("listadepartamento", listadep);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.obterPorId(chamado.getUsuario());
            String r = new DepartamentoUsuarioDAO().obterRamalPorUsuario(usuario.getUsername());
            if (r == "" || r == null) {
                r = "Sem ramal";
            }
            dyna.set("listausuario", usuarioDAO.obterPorDepartamento(usuario.getLotacao()));
            dyna.set("idusuario", chamado.getUsuario() + "");
            dyna.set("iddepartamento", usuario.getLotacao());
            request.setAttribute("nomedepartamento", usuario.getLotacao());
            dyna.set("idorigem", chamado.getOrigem().getId() + "");
            BeanUtils.copyProperties(dyna, chamado);
            request.setAttribute("nomeusuario", usuario.getNome());
            request.setAttribute("ramal", r.toString());

            if (chamado.getResponsavel() != null && chamado.getResponsavel() != 0) {
                Usuario respon = new UsuarioDAO().obterPorId(chamado.getResponsavel());
                request.setAttribute("nomeresponsavel", respon.getNome());
            }

            try {
                if (chamado.getEquipamento() != null && chamado.getEquipamento() != 0) {
                    request.setAttribute("nomeequipamento", new EquipamentoDAO().obterEquipamentosPorId(chamado.getEquipamento()).getNome());
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }

            StringBuffer sb = new StringBuffer();
            List<Historico> lista = new ArrayList(chamado.getHistorico());
            Collections.sort(lista, new Comparator() {

                public int compare(Object o1, Object o2) {
                    Historico h1 = (Historico) o1;
                    Historico h2 = (Historico) o2;
                    return h2.getData().compareTo(h1.getData());
                }
            });
            for (Historico h : lista) {
                sb.append(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(h.getData()));
                sb.append(" - ");
                sb.append(new UsuarioDAO().obterPorId(h.getUsuario()).getNome());
                sb.append(" - ");
                sb.append(h.getSituacao().getSigla());
                sb.append(" - ");
                sb.append(h.getTexto());
                sb.append("\n\n");
            }
            dyna.set("descricao", sb.toString());

            if (chamado.getAnexo().size() > 0) {
//                request.setAttribute("anexo", request.getContextPath() + "/HelpDesk_Anexo" + chamado.getId() + ".zip?id=" + chamado.getAnexo().iterator().next().getId());
                request.setAttribute("anexo", request.getContextPath() + "/ChamadoSuporte.do?metodo=download"
                        + "&id=" + chamado.getAnexo().iterator().next().getId()
                        + "&filename=HelpDesk_Anexo" + chamado.getId() + ".zip");
            }

            Chamado filtro = new Chamado();
            filtro.setUsuario(chamado.getUsuario());
            request.setAttribute("chamado", chamado);
            request.setAttribute("lista", chamadoDAO.obterPorFiltro(filtro));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }

        return mapping.findForward("atender");
    }

    public void download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        new DownloadServlet().download(mapping, form, request, response);
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
            chamado = chamadoDao.obterPorPk(Integer.valueOf(dyna.getString("id")));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        Date date = new Date();
        chamado.setUltimaAcao(date);

        IUsuario usuario = (IUsuario) request.getSession().getAttribute(Constante.USUARIO_LOGADO);
        chamado.setResponsavel(usuario.getId());

        //Seleciona a categoria
        try {
            chamado.setCategoria(new CategoriaDAO().obterPorPk(Integer.valueOf(dyna.getString("idsubcategoria"))));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        //Seleciona Situaï¿½ï¿½o
        try {
            chamado.setSituacao(new SituacaoDAO().obterPorPk(Integer.valueOf(dyna.getString("idsituacao"))));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        //Seleciona Prioridade
        try {
            chamado.setPrioridade(new PrioridadeDAO().obterPorPk(Integer.valueOf(dyna.getString("idprioridade"))));
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
        }
        //observaï¿½ï¿½o interna
        chamado.setObservacao(dyna.getString("observacao"));
        chamado.setSolucao(dyna.getString("solucao"));
        Historico historico = new Historico();
        historico.setSituacao(chamado.getSituacao());
        historico.setData(date);
        historico.setUsuario(chamado.getResponsavel());
        historico.setTexto(dyna.getString("resposta"));
        historico.setChamado(chamado);
        chamado.getHistorico().add(historico);

        try {
            HibernateUtil.beginTransaction();
            chamadoDao.inserirOuAlterar(chamado);
            HibernateUtil.commitTransaction();
            ArrayList erros = new ArrayList();
            erros.add("Chamado alterado com sucesso.");
            try {
                enviaEmail(chamado, request);
            } catch (EmailException ee) {
                ee.printStackTrace();
                erros.add("\\n\\nO email não conseguiu ser gerado.");
            }
            request.getSession().setAttribute(MensagemTag.LISTA_MENSAGENS, erros);
        } catch (AcessoDadosException ex) {
            ex.printStackTrace();
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                Logger.getLogger(ChamadoSuporteAction.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        /* try {
        request.setAttribute("lista",new ChamadoDAO().obterTodos());
        dyna.set("listasituacao",new SituacaoDAO().obterTodos());
        dyna.set("listacategoria",new CategoriaDAO().obterCategoriasOrdenadas());
        } catch (AcessoDadosException ex) {
        ex.printStackTrace();
        }*/
        request.setAttribute("acao", "ChamadoSuporte.do");
        return mapping.findForward("redirect");
    }

    private void enviaEmail(Chamado chamado, HttpServletRequest request) throws EmailException {
        HtmlEmail email = new EmailBuilder().getHelpDeskEmail();
        Usuario usuario = new UsuarioDAO().obterPorId(chamado.getUsuario());
        email.addTo(usuario.getUsername() + "@flem.org.br", usuario.getNome());
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

        email.setMsg("\n\nCategoria: " + chamado.getCategoria().getPai().getDescricao() + "\n\nSubcategoria: " + chamado.getCategoria().getDescricao() + "\n\nAssunto: " + chamado.getAssunto() + "\n\nSituação do chamado: " + chamado.getSituacao().getDescricao() + "\n\n\tO chamado " + chamado.getId() + " foi alterado por um de nossos atendentes. O histórico do atendimento foi descrito abaixo:\n\nHistórico: " + sb.toString() + "\n\n\n Essa é uma resposta automática.\n\n O email do Helpdesk NÃO está sendo mais utilizado, caso tenha algum problema/dúvida/sugestão favor utilizar o Sistema de Helpdesk na Intranet ou no http://www.flem.org.br no link da extranet.");
        email.send();
    }
}
