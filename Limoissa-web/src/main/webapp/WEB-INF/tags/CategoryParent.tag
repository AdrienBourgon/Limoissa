<%-- 
    Document   : CategoryParent
    Created on : 1 déc. 2017, 12:25:28
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags/" %>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@ attribute name="data" type="fr.limoissa.Model.Category" required="true" %>
<%@ attribute name="text" type="java.lang.String" %>

<c:set var="text" value="${ data.name += (empty text ? '' : ' > ') += text}" />
<%-- any content can be specified here e.g.: --%>
<c:choose>
    <c:when test="${ empty data.parent }">${ text }</c:when>
    <c:otherwise>
        <ct:CategoryParent data="${ data.parent }" text="${ text }" />
    </c:otherwise>
</c:choose>