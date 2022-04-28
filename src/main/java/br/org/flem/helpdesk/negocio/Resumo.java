/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.helpdesk.negocio;

import br.org.flem.fwe.hibernate.dto.base.BaseDTOAb;
import java.io.Serializable;
import org.directwebremoting.annotations.DataTransferObject;

/**
 *
 * @author AJLima
 */
@DataTransferObject

public class Resumo extends BaseDTOAb{
    
    // descricao
    private String descricao;
    //dias
    private String segunda;
    private String terca;
    private String quarta;
    private String quinta;
    private String sexta;    
    private String sabado;
    private String domingo;

    
    //meses
    
    private String jan;
    private String fev;
    private String mar;
    private String abr;
    private String mai;
    private String jun;
    private String jul;
    private String ago;
    private String set;
    private String out;
    private String nov;
    private String dez;
    
         
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSegunda() {
        return segunda;
    }

    public void setSegunda(String segunda) {
        this.segunda = segunda;
    }

    public String getTerca() {
        return terca;
    }

    public void setTerca(String terca) {
        this.terca = terca;
    }

    public String getQuarta() {
        return quarta;
    }

    public void setQuarta(String quarta) {
        this.quarta = quarta;
    }

    public String getQuinta() {
        return quinta;
    }

    public void setQuinta(String quinta) {
        this.quinta = quinta;
    }

    public String getSexta() {
        return sexta;
    }

    public void setSexta(String sexta) {
        this.sexta = sexta;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }
    
    
    ///////////////

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getFev() {
        return fev;
    }

    public void setFev(String fev) {
        this.fev = fev;
    }

    public String getMar() {
        return mar;
    }

    public void setMar(String mar) {
        this.mar = mar;
    }

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getMai() {
        return mai;
    }

    public void setMai(String mai) {
        this.mai = mai;
    }

    public String getJun() {
        return jun;
    }

    public void setJun(String jun) {
        this.jun = jun;
    }

    public String getJul() {
        return jul;
    }

    public void setJul(String jul) {
        this.jul = jul;
    }

    public String getAgo() {
        return ago;
    }

    public void setAgo(String ago) {
        this.ago = ago;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getNov() {
        return nov;
    }

    public void setNov(String nov) {
        this.nov = nov;
    }

    public String getDez() {
        return dez;
    }

    public void setDez(String dez) {
        this.dez = dez;
    }
    
    
    

    @Override
    public Serializable getPk() {
        return descricao;
    }
    
    
    
}
