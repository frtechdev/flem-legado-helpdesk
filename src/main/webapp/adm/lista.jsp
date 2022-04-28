<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Filtros dos Chamados</h2>
                <html:form action="ChamadoSuporte.do?metodo=filtrar">

                    <table>
                        <tr>
                            <td>Situação: <html:select property="idsituacao" value="${situacao.id}">
                                    <html:option value="" > Não Finalizados </html:option>  
                                    <html:optionsCollection property="listasituacao" value="id" label="sigla" /> 
                                </html:select>
                            <td>
                        </tr>
                        <tr>
                            <td>
                                Categoria: <html:select property="idcategoria" value="${categoria.id}">
                                    <html:option value="" > TODAS </html:option>  
                                    <html:optionsCollection property="listacategoria" value="id" label="sigla" /> 
                                </html:select>
                            <td>
                        </tr>
                    </table>
                    <html:submit value="filtrar" styleClass="botao"/>
                </html:form>
                <display:table id="chamado" excludedParams="metodo" name="lista" defaultorder="descending" defaultsort="1" sort="list" export="false" requestURI="/ChamadoSuporte.do" class="tabelaDisplay"  pagesize="50">
                    <%String classe = br.org.flem.helpdesk.util.CorLinhaChamadoUtil.getStyleClass((br.org.flem.helpdesk.negocio.Chamado) pageContext.findAttribute("chamado"));%>
                    <display:column class="<%=classe%>" value="${chamado.id}" title="codigo" sortable="true" href="ChamadoSuporte.do?metodo=editar&dir=des" paramId="id" paramProperty="id" />
                    <display:column class="<%=classe%>" title="" sortable="true">
                        <c:forEach  items="${chamado.anexo}" var="anexo" end="0" >
                            <a href="ChamadoSuporte.do?metodo=download&id=${anexo.id}&filename=HelpDesk_Anexo${chamado.id}.zip">
                                <img src="img/attach.png" alt="anexo" title="anexo" />
                            </a>
                        </c:forEach>
                    </display:column>
                    <display:column class="<%=classe%>" value="${chamado.ultimaAcao}" title="Data" sortable="true" decorator="br.org.flem.helpdesk.web.displaytag.DateWrapper"  />
                    <display:column class="<%=classe%>" value="${chamado.assunto}" title="Assunto" sortable="true"  maxLength="30" maxWords="5" />
                    <display:column class="<%=classe%>" value="${chamado.categoria.sigla}" title="Sub Categoria" sortable="true"  />
                    <display:column sortProperty="colaborador" class="<%=classe%>" value="${chamado.solicitante.nome}" title="Solicitante" sortable="true"  />
                    <display:column sortProperty="colaborador" class="<%=classe%>" value="${chamado.colaborador.nome}" title="Responsável" sortable="true"  />
                    <display:column class="<%=classe%>" value="${chamado.situacao.sigla}" title="Situação" sortable="true"  />
                </display:table>
            </div>


            <jsp:include flush="false" page="../inc/footer.jsp" />

        </div>
        <msg:alert escopo="session"/>  
    </body>
</html>
