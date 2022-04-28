/*
 * CriadorRelatorio.java
 *
 * Created on 13 de Mar�o de 2007, 08:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.relatorio;

import br.org.flem.fwe.web.relatorio.CriadorRelatorio;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe respons�vel por montar e exportar os relat�rios.
 * @author dbbarreto
 */
public class HelpDeskCriadorRelatorio extends CriadorRelatorio{
    
   
    /**
     * M�todo que monta o relat�rio com os par�metros e a cole��o de objetos. Este m�todo � privado para que o usu�rio da classe escolha um dos m�todos para a exporta��o desse relat�rio.
     * @param request O request utilizado no momento, pela action.
     * @param localArquivo Local do arquivo, relativo ao contexto da aplica��o.
     * @param parametros Lista de par�metros definida para o relat�rio.
     * @param resultado Cole��o de objetos que ser� utilizada como DataSource do relat�rio.
     * @throws net.sf.jasperreports.engine.JRException Exce��o lan�ada, caso o relat�rio apresente algum problema.
     * @return Relat�rio pronto, que ser� utilizado por algum m�todo de exporta��o.
     */
    protected void montaRelatorio(HttpServletRequest request, Map parametros) {
        parametros.put("logo", request.getSession().getServletContext().getRealPath("/img/logohd.gif"));
    }
}
