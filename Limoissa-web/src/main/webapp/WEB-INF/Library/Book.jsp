<%-- 
    Document   : Book
    Created on : 30 nov. 2017, 18:40:30
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="Book">
    <form method="post" action="#" enctype="multipart/form-data">
        <fieldset>
            <legend>Ajout depuis un fichier XML</legend>
            <input type="file" name="B_XML" placeholder="Votre fichier XML" accept=".xml" />
            <button type="submit">Envoyer</button>
            <p class="MessageXML">${ MessageXML }</p>
        </fieldset>
    </form>
    <form method="post" action="#" enctype="multipart/form-data">
        <fieldset>
            <legend>Ajout / Modification d'un livre</legend>
            <input type="hidden" name="B_Id" value="${ BookToDisplay.id }" />
            <label for="B_Title">Titre</label>
                <input type="text" name="B_Title" id="B_Title" value="${ empty param.B_Title ? BookToDisplay.title : param.B_Title }" />
            <label for="B_Summary">Résumé</label>
                <textarea id="B_Summary" name="B_Summary">${ empty param.B_Summary ? BookToDisplay.summary : param.B_Summary }</textarea>
            <label for="B_Stock">Stock</label>
                <input type="number" min="0" id="B_Stock" name="B_Stock" value="${ empty param.B_Stock ? BookToDisplay.stock : param.B_Stock }" />
            <label for="B_Price">Prix</label>
                <input type="number" min="0" step="0.01" id="B_Price" name="B_Price" value="${ empty param.B_Price ? BookToDisplay.price : param.B_Price }" />
            <label for="B_Author">Auteur</label>
                <select name="B_Author">
                    <c:forEach items="${ Authors }" var="A">
                        <option value="${ A.id }"${ (empty param.B_Author ? BookToDisplay.getAuthor().id : param.B_Author) eq A.id ? ' selected="selected"' : ''}>${A.firstName += ' ' += A.lastName}</option>
                    </c:forEach>
                </select>
            <label for="B_Category">Catégorie</label>
                <select name="B_Category">
                    <option value="-1"></option>
                    <c:forEach items="${ Categories }" var="C">
                        <ct:CategoryNodeSelect data="${ C }" selectedId="${ empty param.B_Category ? BookToDisplay.getCategory().id : param.B_Category }" />
                    </c:forEach>
                </select>
            <label for="B_PublicationDate">Date de publication</label>
                <input type="date" id="B_PublicationDate" name="B_PublicationDate" value="${ empty param.B_PublicationDate ? BookToDisplay.getFormattedDate() : param.B_PublicationDate }" />
            <button type="submit" name="${ empty BookToDisplay.id ? 'B_Add' : 'B_Edit' }">${ empty BookToDisplay.id ? "Ajouter" : "Modifier" }</button>
            <p class="Message">${ Message }</p>
            <p class="error"> ${ Error }</p>
        </fieldset>
    </form>
    <c:if test="${ !empty BookToDisplay }">
        <form method="post" action="#" enctype="multipart/form-data">
            <fieldset>
                <legend>Supprimer le livre</legend>
                <input type="hidden" name="B_Id" value="${ BookToDisplay.id }" />
                <button type="submit" name="B_Delete">Supprimer</button>
            </fieldset>
        </form>
    </c:if>
</div>
