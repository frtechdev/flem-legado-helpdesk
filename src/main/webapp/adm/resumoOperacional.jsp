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
        <link href="css/calendar-blue.css" rel="stylesheet" type="text/css" />
        <link href="<%=request.getContextPath()%>/css/calendar-blue.css" rel="stylesheet" type="text/css" />
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-pt.js"  type="text/javascript" ></script>
        <script language="JavaScript" src="<%=request.getContextPath()%>/js/calendar-setup.js"  type="text/javascript" ></script>

        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    
    <body>
         <acesso:autentica sistema="helpdesk" />
         <div id="wrap">
            <jsp:include flush="false" page="../inc/header.jsp" />
            <jsp:include flush="false" page="../inc/sidebar.jsp" />
            <div id="content">
                <h2>Resumo Operacional</h2>
                <html:form action="/Resumo.do" >
                    <table>
                        <tr><td>Inicial:</td><td><html:text styleId="dataInicial" property="dataInicial"  size="15" maxlength="10" /> <img  alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataInicial', '%d/%m/%Y');"/></td></tr>
                        <tr><td>Final:</td><td> <html:text styleId="dataFinal" property="dataFinal" size="15" maxlength="10"  /> <img  alt="Selecione uma data" src="img/seletorData.png" onclick="return showCalendar('dataFinal', '%d/%m/%Y');"/></td></tr>
                     </table>
                        <html:submit  styleClass="botao" />
                        
               <display:table id="dias" excludedParams="metodo" name="lista"   sort="list" export="false" requestURI="/Resumo.do" class="tabelaDisplay"  pagesize="7">
                    <display:column  title="Descricao" property="descricao" sortable="true"/>
                    <display:column  title="Segunda" property="segunda"/>
                    <display:column  title="Terça" property="terca"/>
                    <display:column  title="Quarta" property="quarta"/>
                    <display:column  title="Quinta" property="quinta"/>
                    <display:column  title="Sexta" property="sexta"/>

                </display:table> 
                        
                <display:table id="meses" excludedParams="metodo" name="listames"   sort="list" export="false" requestURI="/Resumo.do" class="tabelaDisplay"  pagesize="7">
                    <display:column  title="Descricao" property="descricao" sortable="true"/>
                    <display:column  title="Janeiro" property="jan"/>
                    <display:column  title="Fevereiro" property="fev"/>
                    <display:column  title="Março" property="mar"/>
                    <display:column  title="Abril" property="abr"/>
                    <display:column  title="Maio" property="mai"/>
                    <display:column  title="Junho" property="jun"/>
                    <display:column  title="Agosto" property="ago"/>
                    <display:column  title="Setembro" property="set"/>
                    <display:column  title="Outubro" property="out"/>
                    <display:column  title="Novembro" property="nov"/>
                    <display:column  title="Dezembro" property="dez"/>
                </display:table>
                        
                </html:form>
                
            </div>
             
         </div>
    </body>
    
</html>