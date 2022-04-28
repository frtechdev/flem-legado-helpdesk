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
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/800px.css" />
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/displaytag.css" />
        <!--html:javascript formName="relatorioForm" method="validar" page="1"/-->
         <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
         <script src="<%=request.getContextPath()%>/dwr/interface/Funcoes.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/engine.js"></script>
        <script src="<%=request.getContextPath()%>/dwr/util.js"></script>
         <script language="JavaScript" type="text/javascript">
            
            function atualizarSubCategoria(){
                Funcoes.subCategoria(DWRUtil.getValue("categoria"),listaSubCategoria);
            }
            
            function listaSubCategoria(dados){                
                DWRUtil.removeAllOptions("subcategoria");
                DWRUtil.addOptions("subcategoria", [{id:"", sigla:"Selecione:"}], "id", "sigla");        
                DWRUtil.addOptions("subcategoria", dados, "id", "sigla");
            }
        </script>
     
     </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
          <jsp:include flush="false" page="../inc/header.jsp" />
          <jsp:include flush="false" page="/inc/sidebar.jsp" />
            <div id="content">
                  <h2>Filtro Listagem de Chamados</h2>
                    <html:form action="/Relatorio.do?metodo=listagemChamados"> <!--onsubmit="return validar(this);"-->
                    <table>
                        <tr>
                            <td>
                                Data Inicial:
                            </td>
                            <td>
                                <html:text property="inicio" maxlength="10" styleClass="requerido"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Data Final:
                            </td>
                            <td>
                                <html:text property="fim" maxlength="10" styleClass="requerido"/>
                            </td>
                        </tr>
                        <tr>
                            <td>Situações:</td>
                            <td>
                                <html:select property="situacao">
                                    <html:option value="">Todas</html:option>
                                    <html:option value="Nao">Não Fechados</html:option>
                                    <html:options collection="situacoes"  labelProperty="sigla" property="id" />
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Categoria: </td>
                            <td><html:select styleId="categoria" property="categoria" onchange="atualizarSubCategoria();">
                                    <html:option value="" >Todas</html:option>
                                    <html:options collection="categorias" property="id" labelProperty="sigla" /> 
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Sub Categoria: </td>
                            <td><html:select styleId="subcategoria" property="subcategoria">
                                    <html:option value="" >Todas</html:option>
                                </html:select>
                            </td>
                        </tr>
                    </table>
                    <table>
                        
                        <tr><td colspan="2" align="center">&nbsp;</td></tr>
                    </table>
                        <html:submit value="visualizar" styleClass="botao"/>

                </html:form>
            </div>

            <jsp:include flush="false" page="/inc/footer.jsp" />

        </div>
    </body>
</html>
