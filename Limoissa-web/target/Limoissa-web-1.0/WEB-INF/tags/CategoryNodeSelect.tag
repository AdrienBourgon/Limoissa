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
<%@attribute name="indent" %>
<%@attribute name="selectedId" type="java.lang.Integer" %>
<%@attribute name="idToIgnore" type="java.lang.Integer" %>

<%-- Defaut values --%>
<c:set var="indent" value="${(empty indent) ? 0 : indent}" />
<c:set var="selectedId" value="${(empty selectedId) ? 0 : selectedId}" />
<c:set var="idToIgnore" value="${(empty idToIgnore) ? 0 : idToIgnore}" />

<option value="${ data.id }" <c:if test="${ selectedId eq data.id }">selected="selected"</c:if>>
    
<%-- Indent --%>
<c:forEach begin="1" end="${ indent }"> --- </c:forEach>
    
<%-- Node name --%>
<c:out value="${ data.name }" />

</option>

<%-- Affichage des enfants --%>
<c:forEach items="${ data.children }" var="C">
    <ct:CategoryNodeSelect data="${ C }" indent="${ indent + 1 }" selectedId="${ selectedId }" />
</c:forEach>
