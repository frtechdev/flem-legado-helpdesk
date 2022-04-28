<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
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
                <h2>Chamado</h2>
                <display:table id="chamado" name="lista" defaultsort="1" defaultorder="descending" sort="list" export="false" excludedParams="metodo" class="tabelaDisplay" requestURI="/ChamadoUsuario.do" pagesize="20" >
                    <display:column value="${chamado.id}" title="codigo" sortable="true" href="ChamadoUsuario.do?metodo=editar" paramId="id" paramProperty="id" />
                    <display:column value="${chamado.criacao}" title="Criação" sortable="true" decorator="br.org.flem.helpdesk.web.displaytag.DateWrapper"  />
                    <display:column value="${chamado.assunto}" title="Assunto" sortable="true" maxLength="30" maxWords="5" />
                    <display:column value="${chamado.categoria.sigla}" title="Categoria" sortable="true"  />
                    <display:column value="${chamado.ultimaAcao}" title="Ultima Ação" sortable="true" decorator="br.org.flem.helpdesk.web.displaytag.DateWrapper"  />
                    <display:column value="${chamado.situacao.sigla}" title="Situação" sortable="true"  />
                    <display:column href="ChamadoUsuario.do?metodo=formularioEnvioArquivo" paramId="id" paramProperty="id" title="Anexo" media="html" ><img alt="Anexar Arquivo" border="0" src="img/enviar_arquivo.png"/></display:column>
                </display:table>
            </div>

            <jsp:include flush="false" page="../inc/footer.jsp" />

        </div>
        <msg:alert escopo="session"/>  
    </body>
</html>
