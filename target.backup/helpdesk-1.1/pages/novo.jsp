<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
        <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <script language="JavaScript" type="text/javascript">
            
            function atualizarSubCategoria(){
                document.getElementById('divFaq').style.display = 'none';
                Funcoes.subCategoria(DWRUtil.getValue("categoria"),listaSubCategoria);
            }
            
            function listaSubCategoria(dados){                
                DWRUtil.removeAllOptions("subcategoria");
                DWRUtil.addOptions("subcategoria", [{id:"", sigla:"Selecione:"}], "id", "sigla");        
                DWRUtil.addOptions("subcategoria", dados, "id", "sigla");
            }
            
            function onChange_SubCategoria(){
                document.getElementById('divFaq').style.display = 'none';
                Funcoes.faqSubCategoria(DWRUtil.getValue("subcategoria"),mostrarFaqSubCategoria);
            }
            
            function mostrarFaqSubCategoria(texto) { 
                if (texto != "") {
                    var spanTexto = document.getElementById("spanTexto");
                    spanTexto.innerHTML = texto;
                    document.getElementById('divFaq').style.display = 'block';
                }
            }
        </script>
        <html:javascript formName="chamadoForm" method="validar" />
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Chamado</h2>
                <div id="divFaq" style="display: none; background-image: url(./img/balao_help.gif); background-repeat: no-repeat; width:417px; height:101px; padding:2px; margin:0px;">
                    <div id="fechar" style="float:right; margin:0px; right:3px;">
                        <a href="#" onclick="JavaScript: document.getElementById('divFaq').style.display = 'none';" title="Fechar"><span style="font-size:13pt; color:#4682B4; font-weight:bold;" >X</span></a>
                    </div>
                    <div id="spanTexto" style="margin:0px;"></div>
                </div>
                <html:form action="ChamadoUsuario.do?metodo=adicionar" onsubmit="return validar(this);" method="post" enctype="multipart/form-data">
                    <html:hidden property="resposta" value="0" />
                    <table>
                        <tr><td>Categoria: </td>
                            <td><html:select styleId="categoria" property="idcategoria" onchange="atualizarSubCategoria();">
                                    <html:option value="" > -- Selecione -- </html:option>
                                    <html:optionsCollection property="listacategoria" value="id" label="sigla" /> 
                        </html:select></td></tr>
                        <tr><td>Sub Categoria: </td>
                            <td><html:select styleId="subcategoria" property="idsubcategoria" onchange="onChange_SubCategoria();">
                                    <html:option value="" > -- Selecione -- </html:option>
                                    <!--html:optionsCollection property="listasubcategoria" value="id" label="sigla" /--> 
                        </html:select></td></tr>
                        <tr><td>Assunto: </td>
                            <td><html:text property="assunto" size="60" maxlength="200" /> </td>
                        </tr>
                        <tr>
                            <td colspan="2">Descrição:</td>
                        </tr> 
                        <tr>
                            <td colspan="2"><html:textarea property="descricao" cols="50" rows="4" /></td>
                        </tr>
                        <tr>
                            <td colspan="2">Anexo:</td>
                        </tr> 
                        <tr>
                            <td colspan="2"><html:file property="arquivoAnexo" size="50" /></td>
                        </tr>
                    </table>
                    <html:submit value="adicionar" styleClass="botao"/>
                    &nbsp;
                    <input type="button" value="voltar" onclick="location.href='ChamadoUsuario.do'" Class="botao"/>
                </html:form>
            </div>

            <jsp:include flush="false" page="../inc/footer.jsp" />
            
        </div>
    </body>
</html>
