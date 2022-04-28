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
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Chamado</h2>
                <html:form action="ChamadoUsuario.do?metodo=salvar" onsubmit="return validar(this);" >
                    <html:hidden property="id" />
                        <html:hidden property="assunto" />
                        <html:hidden property="idcategoria" />
                        <html:hidden property="idsubcategoria" />
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
                        <logic:present name="anexo">
                        <tr>
                            <td>Anexo:</td>
                            <td><a href="${anexo}" target="blank">Download</a></td>
                        </tr>
                        </logic:present>
                        <tr>
                            <td colspan="2">Resposta:</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <html:textarea property="resposta" rows="4" cols="40" />
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">Historico:</td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <tr>
                                    <td colspan="2" ><html:textarea readonly="true"  property="descricao" cols="50" rows="8" /></td>
                                </tr>
                            </td>
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
