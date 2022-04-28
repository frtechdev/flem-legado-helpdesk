/*
 * ChamadoDAO.java
 *
 * Created on 10 de Setembro de 2006, 12:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.dao;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.dao.base.BaseDAOAb;
import br.org.flem.fwe.web.tag.jfreechart.dto.DadosGrafico;
import br.org.flem.helpdesk.dao.util.GeradorQueryUtil;
import br.org.flem.helpdesk.negocio.Categoria;
import br.org.flem.helpdesk.negocio.Chamado;
import br.org.flem.helpdesk.negocio.MeioComunicacao;
import br.org.flem.helpdesk.negocio.Resumo;
import br.org.flem.helpdesk.negocio.Situacao;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

/**
 *
 * @author mario
 */
public class ChamadoDAO extends BaseDAOAb<Chamado> {
    

    
    public ChamadoDAO() throws AcessoDadosException {
        super();
    }
    
    public ChamadoDAO(Session s) {
        super(s);
    }
    
    public List<Chamado> obterChamadosPorSituacao(Situacao situacao) throws AcessoDadosException {
        try {
            return this.session.createQuery("from Chamado c where c.situacao.id = :situacao").setInteger("situacao",
                    situacao.getId()).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Chamado> obterChamadosPorCategoria(Categoria categoria) throws AcessoDadosException {
        try {
            return this.session.createQuery("from Chamado c where c.categoria.pai.id = :categoria").setInteger("categoria",
                    categoria.getId()).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Chamado> obterChamadosPorSituacaoCategoria(Situacao situacao, Categoria categoria) throws AcessoDadosException {
        try {
            return this.session.createQuery("from Chamado c where" +
                    " c.categoria.pai.id = :categoria and" +
                    " c.situacao.id = :situacao").setInteger("categoria",
                    categoria.getId()).setInteger("situacao",situacao.getId()).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Chamado> obterChamadosPorPeriodoSituacao(Date inicio, Date fim, Situacao situacao, Categoria pai, Categoria filho, boolean naoFechados) throws AcessoDadosException {
        try {
            Criteria chamado = session.createCriteria(Chamado.class);
            if (inicio != null) {
                chamado.add(Expression.ge("criacao", inicio));
               // chamado.add(Expression.eq("criacao", inicio));
            }
            if (fim != null) {
                chamado.add(Expression.le("criacao", fim));
                //chamado.add(Expression.eq("criacao", fim));
            }
            
            if (naoFechados) {
                Situacao fechado = new Situacao();
                fechado.setId(2);
                chamado.add(Expression.not(Expression.eq("situacao", fechado)));
            }
            else if (situacao != null) {
                chamado.add(Expression.eq("situacao", situacao));
            }
            
            Criteria subcategoria = chamado.createCriteria("categoria");
            
            if (filho != null) {
                subcategoria.add(Expression.idEq(filho.getId()));
            }
            
            Criteria categoria = subcategoria.createCriteria("pai");
            
            if (pai != null) {
                categoria.add(Expression.idEq(pai.getId()));
                //Criteria categoria = chamado.createCriteria("categoria");
                //categoria.add(Expression.eq("pai", pai));
            }
            
            categoria.addOrder(Order.asc("sigla"));
            subcategoria.addOrder(Order.asc("sigla"));
            chamado.addOrder(Order.asc("criacao"));
            
            return categoria.list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Chamado> obterChamadosPorSituacaoUsuarioOrigemPalavras(Integer usuario, Situacao situacao, Categoria pai, Categoria filho, MeioComunicacao origem, String textoAssunto, String textoHistorico) throws AcessoDadosException {
        try {
            Criteria chamado = session.createCriteria(Chamado.class);
            
            if (textoAssunto != null && !textoAssunto.equals("")) { 
                chamado.add(GeradorQueryUtil.montaBuscaTextoComplexa(textoAssunto, "assunto"));
            }
            
            if (textoHistorico != null && !textoHistorico.equals("")) {
                chamado.createCriteria("historico").add(GeradorQueryUtil.montaBuscaTextoComplexa(textoHistorico, "texto"));
            }
            
            if (situacao != null) {
                chamado.add(Expression.eq("situacao", situacao));
            }
            
            if (origem != null) {
                chamado.add(Expression.eq("origem", origem));
            }
            
            if (usuario != null) {
                chamado.add(Expression.eq("usuario", usuario));
            }        
            
            Criteria subcategoria = chamado.createCriteria("categoria");
            
            if (filho != null) {
                subcategoria.add(Expression.idEq(filho.getId()));
            }
            
            Criteria categoria = subcategoria.createCriteria("pai");
            
            if (pai != null) {
                categoria.add(Expression.idEq(pai.getId()));
                //Criteria categoria = chamado.createCriteria("categoria");
                //categoria.add(Expression.eq("pai", pai));
            }
            
            
            
            return categoria.list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    
    public List<Chamado> obterChamadosNaoFechados() throws AcessoDadosException {
        try {
            return this.session.createQuery("from Chamado c where c.situacao.id != :situacao").setInteger("situacao",2).list();
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
        
        
    }
    
    public Collection<Resumo> resumoOperacionalMes() throws AcessoDadosException{
        try {
            
            
            SQLQuery query = null;
            StringBuilder str = new StringBuilder("select S.descricao,COUNT( case when DATEPART(MONTH,H.data)=1 then 1 end) Jan,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=2 then 1 end) Fev,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=3 then 1 end) Mar,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=4 then 1 end) Abr,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=5 then 1 end) Mai,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=6 then 1 end) Jun,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=7 then 1 end) Jul,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=8 then 1 end) Ago,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=9 then 1 end) 'Set',"
                    + "COUNT( case when DATEPART(MONTH,H.data)=10 then 1 end) 'Out',"
                    + "COUNT( case when DATEPART(MONTH,H.data)=11 then 1 end) Nov,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=12 then 1 end) Dez "
                    + "from CHAMADO as C inner join HISTORICO as H ON H.id_chamado=C.id_chamado "
                    + "inner join SITUACAO as S on S.id_situacao=H.id_situacao "
                    + "group by S.descricao");
            query = session.createSQLQuery(str.toString());
            
            List<Object[]> lista = query.list();
            
            List<Resumo> resumos = new ArrayList<Resumo>();
            
            for( Object[] r : lista){
 
                Resumo rr = new  Resumo();
                rr.setDescricao(r[0].toString());
                rr.setJan(r[1].toString());
                rr.setFev(r[2].toString());
                rr.setMar(r[3].toString());
                rr.setAbr(r[4].toString());
                rr.setMai(r[5].toString());
                rr.setJun(r[6].toString());
                rr.setJul(r[7].toString());
                rr.setAgo(r[8].toString());
                rr.setSet(r[9].toString());
                rr.setOut(r[10].toString());
                rr.setNov(r[11].toString());
                rr.setDez(r[12].toString());
                resumos.add(rr);
            }
            
            return resumos;
            
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
        
    }
    public Collection<Resumo> resumoOperacionalSemana() throws AcessoDadosException{
        try {
            
            
            SQLQuery query = null;
            StringBuilder str = new StringBuilder("select S.descricao,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=2 then 1 end) seg,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=3 then 1 end) ter,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=4 then 1 end) qua,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=5 then 1 end) qui,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=6 then 1 end) sex,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=7 then 1 end) sab,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=1 then 1 end) dom"
                    + " from CHAMADO as C inner join HISTORICO as H ON H.id_chamado=C.id_chamado "
                    + "inner join SITUACAO as S on S.id_situacao=H.id_situacao "
                    + "group by S.descricao");
            query = session.createSQLQuery(str.toString());
            
            List<Object[]> lista = query.list();
            
            List<Resumo> resumos = new ArrayList<Resumo>();
            
            for( Object[] r : lista){
 
                Resumo rr = new  Resumo();
                rr.setDescricao(r[0].toString());
                rr.setSegunda(r[1].toString());
                rr.setTerca(r[2].toString());
                rr.setQuarta(r[3].toString());
                rr.setQuinta(r[4].toString());
                rr.setSexta(r[5].toString());
                rr.setSabado(r[6].toString());
                rr.setDomingo(r[7].toString());
                resumos.add(rr);
            }
            
            return resumos;
            
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
        
    }
    
    public Collection<Resumo> resumoSemana( String dt1, String dt2) throws AcessoDadosException{
        try {
            SQLQuery query = null;
            StringBuilder str = new StringBuilder("select S.descricao,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=2 then 1 end) seg,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=3 then 1 end) ter,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=4 then 1 end) qua,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=5 then 1 end) qui,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=6 then 1 end) sex,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=7 then 1 end) sab,"
                    + "COUNT( case when DATEPART(WEEKDAY,H.data)=1 then 1 end) dom"
                    + " from CHAMADO as C inner join HISTORICO as H ON H.id_chamado=C.id_chamado "
                    + "inner join SITUACAO as S on S.id_situacao=H.id_situacao "
                    + "where H.data between '"+dt1+"' and '"+dt2+"' "
                    + "group by S.descricao");
           
            query = session.createSQLQuery(str.toString());
            
            List<Object[]> lista = query.list();
            
            List<Resumo> resumos = new ArrayList<Resumo>();
            
            for( Object[] r : lista){
 
                Resumo rr = new  Resumo();
                rr.setDescricao(r[0].toString());
                rr.setSegunda(r[1].toString());
                rr.setTerca(r[2].toString());
                rr.setQuarta(r[3].toString());
                rr.setQuinta(r[4].toString());
                rr.setSexta(r[5].toString());
                rr.setSabado(r[6].toString());
                rr.setDomingo(r[7].toString());
                resumos.add(rr);
            }
            
            return resumos;
            
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    public Collection<Resumo> resumoMes( String dt1, String dt2) throws AcessoDadosException{
        try {
            SQLQuery query = null;
            StringBuilder str = new StringBuilder("select S.descricao,COUNT( case when DATEPART(MONTH,H.data)=1 then 1 end) Jan,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=2 then 1 end) Fev,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=3 then 1 end) Mar,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=4 then 1 end) Abr,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=5 then 1 end) Mai,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=6 then 1 end) Jun,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=7 then 1 end) Jul,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=8 then 1 end) Ago,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=9 then 1 end) 'Set',"
                    + "COUNT( case when DATEPART(MONTH,H.data)=10 then 1 end) 'Out',"
                    + "COUNT( case when DATEPART(MONTH,H.data)=11 then 1 end) Nov,"
                    + "COUNT( case when DATEPART(MONTH,H.data)=12 then 1 end) Dez "
                    + "from CHAMADO as C inner join HISTORICO as H ON H.id_chamado=C.id_chamado "
                    + "inner join SITUACAO as S on S.id_situacao=H.id_situacao "
                    + "where H.data between '"+dt1+"' and '"+dt2+"' "
                    + "group by S.descricao");
           
            query = session.createSQLQuery(str.toString());
            
            List<Object[]> lista = query.list();
            
            List<Resumo> resumos = new ArrayList<Resumo>();
            
            for( Object[] r : lista){
 
                Resumo rr = new  Resumo();
                rr.setDescricao(r[0].toString());
                rr.setJan(r[1].toString());
                rr.setFev(r[2].toString());
                rr.setMar(r[3].toString());
                rr.setAbr(r[4].toString());
                rr.setMai(r[5].toString());
                rr.setJun(r[6].toString());
                rr.setJul(r[7].toString());
                rr.setAgo(r[8].toString());
                rr.setSet(r[9].toString());
                rr.setOut(r[10].toString());
                rr.setNov(r[11].toString());
                rr.setDez(r[12].toString());
                resumos.add(rr);
            }
            
            return resumos;
            
        } catch (HibernateException e) {
            throw new AcessoDadosException(e);
        }
    }
    @Override
    protected Class<Chamado> getClasseDto() {
        return Chamado.class;
    }
    
}
