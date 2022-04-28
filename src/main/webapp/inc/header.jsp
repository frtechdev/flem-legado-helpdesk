<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %> 
<div id="header">
    <a href="Home.do"><img align="left" src="img/logohd.gif" width="262" height="52" alt="HelpDesk - Página Inicial"/></a>
    
     <img align="right" src="img/flemlogo.gif" width="63" height="46" alt="Flem Web"/>
 </div>
 <div id="subHeader" style="text-align:right; font-size:7pt; color:#448866; font-weight:bold;">
     
         <%=new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())%>,
         Olá 
         <%=((br.org.flem.fw.service.IUsuario) session.getAttribute(br.org.flem.fw.util.Constante.USUARIO_LOGADO)).getNome()%>
 </div>
