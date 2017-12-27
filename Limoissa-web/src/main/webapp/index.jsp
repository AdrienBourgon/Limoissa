<%@ page import="java.util.HashMap"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
    /*Gestion des pages du site */
    HashMap<String, String[]> Pages = new HashMap<>();
    Pages.put("Signin", new String[] {"Connexion", "WEB-INF/Connection.jsp"});
    
    String Page = request.getParameter("page");
    if(null == Page || Page.isEmpty() || !Pages.containsKey(Page))
        Page = "Signin";
    
    /* Fichiers CSS */
    String[] CSS = new String[] {
        "Style.css",
        "Forms.css"
    };
%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <title><%= Pages.get(Page)[0] %></title>
        <% for(String F: CSS) { %><link rel="stylesheet" type="text/css" href="Style/<%= F %>" /><% } %>
    </head>
    <body>
        <jsp:include page="<%= Pages.get(Page)[1] %>" />
    </body>
</html>
