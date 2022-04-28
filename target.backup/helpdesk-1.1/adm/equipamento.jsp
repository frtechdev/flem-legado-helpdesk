<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script>
            function mostraDetalheEquipamento(codigo, modelo, fabricante, serial, tombo) {
                alert(
                    "Código: "+codigo+"\n\n"+
                    "Modelo: "+modelo+"\n\n"+
                    "Fabricante: "+fabricante+"\n\n"+
                    "Serial: "+serial+"\n\n"+
                    "Tombo: "+tombo+"\n\n"
                );
            }
        </script>
    </head>
    <body id="body" >
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap" >
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Equipamentos</h2>
                <html:form action="Equipamento.do?metodo=filtrar">
                    <table>
                        <tr>
                            <td>
                                Código:
                            <td>
                            <td>
                                <html:text property="codigo" size="30" value="${EquipamentoAction_codigo}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Descrição:
                            <td>
                            <td>
                                <html:text property="descricao" size="30" value="${EquipamentoAction_descricao}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Responsável:
                            <td>
                            <td>
                                <html:text property="responsavel" size="30" value="${EquipamentoAction_responsavel}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Detentor:
                            <td>
                            <td>
                                <html:text property="detentor" size="30" value="${EquipamentoAction_detentor}"/>
                            <td>    
                        </tr>
                        <tr>
                            <td>
                                Tombo:
                            <td>
                            <td>
                                <html:text property="tombo" size="30" value="${EquipamentoAction_tombo}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Situação:
                            <td>
                            <td>
                                <html:select property="situacao" value="${EquipamentoAction_situacao}">
                                    <html:option value="">Todas</html:option>
                                    <html:option value="0">Ativo em uso</html:option>
                                    <html:option value="1">Ativo fora uso</html:option>
                                    <html:option value="2">Inativo</html:option>
                                </html:select>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Localização:
                            <td>
                            <td>
                                <html:text property="localizacao" size="30" value="${EquipamentoAction_localizacao}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Serial:
                            <td>
                            <td>
                                <html:text property="serial" size="30" value="${EquipamentoAction_serial}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Fabricante:
                            <td>
                            <td>
                                <html:text property="fabricante" size="30" value="${EquipamentoAction_fabricante}"/>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Modelo:
                            <td>
                            <td>
                                <html:text property="modelo" size="30" value="${EquipamentoAction_modelo}"/>
                            <td>
                        </tr>
                    </table>
                    <html:submit value="filtrar" styleClass="botao"/>
                    &nbsp; &nbsp;
                    <html:button property="a" value="Imprimir" styleClass="botao" onclick="location.href='Relatorio.do?metodo=listagemEquipamentos'"/>
                </html:form>
                
                <display:table id="equipamento" excludedParams="metodo" name="lista" defaultorder="descending" defaultsort="1" sort="list" export="false" requestURI="/Equipamento.do" class="tabelaDisplay"  pagesize="50">
                    <display:column property="tombo" title="Tombo" sortable="true"   />
                    <display:column property="descricao"  title="Descrição" sortable="true"   />
                    <display:column property="responsavel"  title="Responsavel" sortable="true"  />
                    <display:column property="detentor"  title="Detentor" sortable="true"  />
                    <display:column property="localizacao"  title="Localização" sortable="true"   />
                    <display:column property="nomeRede"  title="Nome na Rede" sortable="true"   />
                    <display:column property="situacao" title="Situação" sortable="true"  decorator="br.org.flem.helpdesk.web.displaytag.SituacaoEquipamento" />
                    <display:column title="Detalhe" sortable="true" ><div style="cursor: pointer;" onclick=" mostraDetalheEquipamento('${equipamento.codigo}', '${equipamento.modelo}', '${equipamento.fabricante}' ,'${equipamento.serial}', '${equipamento.tombo}');">Detalhe</div></display:column>
                </display:table>
            </div>
            <jsp:include flush="false" page="../inc/footer.jsp" />
        </div>
        <msg:alert escopo="session"/>  
    </body>
</html>