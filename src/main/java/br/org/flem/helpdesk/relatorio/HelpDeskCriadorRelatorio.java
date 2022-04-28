/*
 * CriadorRelatorio.java
 *
 * Created on 13 de Março de 2007, 08:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.relatorio;

import br.org.flem.fwe.web.relatorio.CriadorRelatorio;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe responsável por montar e exportar os relatórios.
 * @author dbbarreto
 */
public class HelpDeskCriadorRelatorio extends CriadorRelatorio{
    
   
    /**
     * Método que monta o relatório com os parâmetros e a coleção de objetos. Este método é privado para que o usuário da classe escolha um dos métodos para a exportação desse relatório.
     * @param request O request utilizado no momento, pela action.
     * @param localArquivo Local do arquivo, relativo ao contexto da aplicação.
     * @param parametros Lista de parâmetros definida para o relatório.
     * @param resultado Coleção de objetos que será utilizada como DataSource do relatório.
     * @throws net.sf.jasperreports.engine.JRException Exceção lançada, caso o relatório apresente algum problema.
     * @return Relatório pronto, que será utilizado por algum método de exportação.
     */
    protected void montaRelatorio(HttpServletRequest request, Map parametros) {
        parametros.put("logo", request.getSession().getServletContext().getRealPath("/img/logohd.gif"));
    }
}
