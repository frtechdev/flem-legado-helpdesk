<%@page contentType="text/html" errorPage="/erro.jsp"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://flem.org.br/autentica-tag" prefix="acesso"%>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css" href="css/800px.css" />
         <title><fmt:message key="aplicacao.nome" /> versão: <fmt:message key="aplicacao.versao" /></title>
    </head>
    <body>
        <acesso:autentica sistema="helpdesk" />
         <div id="wrap">
             
             <jsp:include flush="false" page="inc/header.jsp" />
             
            <div id="content">
                <h2><fmt:message key="aplicacao.nome" /></h2>
                <p><img src="img/example.jpg" width="280" height="144" alt="Example frontpage image" class="left photo" />Bla blabla bla bla lba lba lba lba lba l
                    bkabakba kab kab kab kab kba kba kb kb aka ba l bl abl bla blabla 
                    ksaksjak j ksjak jaks jaksj aksja kjaks jkajska jsakj skaj skaj ka jdsdfsf. 
                    </p>
                 <p class="box"><strong>Nota:</strong> Bla blabla bla bla lba lba lba lba lba l
                    bkabakba kab kab kab kab kba kba kb kb aka ba l bl abl bla blabla 
                    ksaksjak j ksjak jaks jaksj aksja kjaks jkajska jsakj skaj skaj ka jdsdfsf.</p>
            </div>

            <jsp:include flush="false" page="inc/sidebar.jsp" />

            <jsp:include flush="false" page="inc/footer.jsp" />

        </div>
    </body>
</html>
