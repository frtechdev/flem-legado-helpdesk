/*
 * Prioridade.java
 *
 * Created on 10 de Setembro de 2006, 11:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;


/**
 *
 * @author mario
 */
@DataTransferObject
public class Prioridade  extends BaseDTOAb{
    
    @RemoteProperty
    private Integer id;
    @RemoteProperty
    private String sigla;

    private String descricao;
    
    /** Creates a new instance of Prioridade */
    public Prioridade() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public Serializable getPk() {
        return getId();
    }
    
}
