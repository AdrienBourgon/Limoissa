<%-- 
    Document   : Category
    Created on : 28 nov. 2017, 19:32:53
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<div id="Category">
    <div>
        <form method="get" action="#">
        <input type="hidden" name="page" value="Categories" />
            <select name="C_Id">
                <c:forEach items="${ Categories }" var="I">
                    <ct:CategoryNodeSelect data="${ I }" />
                </c:forEach>
            </select>
            <button type="submit">Modifier</button>
        </form>
    </div>
    <form method="post" action="Library?page=Categories">
        <fieldset>
            <legend>Ajout / Modification d'une catégorie</legend>
            <input type="hidden" name="C_Id" id="C_Id" value="${ empty param.C_Id ? CategoryToDisplay.id : param.C_Id }" />
            <label for="C_Name">Nom :</label>
            <input type="text" name="C_Name" value="${ empty param.C_Name ? CategoryToDisplay.name : param.C_Name }" placeholder="Nom de la catégorie" />
            <label for="C_Parent">Catégorie parente :</label>
            <select name="C_Parent">
                <option value="-1"> </option>
                <c:forEach items="${ Categories }" var="I">
                    <ct:CategoryNodeSelect data="${ I }" selectedId="${ empty param.C_Parent ? CategoryToDisplay.getParent().id : param.C_Parent }" />
                </c:forEach>
            </select>
            <input type="checkbox" name="C_Disabled" id="C_Disabled" ${ (empty param.C_Disabled ? CategoryToDisplay.disabled : (param.C_Disabled eq "on")) ? 'checked="checked"' : '' } />
            <label for="C_Disabled">Désactivée</label>
            <button type="submit" name="${ empty CategoryToDisplay ? 'C_Add' : 'C_Edit' }">${ empty CategoryToDisplay ? "Ajouter" : "Modifier" }</button>
            <a href="Library?page=Categories">Effacer</a>
            <p class="error">${ Error }</p>
            <p class="Message">${ Message }</p>
        </fieldset>
    </form>
</div>
