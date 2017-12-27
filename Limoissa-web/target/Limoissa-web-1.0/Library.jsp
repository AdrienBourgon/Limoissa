<%-- 
    Document   : Library
    Created on : 28 nov. 2017, 16:37:56
    Author     : Adrien
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%    
    /* Fichiers CSS */
    String[] CSS = new String[] {
        "Style.css",
        "Forms.css",
        "Menu.css",
        "Search.css"
    };
    
    /* Ficheirs JavaScript */
    String[] JS = new String[] {
        "jquery-3.2.1.min.js",
        "Search.js"
    };
%>

<!DOCTYPE html>
<html lang="fr">
    <head>
        <title>${ TitleToDisplay }</title>
        <meta charset="UTF-8">
        <% for(String F: CSS) { %><link rel="stylesheet" type="text/css" href="Style/<%= F %>" /><% } %>
        <% for(String F: JS) { %><script type="text/JavaScript" src="Scripts/<%= F %>" ></script><% } %>
    </head>
    <body>
        <header>
            <jsp:include page="WEB-INF/Template/Menu.jsp" />
            <c:if test="${ Member.IsAdmin() }">
                <jsp:include page="WEB-INF/Template/AdminMenu.jsp" />
            </c:if>
            <div class="clear"></div>
        </header>
        <div class="MainError">${ MainError }</div>
        
        <jsp:include page="${ PageToInclude }" />
    </body>
</html>
