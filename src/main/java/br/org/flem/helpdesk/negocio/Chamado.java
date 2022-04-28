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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mario
 */
public class Chamado extends BaseDTOAb {
    
    private Integer id;
    
    private String assunto;
    
    private Date criacao;
    
    private Date ultimaAcao;
    
    private String observacao;
    
    private Integer usuario;
    
    private Integer responsavel;
    
    private MeioComunicacao origem;
    
    private Situacao situacao;
    
    private Categoria categoria;
    
    private Prioridade prioridade;
    
    private String solucao;
    
    private Integer equipamento;
    
    private Set<Historico> historico = new HashSet<Historico>();
    
    private Set<Anexo> anexo = new HashSet<Anexo>();
    
    public Chamado() {
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

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
    }

    public Date getUltimaAcao() {
        return ultimaAcao;
    }

    public void setUltimaAcao(Date ultimaAcao) {
        this.ultimaAcao = ultimaAcao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Integer responsavel) {
        this.responsavel = responsavel;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public MeioComunicacao getOrigem() {
        return origem;
    }

    public void setOrigem(MeioComunicacao origem) {
        this.origem = origem;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public Integer getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Integer equipamento) {
        this.equipamento = equipamento;
    }

    public Set<Historico> getHistorico() {
        return historico;
    }

    public void setHistorico(Set<Historico> historico) {
        this.historico = historico;
    }

    public Set<Anexo> getAnexo() {
        return anexo;
    }

    public void setAnexo(Set<Anexo> anexo) {
        this.anexo = anexo;
    }

}
