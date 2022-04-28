/*
 * DateWrapper.java
 *
 * Created on 13 de Setembro de 2006, 11:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.web.displaytag;

import br.org.flem.helpdesk.util.SituacaoEquipamentoUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 *
 * @author mjpereira
 */
public class SituacaoEquipamento implements DisplaytagColumnDecorator{
    
private DateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

  /**
   * Method decorate
   * @param pColumnValue Object
   * @return Object
   */
    public Object decorate(Object object, PageContext pageContext, MediaTypeEnum mediaTypeEnum) throws DecoratorException {
        String situacao = (String) object;
        
	return new SituacaoEquipamentoUtil().converteSituacao(situacao);
    }
    
}
