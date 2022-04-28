<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@taglib uri="http://flem.org.br/mensagem-tag" prefix="msg"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:javascript formName="baseForm" method="validar" />
        <title><fmt:message key="aplicacao.nome" /></title>
        
    </head>
    <body>
        <msg:alert />
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Categoria</h2>
                <html:form action="Categoria.do?metodo=adicionar" onsubmit="return validar(this);">
                    <table>
                        
                        <tr><td>Sigla:</td>
                            <td><html:text property="sigla" size="20" maxlength="50" /></td>
                        </tr>
                        <tr><td>Descricao:</td>
                        <td><html:text property="descricao" size="60" maxlength="200" /></td>
                        <tr><td colspan="2"><html:submit value="adicionar" styleClass="botao"/></td></tr>
                    </table>
                </html:form>
                <display:table name="lista" defaultsort="0" sort="list" export="false" requestURI="" class="tabelaDisplay">
                    <display:column property="sigla" title="Sigla" sortable="true"  />
                    <display:column property="descricao" title="Descrição" sortable="true"  />
                    <display:column href="Categoria.do?metodo=subCategoria" paramId="id" paramProperty="id" title="Sub Categoria" media="html"><img alt="Sub Categoria" border="0" src="img/subcategoria.png"/></display:column>
                    <display:column href="Categoria.do?metodo=excluir" paramId="id" paramProperty="id" title="Excluir" media="html" ><img alt="Excluir" border="0" src="img/excluir.png"/></display:column>
                </display:table>
            </div>
            
            <jsp:include flush="false" page="../inc/footer.jsp" />
            
        </div>
    </body>
</html>
