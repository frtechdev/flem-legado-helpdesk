/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.helpdesk.web.struts.action;

import br.org.flem.fwe.util.Data;
import br.org.flem.helpdesk.dao.ChamadoDAO;
import br.org.flem.helpdesk.negocio.Resumo;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
//import sun.text.resources.FormatData;

/**
 *
 * @author AJLima
 */
public class ResumoAction extends DispatchAction {

    public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        DynaActionForm dyna = (DynaActionForm) form;

        if ((dyna.getString("dataInicial") != "" && dyna.getString("dataInicial").trim().length() > 0)
                && (dyna.getString("dataFinal") != "" && dyna.getString("dataFinal").trim().length() > 0)) {

            String datainicio = (dyna.getString("dataInicial"));
            String datafim = (dyna.getString("dataFinal"));

            Collection<Resumo> listaMes = new ChamadoDAO().resumoMes(datainicio, datafim);
            Collection<Resumo> listaSemana = new ChamadoDAO().resumoSemana(datainicio, datafim);
            if (listaSemana != null && !listaSemana.isEmpty()) {
                request.setAttribute("lista", (Collection<Resumo>) listaSemana);
                request.setAttribute("listames", (Collection<Resumo>) listaMes);
            } else {
                request.setAttribute("lista", new ArrayList());
                request.setAttribute("listames", new ArrayList());
            }
        } else {
            Collection<Resumo> listaSemana = new ChamadoDAO().resumoOperacionalSemana();
            Collection<Resumo> listatMes = new ChamadoDAO().resumoOperacionalMes();
            if (listaSemana != null && !listaSemana.isEmpty()) {
                request.setAttribute("lista", (Collection<Resumo>) listaSemana);
                request.setAttribute("listames", (Collection<Resumo>) listatMes);

            } else {
                request.setAttribute("lista", new ArrayList());
                request.setAttribute("listames", new ArrayList());
            }
        }

        return mapping.findForward("lista");
    }

}
