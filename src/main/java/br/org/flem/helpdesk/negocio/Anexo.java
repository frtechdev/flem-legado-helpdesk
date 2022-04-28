/*
 * Chamado.java
 *
 * Created on 10 de Setembro de 2006, 11:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;

/**
 *
 * @author diego
 */
public class Anexo extends BaseDTOAb {
    
    private Integer id;
    
    private byte[] arquivo;
    
    private Chamado chamado;
    
    public Anexo() {
    }

    @Override
    public Serializable getPk() {
        return getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

}
