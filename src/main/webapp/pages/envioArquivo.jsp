<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <html:javascript formName="chamadoForm" method="validar" page="2"/>
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Anexar Arquivo do Chamado</h2>
                <html:form action="ChamadoUsuario.do?metodo=enviarArquivo" onsubmit="return validar(this);" method="post" enctype="multipart/form-data">
                    <html:hidden property="id" />
                    <table>
                        <tr>
                            <td>Data de Criação:</td>
                            <td>
                                <bean:write name="chamado" property="criacao" format="dd/MM/yyyy hh:mm:ss" /> 
                            </td>
                        </tr>
                        <tr>
                            <td>Assunto: </td>
                            <td>${chamado.assunto}</td>
                        </tr>
                        <tr>
                            <td>Ultima Ação:</td>
                            <td>
                                <bean:write name="chamado" property="ultimaAcao" format="dd/MM/yyyy hh:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <td>Responsavel:</td>
                            <td>${nomeresponsavel}</td>
                        </tr>
                        <tr>
                            <td>Categoria: </td>
                            <td>${chamado.categoria.pai.sigla}</td>
                        </tr>
                        <tr>
                            <td>Sub Categoria:</td>
                            <td>${chamado.categoria.sigla}</td>
                        </tr>
                        <tr>
                            <td>Situação:</td>
                            <td>${chamado.situacao.sigla}</td>
                        </tr>
                        <tr>
                            <td colspan="2">Anexo:</td>
                        </tr> 
                        <tr>
                            <td colspan="2"><html:file property="arquivoAnexo" size="50" /></td>
                        </tr>
                    </table>
                    <html:submit value="salvar" styleClass="botao"/>
                    &nbsp;
                    <input type="button" value="voltar" onclick="location.href='ChamadoUsuario.do'" Class="botao"/>
                </html:form>
            </div>
            
            
            <jsp:include flush="false" page="../inc/footer.jsp" />
            
        </div>
    </body>
</html>
