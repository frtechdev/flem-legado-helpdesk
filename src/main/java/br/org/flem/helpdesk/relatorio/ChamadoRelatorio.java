/*
 * ChamadoRelatorio.java
 *
 * Created on 20 de Junho de 2007, 15:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.org.flem.helpdesk.relatorio;

import br.org.flem.fw.service.IUsuario;
import br.org.flem.helpdesk.negocio.Chamado;

/**
 *
 * @author dbbarreto
 */
public class ChamadoRelatorio extends Chamado {

    /**
     * Atributo que corresponde ao usu�rio no outro banco de dados.
     */
    private IUsuario colaborador;
    private IUsuario solicitante;

    /**
     * Creates a new instance of ChamadoRelatorio
     */
    public ChamadoRelatorio() {
    }

    public ChamadoRelatorio(Chamado chamado) {
        this.setAssunto(chamado.getAssunto());
        this.setCategoria(chamado.getCategoria());
        this.setCriacao(chamado.getCriacao());
        this.setEquipamento(chamado.getEquipamento());
        this.setId(chamado.getId());
        this.setObservacao(chamado.getObservacao());
        this.setOrigem(chamado.getOrigem());
        this.setPrioridade(chamado.getPrioridade());
        this.setResponsavel(chamado.getResponsavel());
        this.setSituacao(chamado.getSituacao());
        this.setSolucao(chamado.getSolucao());
        this.setUltimaAcao(chamado.getUltimaAcao());
        this.setUsuario(chamado.getUsuario());
        this.setAnexo(chamado.getAnexo());
    }

    public IUsuario getColaborador() {
        return colaborador;
    }

    public void setColaborador(IUsuario colaborador) {
        this.colaborador = colaborador;
    }

    public IUsuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(IUsuario solicitante) {
        this.solicitante = solicitante;
    }
}