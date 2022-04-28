<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
        <link href="css/displaytag.css" rel="stylesheet" type="text/css" />
        <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
        <div id="wrap">
            <jsp:include flush="false" page="inc/header.jsp" />
            <jsp:include flush="false" page="inc/sidebar.jsp" />
            <div id="content">
                <h2><fmt:message key="aplicacao.nome" /></h2>
                <p><img src="img/helpdesk.jpg" height="300" width="150" alt=" Example frontpage image" class="left photo" />Para que você possa contar sempre com serviço de qualidade 
                    do NTO, estamos disponibilizando uma nova ferramenta para 
                    facilitar a abertura de chamado, o atendimento e a avaliação 
                    da solução prestada aos usuários pelo NTO. 
                </p>
                <p class="box"><strong>Nota:</strong> O NTO pensando em você!</p>
            </div> 
            
            <jsp:include flush="false" page="inc/footer.jsp" />
            
        </div>
    </body>
</html>
