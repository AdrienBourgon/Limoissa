<%-- 
    Document   : Country
    Created on : 30 nov. 2017, 14:06:17
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="Country">
    <form method="get" action="#">
        <input type="hidden" name="page" value="Countries" />
        <select name="C_Id">
            <c:forEach items="${ Countries }" var="C">
                <option value="${ C.id }">${ C.name }</option>
            </c:forEach>
        </select>
        <button type="submit">Modifier</button>
    </form>
    <form method="post" actoin="#">
        <fieldset>
            <legend>Ajout / Modification de pays</legend>
            <input type="hidden" name="C_Id" value="${ empty param.C_Id ? CountryToDisplay.id : param.C_Id }" />
            <label for="C_Name">Nom :</label>
            <input type="text" name="C_Name" id="C_Name" value="${ empty param.C_Name ? CountryToDisplay.name : param.C_Name }" />
            <button type="submit" name="${ empty CountryToDisplay ? 'C_Add' : 'C_Edit' }">${ empty CountryToDisplay ? "Ajouter" : "Modifier" }</button>
            <a href="Library?page=Countries">Effacer</a>
            <p class="error">${ Error }</p>
            <p class="Message">${ Message }</p>
        </fieldset>
    </form>
</div>
