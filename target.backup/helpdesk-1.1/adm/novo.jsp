<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
        <script language="JavaScript" type="text/javascript">
            function atualizarSubCategoria(){
                Funcoes.subCategoria(DWRUtil.getValue("idcategoria"),listaSubCategoria);
            }
            
            function listaSubCategoria(dados){                
                DWRUtil.removeAllOptions("idsubcategoria");
                DWRUtil.addOptions("idsubcategoria", [{id:"", sigla:"Selecione:"}], "id", "sigla");        
                DWRUtil.addOptions("idsubcategoria", dados, "id", "sigla");
            }
            
            function atualizarUsuario(){
                Funcoes.usuarioDepartamento( DWRUtil.getValue("iddepartamento") ,listaUsuario );
            }

            function listaUsuario(dados){
                   DWRUtil.removeAllOptions("idusuario");
                   DWRUtil.addOptions("idusuario", [{id:"", nome:"Selecione:"}], "id", "nome");        
                   DWRUtil.addOptions("idusuario", dados, "id", "nome");
            }
        </script>
        
        <html:javascript formName="chamadoSuporteForm" method="validar" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Chamado</h2>
                <html:form action="ChamadoSuporte.do?metodo=adicionar" onsubmit="return validar(this);" method="post" enctype="multipart/form-data">
                    <html:hidden property="idsituacao" value="0" />
                    <html:hidden property="idprioridade" value="0" />
                    <html:hidden property="resposta" value="0" />
                    <table>
                        <tr><td>Departamento:</td>
                            <td><html:select styleId="iddepartamento" property="iddepartamento" onchange="atualizarUsuario();">
                                    <html:option value="" >Selecione: </html:option>
                                    <html:optionsCollection property="listadepartamento" value="codigo" label="codigo" /> 
                            </html:select></td>
                        </tr>
                        <tr><td>Usuario:</td>
                            <td><html:select styleId="idusuario" property="idusuario" >
                                    <html:option value="" >Selecione:</html:option> 
                            </html:select></td>
                        </tr>
                        <tr><td>Origem:</td>
                            <td><html:select property="idorigem" >
                                    <html:option value="" >Selecione:</html:option> 
                                    <html:optionsCollection property="listaorigem" value="id" label="sigla" /> 
                            </html:select></td>
                        </tr>
                        <tr><td>Categoria:</td>
                            <td><html:select styleId="idcategoria" property="idcategoria" onchange="atualizarSubCategoria();">
                                    <html:option value="" >Selecione:</html:option> 
                                    <html:optionsCollection property="listacategoria" value="id" label="sigla" /> 
                            </html:select></td>
                        </tr>
                        <tr><td>Sub Categoria:</td>
                            <td><html:select styleId="idsubcategoria" property="idsubcategoria" >
                                     <html:option value="" >Selecione:</html:option> 
                            </html:select></td>
                        </tr>
                        <tr><td>Assunto:</td>
                        <td><html:text property="assunto" size="60" maxlength="100" /> </td>
                        <tr><td colspan="2">Descrição:</td></tr>
                        <tr><td colspan="2"><html:textarea property="descricao" cols="50" rows="4" /></td></tr>
                        <tr>
                            <td colspan="2">Anexo:</td>
                        </tr> 
                        <tr>
                            <td colspan="2"><html:file property="arquivoAnexo" size="50" /></td>
                        </tr>
                    </table>
                    <html:submit value="adicionar" styleClass="botao"/>
                    &nbsp;
                    <input type="button" value="voltar" onclick="location.href='ChamadoSuporte.do'" class="botao"/>
                </html:form>
            </div>
            
            <jsp:include flush="false" page="../inc/footer.jsp" />
            
        </div>
    </body>
</html>
