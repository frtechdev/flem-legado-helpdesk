<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
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
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Consulta</h2>
                <html:form action="/Consulta.do?metodo=consultaChamados" >
                    <table>
                        <tr>
                            <td>Departamento:</td>
                            <td><html:select styleId="iddepartamento" property="departamento" onchange="atualizarUsuario();" value="${ConsultaAction_departamento}">
                                    <html:option value="" >Todos</html:option>
                                    <html:options collection="departamentos" property="codigo" labelProperty="codigo" /> 
                            </html:select></td>
                        </tr>
                        <tr>
                            <td>Usuario:</td>
                            <td>
                                <html:select styleId="idusuario" property="usuario" value="${ConsultaAction_usuario}">
                                    <html:options collection="usuarios" property="id" labelProperty="nome" /> 
                                </html:select>
                            </td>
                        </tr>
                        <tr>
                            <td>Origem:</td>
                            <td><html:select property="origem" value="${ConsultaAction_origem}">
                                    <html:option value="" >Todas</html:option> 
                                    <html:options collection="origens" property="id" labelProperty="sigla" /> 
                            </html:select></td>
                        </tr>
                        <tr>
                            <td>Categoria:</td>
                            <td><html:select styleId="idcategoria" property="categoria" onchange="atualizarSubCategoria();" value="${ConsultaAction_categoria}">
                                    <html:option value="" >Todas</html:option> 
                                    <html:options collection="categorias" property="id" labelProperty="sigla" /> 
                            </html:select></td>
                        </tr>
                        <tr>
                            <td>Sub Categoria:</td>
                            <td>
                                <html:select styleId="idsubcategoria" property="subcategoria" value="${ConsultaAction_subcategoria}">
                                    <html:options collection="subcategorias" property="id" labelProperty="sigla" /> 
                                </html:select>
                                
                            </td>
                        </tr>
                        <tr>
                            <td>Assunto:</td>
                            <td>
                                <html:text property="assunto" size="60" value="${ConsultaAction_assunto}"/> <a style="font-size: 14px;" href="#" onclick="if (document.getElementById('ajudaAssunto').style.display == 'none') {document.getElementById('ajudaAssunto').style.display = 'block'}else {document.getElementById('ajudaAssunto').style.display = 'none'}">?</a> 
                                <div id="ajudaAssunto" style="width: 650px; display: none; border:1px solid #999999;">
                                    <span><p>Digitando apenas palavras, o resultado será chamados que possuam, no assunto, qualquer 
                                            uma das palavras digitadas, como no exemplo <b>problema monitor</b>, onde o resultado será chamados 
                                        que possuam, no assunto, a palavra <b>problema</b> ou a palavra <b>monitor</b>.</p> <p>É possível utilizar o sinal de 
                                            adição <b>+</b>, para forçar que o resultado deva conter todas as palavras, como no exemplo <b>problema 
                                            + monitor</b>, onde o resultado será chamados que possuam, no assunto, as palavras <b>problema</b> 
                                        e <b>monitor</b>.</p> <p>Ainda existe uma terceira forma de busca que força que o resultado seja 
                                            chamados que possuam exatamente as palavras digitadas. Utilizando aspas duplas <b>" "</b> entre as 
                                            palavras, como no exemplo <b>"problema com monitor"</b>, 
                                        onde o resultado será chamados que possuam, no assunto, exatamente a frase <b>problema com monitor</b>.</p>
                                    </span>
                                    <br />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>Histórico:</td>
                            <td>
                                <html:text property="historico" size="60" value="${ConsultaAction_historico}"/> <a style="font-size: 14px;" href="#" onclick="if (document.getElementById('ajudaHistorico').style.display == 'none') {document.getElementById('ajudaHistorico').style.display = 'block'}else {document.getElementById('ajudaHistorico').style.display = 'none'}">?</a> 
                                <div id="ajudaHistorico" style="width: 650px; display: none; border:1px solid #999999;">
                                    <span><p>Digitando apenas palavras, o resultado será chamados que possuam, no histórico, qualquer 
                                            uma das palavras digitadas, como no exemplo <b>problema monitor</b>, onde o resultado será chamados 
                                        que possuam, no histórico, a palavra <b>problema</b> ou a palavra <b>monitor</b>.</p> <p>É possível utilizar o sinal de 
                                            adição <b>+</b>, para forçar que o resultado deva conter todas as palavras, como no exemplo <b>problema 
                                            + monitor</b>, onde o resultado será chamados que possuam, no histórico, as palavras <b>problema</b> 
                                        e <b>monitor</b>.</p> <p>Ainda existe uma terceira forma de busca que força que o resultado seja 
                                            chamados que possuam exatamente as palavras digitadas. Utilizando aspas duplas <b>" "</b> entre as 
                                            palavras, como no exemplo <b>"problema com monitor"</b>, 
                                        onde o resultado será chamados que possuam, no histórico, exatamente a frase <b>problema com monitor</b>.</p>
                                    </span>
                                    <br />
                                </div>
                            </td>
                        </tr>
                    </table>
                    <html:submit value="filtrar" styleClass="botao"/>
                </html:form>
                
                <display:table id="chamado" name="listaChamados" defaultsort="1" sort="list" export="false" excludedParams="metodo" class="tabelaDisplay" requestURI="./Consulta.do?metodo=consultaChamados" pagesize="50">
                    <display:column value="${chamado.id}" title="codigo" sortable="true" href="ChamadoSuporte.do?metodo=editar" paramId="id" paramProperty="id" />
                    <display:column value="${chamado.criacao}" title="Criação" sortable="true" decorator="br.org.flem.helpdesk.web.displaytag.DataSemHoraWrapper"  />
                    <display:column value="${chamado.colaborador.nome}" title="Usuário" sortable="true"  />
                    <display:column value="${chamado.categoria.sigla}" title="Sub Categoria" sortable="true"  />
                    <display:column value="${chamado.situacao.sigla}" title="Situação" sortable="true"  />
                    <display:column value="${chamado.assunto}" title="Assunto" sortable="true"  />
                </display:table>
            </div>
            
            
            <jsp:include flush="false" page="../inc/footer.jsp" />
            
        </div>
    </body>
</html>