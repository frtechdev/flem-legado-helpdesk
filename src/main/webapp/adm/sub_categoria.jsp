<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <html:javascript formName="baseForm" method="validar" />
       <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script>
            function limpaForm(form) {
                for( var i = 0; i < form.elements.length; i++ ) 
                { 
                    if (form.elements[i].type != "button" && 
                        form.elements[i].type != "submit") {
                        form.elements[i].value = "";
                    }
                } 
            }
        </script>
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                <h2>Sub Categoria</h2>
               <html:form action="Categoria.do?metodo=adicionarSubCategoria" onsubmit="return validar(this);">                    
                   <html:hidden property="id" />
                   
               <table>
                    <tr><td colspan="2">Categoria: <html:hidden property="idPai" /></td></tr>
                    <tr><td>Sigla: </td><td>${categoria.sigla}</td></tr>
                    <tr><td>Descrição: </td><td>${categoria.descricao}</td></tr>
                    <tr><td colspan="2">Sub Categoria: </td></tr>
                    <tr><td>Sigla:</td>
                    <td><html:text property="sigla" size="20" maxlength="50" /></td>
                    </tr>
                    <tr><td>Descricao:</td>
                    <td><html:text property="descricao" size="60" maxlength="200" /></td>
                    <tr><td>FAQ:</td>
                    <td><html:textarea property="faq" cols="40" rows="3"/></td>                    
                    </table>
                    <html:submit value="salvar" styleClass="botao"/>
                    &nbsp;
                    <input type="button" value="limpar" onclick="limpaForm(this.form);" Class="botao"/>
                    &nbsp;
                    <input type="button" value="voltar" onclick="location.href='Categoria.do'" Class="botao"/>
                    </html:form>
                    <display:table name="lista" defaultsort="1" sort="list" export="false" requestURI="" class="tabelaDisplay" >
                        <display:column property="sigla" title="Sigla" sortable="false"  />
                        <display:column property="descricao" title="Descrição" sortable="false"  />
                        <display:column href="Categoria.do?metodo=selecionarSubCategoria" paramId="id" paramProperty="id" title="Alterar" media="html" ><img alt="Alterar" border="0" src="img/editar.png"/></display:column>
                        <display:column href="Categoria.do?metodo=excluirSubCategoria" paramId="id" paramProperty="id" title="Excluir" media="html" ><img alt="Excluir" border="0" src="img/excluir.png"/></display:column>
                    </display:table>
            </div>
            <jsp:include flush="false" page="../inc/footer.jsp" />

        </div>
    </body>
</html>
