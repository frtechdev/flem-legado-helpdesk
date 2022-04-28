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
    <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
    <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
    <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
    <title><fmt:message key="aplicacao.nome" /></title>
    <script>
        function alterarSituacao(){
            Funcoes.respostaAutomatica( DWRUtil.getValue("situacao") ,trocarResposta );
        }

        function trocarResposta(dados){
            resposta = document.getElementById('resposta');
            resposta.value = dados;
        }
    </script>
    <html:javascript formName="situacaoForm" method="validar" />
</head>
<body>

<acesso:autentica sistema="helpdesk" />
<div id="wrap">
    <jsp:include flush="false" page="../inc/header.jsp" />
    <jsp:include flush="false" page="../inc/sidebar.jsp" />
    <div id="content">
        <h2>Resposta Automática</h2>
        <html:form action="Situacao.do?metodo=salvar" onsubmit="return validar(this);">
        <table>     
            <tr>
                <td>Situacao:</td>
                <td>
                    <html:select property="situacao"  styleId="situacao" onchange="alterarSituacao();">
                        <html:option value="" >Selecione:</html:option>
                        <html:options collection="situacoes" property="id" labelProperty="sigla" /> 
                    </html:select>
                </td>
            </tr>
            <tr>
                <td>Resposta Automática:</td>
                <td>
                    <html:text property="resposta" styleId="resposta" size="60" maxlength="200" />
                </td>
            </tr>
            <tr>
                <td colspan="2"><html:submit value="salvar" styleClass="botao"/></td>
            </tr>
        </table>
        </html:form>
    </div>
    
    <jsp:include flush="false" page="../inc/footer.jsp" />
    
</div>
<msg:alert />
</body>
</html>
