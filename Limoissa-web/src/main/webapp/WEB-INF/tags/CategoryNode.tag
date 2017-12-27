<%-- 
    Document   : CategoryNode
    Created on : 28 nov. 2017, 19:36:02
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>
<%@tag description="Display a node and his children" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="data" required="true" type="fr.limoissa.Model.Category" description="The node you want to display" %>
<%@attribute name="indent" type="java.lang.Integer" %>

<%-- Defaut values --%>
<c:set var="indent" value="${(empty indent) ? 0 : indent}" />

<%-- Indent --%>
<c:forEach begin="1" end="${ indent }">
    <c:out escapeXml="false" value="&nbsp;&nbsp;&nbsp;&nbsp;" />
</c:forEach>
    
<%-- Node name --%>
<c:out value="${ data.name }" />
    
<br />

<%-- Affichage des enfants --%>
<c:forEach items="${ data.children }" var="C">
    <ct:CategoryNode data="${ C }" indent="${ indent + 1 }" />
</c:forEach>
