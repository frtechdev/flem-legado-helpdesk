<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <script language="JavaScript" type="text/javascript">
            function update(){
                Funcoes.subCategoria(DWRUtil.getValue("idcategoria"), createList);
            }
            function createList(data){
                DWRUtil.removeAllOptions("idsubcategoria");
                DWRUtil.addOptions("idsubcategoria", data, "id", "sigla");
            }
            
            function alterarSituacao(){
                Funcoes.respostaAutomatica( DWRUtil.getValue("situacao") ,trocarResposta );
            }

            function trocarResposta(dados){
                resposta = document.getElementById('resposta');
                resposta.value = resposta.value + dados;
            }
        </script>
        <html:javascript formName="chamadoSuporteForm" method="validar" page="2"/>
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <msg:alert />
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Chamado <c:out value="${chamado.id}"/> </h2>
                <html:form action="ChamadoSuporte.do?metodo=salvar" onsubmit="return validar(this);">
                    <table>
                        <html:hidden property="id" />
                        <html:hidden property="usuario" />
                        <html:hidden property="assunto" />
                        <html:hidden property="responsavel" />
                        <html:hidden property="idorigem" />
                        <tr><td>Usuario:</td><td><c:out value="${nomeusuario}"/></td></tr>
                        <tr><td>Departamento:</td><td><c:out value="${nomedepartamento}"/></td></tr>
                        <tr><td>Ramal:</td><td><c:out value="${ramal}"/></td></tr>
                        <tr><td>Nome da Maquina:</td><td><c:out value="${nomeequipamento}"/></td></tr>
                        <tr><td>Assunto:</td><td><c:out value="${chamado.assunto}"/></td></tr>
                        <tr><td>Responsavel:</td><td><c:out value="${nomeresponsavel}"/></td><tr>
                        <tr><td>Origem:</td><td><c:out value="${chamado.origem.sigla}"/></td></tr>
                        <tr><td>Criação:</td><td><bean:write name="chamado" property="criacao" format="dd/MM/yyyy hh:mm" /></td></tr>
                        <tr><td>Ultima Ação:</td><td><bean:write name="chamado" property="ultimaAcao" format="dd/MM/yyyy hh:mm" /></td></tr>
                        <logic:present name="anexo">
                        <tr>
                            <td>Anexo:</td>
                            <td><a href="${anexo}" target="blank">Download</a></td>
                        </tr>
                        </logic:present>
                        <tr><td>Situacao:</td><td><html:select property="idsituacao" styleId="situacao" value="${chamado.situacao.id}" onchange="alterarSituacao();">
                                    <html:optionsCollection property="listasituacao" value="id" label="sigla" /> 
                        </html:select></td></tr>
                        <tr><td>Prioridade:</td><td><html:select property="idprioridade" value="${chamado.prioridade.id}" >
                                    <html:optionsCollection property="listaprioridade" value="id" label="sigla" /> 
                        </html:select></td></tr>
                        <tr><td>Categoria:</td><td><html:select styleId="idcategoria" property="idcategoria" value="${chamado.categoria.pai.id}" onchange="update();">
                                    <html:optionsCollection property="listacategoria" value="id" label="sigla" /> 
                        </html:select></td></tr>
                        <tr><td>Sub Categoria:</td><td><html:select styleId="idsubcategoria" property="idsubcategoria" value="${chamado.categoria.id}" >
                                    <html:optionsCollection property="listasubcategoria" value="id" label="sigla" /> 
                        </html:select></td></tr>
                        <tr><td colspan="2" >Observação:</td></tr>
                        <tr><td colspan="2" ><html:textarea property="observacao" cols="50" rows="4" /></td></tr>
                        <tr><td colspan="2" >Resposta:</td></tr>
                        <tr><td colspan="2" ><html:textarea property="resposta" styleId="resposta" cols="50" rows="4" /></td></tr>                
                        <tr><td colspan="2" >Solução:</td></tr>
                        <tr><td colspan="2" ><html:textarea property="solucao" cols="50" rows="4" /></td></tr>
                        <tr><td colspan="2" >Historico:</td></tr>
                        <tr><td colspan="2" ><html:textarea readonly="true"  property="descricao" cols="50" rows="8" /></td></tr>
                    </table>
                    <html:submit value="salvar" styleClass="botao"/>
                    &nbsp;
                    <input type="button" value="voltar" onclick="location.href='ChamadoSuporte.do'" class="botao"/>
                </html:form>
                
                <display:table id="chamado" name="lista" defaultsort="2" sort="list" export="false" requestURI="/ChamadoSuporte.do" class="tabelaDisplay" >
                    <display:column value="${chamado.id}" title="codigo" sortable="true" href="ChamadoSuporte.do?metodo=editar" paramId="id" paramProperty="id" />
                    <display:column value="${chamado.criacao}" title="Criação" sortable="true" decorator="br.org.flem.helpdesk.web.displaytag.DateWrapper"  />
                    <display:column value="${chamado.assunto}" title="Assunto" sortable="true"  />
                    <display:column value="${chamado.categoria.sigla}" title="Categoria" sortable="true"  />
                    <display:column value="${chamado.ultimaAcao}" title="Data" sortable="true" decorator="br.org.flem.helpdesk.web.displaytag.DateWrapper"  />
                    <display:column value="${chamado.situacao.sigla}" title="Situação" sortable="true"  />
                </display:table>
            </div>
            
            <jsp:include flush="false" page="../inc/footer.jsp" />
            
        </div>
    </body>
</html>
