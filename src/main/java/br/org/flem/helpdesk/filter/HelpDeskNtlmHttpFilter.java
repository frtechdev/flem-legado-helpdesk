/*
 * HelpDeskNtlmHttpFilter.java
 *
 * Created on 28 de Junho de 2007, 16:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.filter;

import br.org.flem.fw.service.IUsuario;
import br.org.flem.fw.util.Constante;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import jcifs.http.NtlmHttpFilter;

/**
 *
 * @author dbbarreto
 */
public class HelpDeskNtlmHttpFilter extends NtlmHttpFilter{
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        IUsuario user = (IUsuario) httpRequest.getSession().getAttribute(Constante.USUARIO_LOGADO);
        if (user == null) {
            super.doFilter(request, response, chain);
        }
        else {
            chain.doFilter(request, response);
        } 
    }
    
    
}
