/*
 * DownloadServlet.java
 *
 * Created on 3 de Julho de 2007, 16:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.filter;

import br.org.flem.helpdesk.dao.AnexoDAO;
import br.org.flem.helpdesk.negocio.Anexo;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author dbbarreto
 */
public class DownloadServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost( req, res );
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.valueOf(request.getParameter("id"));
        
        try {
            Anexo anexo = new AnexoDAO().obterPorPk(id);
            String fileName = "arquivosuporte"+anexo.getChamado().getId()+".zip";
            response.setContentType("application/zip");
            response.setContentLength(anexo.getArquivo().length);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(anexo.getArquivo(), 0, anexo.getArquivo().length);
            outStream.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        
        Integer id = Integer.valueOf(request.getParameter("id"));
        String fileName = String.valueOf(request.getParameter("filename"));
        
        try {
            Anexo anexo = new AnexoDAO().obterPorPk(id);
            
            response.setContentType("application/zip");
            response.setContentLength(anexo.getArquivo().length);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(anexo.getArquivo(), 0, anexo.getArquivo().length);
            outStream.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    
}