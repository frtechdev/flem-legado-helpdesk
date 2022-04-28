/*
 * HelpDeskMenuTag.java
 *
 * Created on 22 de Setembro de 2006, 11:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.tag;


import br.org.flem.fw.service.IUsuario;
import br.org.flem.fwe.service.IUsuarioExterno;
import br.org.flem.fwe.web.tag.MenuTag;


/**
 *
 * @author mjpereira
 */
public class HelpDeskMenuTag extends MenuTag {
    
    
    public StringBuffer geraMenu(IUsuarioExterno usuarioExterno) {
        IUsuario usuario = (IUsuario) usuarioExterno;
        StringBuffer sb = new StringBuffer();
        sb.append("<h2>HelpDesk: </h2><ul>");
         if (usuario.getPermissoes().contains("lschmsup")){
            sb.append("<li>Suporte<ul>");
            sb.append("<li><a href=\"./ChamadoSuporte.do?metodo=novo\">Novo Chamado</a></li>");
            sb.append("<li><a href=\"./ChamadoSuporte.do\">Listar Chamados</a></li>");
            sb.append("<li><a href=\"./Categoria.do\">Gerenciar Categorias</a></li>");
            sb.append("</ul></li>");
        }
        if (usuario.getPermissoes().contains("lschmuser")){
            sb.append("<li>Atendimento<ul>" +
                    "<li><a href=\"./ChamadoUsuario.do?metodo=novo\">Novo Chamado</a></li>" +
                    "<li><a href=\"./ChamadoUsuario.do\">Listar Chamados</a></li>" +
                    "</ul></li>");
        }
        sb.append("</ul>");
        return sb;
    }
        
    
    
}
