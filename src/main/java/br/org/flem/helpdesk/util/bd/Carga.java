/*
 * Carga.java
 *
 * Created on 12 de Setembro de 2006, 09:57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.util.bd;

import br.org.flem.fwe.exception.AcessoDadosException;
import br.org.flem.fwe.hibernate.util.HibernateUtil;
import br.org.flem.helpdesk.dao.CategoriaDAO;
import br.org.flem.helpdesk.dao.MeioComunicacaoDAO;
import br.org.flem.helpdesk.dao.PrioridadeDAO;
import br.org.flem.helpdesk.dao.SituacaoDAO;
import br.org.flem.helpdesk.negocio.Categoria;
import br.org.flem.helpdesk.negocio.MeioComunicacao;
import br.org.flem.helpdesk.negocio.Prioridade;
import br.org.flem.helpdesk.negocio.Situacao;

/**
 *
 * @author mjpereira
 */
public class Carga {
    
    /** Creates a new instance of Carga */
    public Carga() {
    }
    
    private boolean carregaMeioComunicacao() {
      
        try {
            MeioComunicacaoDAO dao = new MeioComunicacaoDAO();
             HibernateUtil.beginTransaction();
            //Intranet
            MeioComunicacao intranet = new MeioComunicacao();
            intranet.setSigla("Intranet");
            intranet.setDescricao("Chamados abertos atrav�s da intranet.");
            dao.inserir(intranet);
            
            //Telefone
            MeioComunicacao telefone = new MeioComunicacao();
            telefone.setSigla("Telefone");
            telefone.setDescricao("Chamados abertos atrav�s do telefone.");
            dao.inserir(telefone);
            
            //Email
            MeioComunicacao email = new MeioComunicacao();
            email.setSigla("Email");
            email.setDescricao("Chamados abertos atrav�s de email.");
            dao.inserir(email);
            
            //Outros
            MeioComunicacao outros = new MeioComunicacao();
            outros.setSigla("site");
            outros.setDescricao("Chamados abertos de outras formas (pessoalmente, carta, sinal de fuma�a e etc).");
            dao.inserir(outros);
            HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
               ex1.printStackTrace();
            }
            //ex.printStackTrace();
            return false;
        }
        return true;
    }
    
   
    private boolean carregaPrioridade() {
      
        try {
          
            PrioridadeDAO dao = new PrioridadeDAO();
            HibernateUtil.beginTransaction();
            //alta
            Prioridade alta = new Prioridade();
            alta.setSigla("Alta");
            alta.setDescricao("Chamados que necessitam de maior aten��o e um" +
                    "baixo tempo de resposta. Impossibilitam o colaborador de " +
                    "executar suas atividades");
            dao.inserir(alta);
            
            //media
            Prioridade media = new Prioridade();
            media.setSigla("Media");
            media.setDescricao("Chamados que necessitam de aten��o. Criam dificuldades" +
                    "para o colaborador executar suas atividades (48hs)");
            dao.inserir(media);
            
             //baixa
            Prioridade baixa = new Prioridade();
            baixa.setSigla("Baixa");
            baixa.setDescricao("Chamados que facilitariam as atividades do colaborador " +
                    "e solicita��es n�o associadas diretamente a sua atividade.");
            dao.inserir(baixa);
            
           HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
     private boolean carregaCategoria() {
      
        try {
            CategoriaDAO dao = new CategoriaDAO();
             HibernateUtil.beginTransaction();
            //suporte
            Categoria suporte = new Categoria();
            suporte.setSigla("Suporte");
            suporte.setDescricao("Chamados de suporte ao usuario");
            dao.inserir(suporte);
            
            //sistemas
            Categoria intranet = new Categoria();
            intranet.setSigla("Intranet");
            intranet.setDescricao("Chamados de suporte ao usuario referentes aos sitemas da intranet");
            dao.inserir(intranet);
            
            //hr
            Categoria hr = new Categoria();
            hr.setSigla("HR");
            hr.setDescricao("Chamados de suporte ao HR");
            dao.inserir(hr);
            
            //gem
            Categoria gem = new Categoria();
            gem.setSigla("GEM");
            gem.setDescricao("Chamados de suporte ao GEM");
            dao.inserir(gem);
            
            //outros
            Categoria outros = new Categoria();
            outros.setSigla("Outros");
            outros.setDescricao("Duvidas, suges�es e criticas");
            dao.inserir(outros);
            
           HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
        return true;
    } 
        
        
    private boolean carregaSituacao() {
        try {
            SituacaoDAO dao = new SituacaoDAO();
            HibernateUtil.beginTransaction();
            //aberto
            Situacao aberto = new Situacao();
            aberto.setSigla("Aberto");
            aberto.setDescricao("Chamados abertos");
            dao.inserir(aberto);
            
            //fechado
            Situacao fechado = new Situacao();
            fechado.setSigla("Fechado");
            fechado.setDescricao("Chamados fechados");
            dao.inserir(fechado);
            
            //em andamento
            Situacao andamento = new Situacao();
            andamento.setSigla("Em andamento");
            andamento.setDescricao("Chamados em andamento");
            dao.inserir(andamento);
            
            //pendente terceiros
            Situacao terceiros = new Situacao();
            terceiros.setSigla("Pendente Terceiros");
            terceiros.setDescricao("Chamados com pendencias por parte de Terceiros");
            dao.inserir(terceiros);

            //pendente colaborador
            Situacao colaborador = new Situacao();
            colaborador.setSigla("Pendente Colaborador");
            colaborador.setDescricao("Chamados com pendencias por parte do colaborador Flem");
            dao.inserir(colaborador);
            
            //reaberto
            Situacao reaberto = new Situacao();
            reaberto.setSigla("Reaberto");
            reaberto.setDescricao("Chamados reaberto");
            dao.inserir(reaberto);
            
           HibernateUtil.commitTransaction();
        } catch (AcessoDadosException ex) {
            try {
                HibernateUtil.rollbackTransaction();
            } catch (AcessoDadosException ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        Carga carga = new Carga();
       // System.out.println("Categoria: "+carga.carregaCategoria());
        //System.out.println("Meio Comunicacao: "+carga.carregaMeioComunicacao());
        //System.out.println("Prioridade: "+carga.carregaPrioridade());
       //System.out.println("Situacao: "+carga.carregaSituacao());
       // System.out.println("Usuario: "+carga.carregaUsuario());
        
        
    }
    
}
