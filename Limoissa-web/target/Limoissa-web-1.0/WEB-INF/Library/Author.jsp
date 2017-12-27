<%-- 
    Document   : Author
    Created on : 30 nov. 2017, 14:42:05
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="Author">
    <form method="get" action="#">
        <input type="hidden" name="page" value="Authors" />
        <select name="A_Id">
            <c:forEach items="${ Authors }" var="A">
                <option value="${ A.id }">${ A.firstName += ' ' += A.lastName }</option>
            </c:forEach>
        </select>
        <button type="submit">Modifier</button>
    </form>
    
    <form method="post" action="#">
        <fieldset>
            <legend>Ajout / Modification d'un auteur</legend>
            <input type="hidden" name="A_Id" value="${ param.A_Id }" />
            <label for="A_FirstName">Prénom :</label>
                <input type="text" id="A_FirstName" name="A_FirstName" value="${ empty param.A_FirstName ? AuthorToDisplay.firstName : param.A_FirstName }" />
            <label for="A_LastName">Nom :</label>
                <input type="text" id="A_LastName" name="A_LastName" value="${ empty param.A_LastName ? AuthorToDisplay.lastName : param.A_LastName }" />
            <label for="A_Country">Pays :</label>
            <select id="A_Country" name="A_Country">
                <c:forEach items="${ Countries }" var="C">
                    <option value="${ C.id }"${(empty param.A_Country ? AuthorToDisplay.getCountry().id : param.A_Country) eq C.id ? ' selected="selected"' : ''}>${ C.name }</option>
                </c:forEach>
            </select>
            <input type="checkbox" name="A_Disabled" id="A_Disabled"${ (empty param.A_Disabled ? AuthorToDisplay.disabled : param.A_Disabled eq "on") ? ' checked="checked"' : '' } />
            <label for="A_Disabled">Désactivé</label>
            <button type="submit" name="${ empty AuthorToDisplay ? 'A_Add' : 'A_Edit' }">${ empty AuthorToDisplay ? "Ajouter" : "Modifier" }</button>
            <a href="Library?page=Authors">Effacer</a>
            <p class="error">${ Error }</p>
            <p class="Message">${ Message }</p>
        </fieldset>
    </form>
</div>
